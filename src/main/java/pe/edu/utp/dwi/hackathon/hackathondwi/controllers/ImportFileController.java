package pe.edu.utp.dwi.hackathon.hackathondwi.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pe.edu.utp.dwi.hackathon.hackathondwi.dao.EvaluationDAO;
import pe.edu.utp.dwi.hackathon.hackathondwi.domain.upload.StudentDataImport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(value = "/import-file")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class ImportFileController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/import.xhtml").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("file");
        XSSFWorkbook book = new XSSFWorkbook(filePart.getInputStream());
        XSSFSheet sheet = book.getSheetAt(0);

        String sectionId = sheet.getRow(0).getCell(1).getRawValue();
        String sectionName = sheet.getRow(0).getCell(2).getStringCellValue();

        String evaluationName = sheet.getRow(1).getCell(1).getStringCellValue();
        Date evaluationDate = sheet.getRow(2).getCell(1).getDateCellValue();

        Double evaluationMinimum = sheet.getRow(3).getCell(1).getNumericCellValue();
        short amountOfQuestions = sheet.getRow(4).getLastCellNum();

        ArrayList<Double> questionsPoints = new ArrayList<>();

        for (int i = 1; i < amountOfQuestions; i++) {
            try {
                XSSFCell cell = sheet.getRow(4).getCell(i);
                if(cell == null) continue;
                questionsPoints.add(cell.getNumericCellValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        final int fixedFirstStudentRow = 7;

        int amountOfStudents = sheet.getLastRowNum() - fixedFirstStudentRow + 1;

        final ArrayList<StudentDataImport> students = new ArrayList<>();

        for (int i = 0; i < amountOfStudents; i++) {
            XSSFRow row = sheet.getRow(fixedFirstStudentRow + i);

            ArrayList<Double> points = new ArrayList<>();

            for (int j = 1; j <= amountOfQuestions; j++) {
                XSSFCell cell = row.getCell(j);
                if(cell == null) continue;
                if(cell.getCellType().equals(CellType.NUMERIC)) {
                    points.add(cell.getNumericCellValue());
                } else {
                    points.add(0.0);
                }
            }

            students.add(new StudentDataImport(
                    row.getCell(0).getStringCellValue(),
                    points
            ));
        }

        Integer evaluationId = new EvaluationDAO().insertEvaluationAndSection(sectionId, sectionName, evaluationName, evaluationDate, evaluationMinimum);

        if(evaluationId == null) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error inserting evaluation");
            return;
        }

        for (int i = 0; i < questionsPoints.size(); i++) {
            int questionNumber = i + 1;
            new EvaluationDAO().insertQuestion(evaluationId, questionNumber, questionsPoints.get(i));
        }

        for (StudentDataImport student : students) {
            new EvaluationDAO().setStudentWithNotes(evaluationId, student);
        }


        System.out.println(amountOfStudents);

        System.out.println("Section ID: " + sectionId);
        System.out.println("Section Name: " + sectionName);
        System.out.println("Evaluation Name: " + evaluationName);
        System.out.println("Evaluation Date: " + evaluationDate);
        System.out.println("Evaluation Minimum: " + evaluationMinimum);
        System.out.println("Amount of Questions: " + amountOfQuestions);

        resp.sendRedirect("/home");
    }
}
