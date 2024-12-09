package pe.edu.utp.dwi.hackathon.hackathondwi.dao;

import pe.edu.utp.dwi.hackathon.hackathondwi.domain.upload.StudentDataImport;
import pe.edu.utp.dwi.hackathon.hackathondwi.dto.EvaluationData;
import pe.edu.utp.dwi.hackathon.hackathondwi.dto.QuestionData;
import pe.edu.utp.dwi.hackathon.hackathondwi.dto.StudentPoints;
import pe.edu.utp.dwi.hackathon.hackathondwi.singleton.DatabaseConnection;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EvaluationDAO implements IEvaluationDAO {
    @Override
    public List<EvaluationData> getAllEvaluationsOf(String sectionId) {
        String query = "CALL getEvaluationsOfSection(?)";
        List<EvaluationData> evaluations = new ArrayList<EvaluationData>();

        try {
            DatabaseConnection db = DatabaseConnection.getInstancia();
            CallableStatement statement = db.getConexion().prepareCall(query);
            statement.setString(1, sectionId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                evaluations.add(new EvaluationData(
                        rs.getInt("evaluation_id"),
                        rs.getString("evaluation_name"),
                        rs.getInt("question_count"),
                        rs.getDouble("min_points"),
                        rs.getDate("evaluation_date"),
                        rs.getDouble("average_student_score")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return evaluations;
    }

    @Override
    public List<QuestionData> getAllQuestionsOf(int evaluationId) {
        String query = "CALL getQuestionsOfEvaluation(?)";
        List<QuestionData> questions = new ArrayList<>();

        try {
            DatabaseConnection db = DatabaseConnection.getInstancia();
            CallableStatement statement = db.getConexion().prepareCall(query);
            statement.setInt(1, evaluationId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                questions.add(new QuestionData(
                        rs.getInt("question_id"),
                        rs.getInt("question_number"),
                        rs.getDouble("value")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }

    @Override
    public List<StudentPoints> getAllStudentPointsOf(int evaluationId) {
        String query = "CALL getStudentsNotesOfEvaluation(?)";
        List<StudentPoints> studentPoints = new ArrayList<>();

        try {
            DatabaseConnection db = DatabaseConnection.getInstancia();
            CallableStatement statement = db.getConexion().prepareCall(query);
            statement.setInt(1, evaluationId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                studentPoints.add(new StudentPoints(
                        rs.getInt("student_id"),
                        rs.getString("student_name"),
                        rs.getInt("question_number"),
                        rs.getDouble("student_points")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentPoints;
    }

    @Override
    public void deleteEvaluation(int evaluationId) {
        String query = "CALL deleteEvaluation(?)";

        try {
            DatabaseConnection db = DatabaseConnection.getInstancia();
            CallableStatement statement = db.getConexion().prepareCall(query);
            statement.setInt(1, evaluationId);
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer insertEvaluationAndSection(String sectionId, String sectionName, String evaluationName, Date date, double minimumToApprove) {
        String query = "CALL insertOrCreateSectionEvaluation(?, ?, ?, ?, ?)";

        try {
            DatabaseConnection db = DatabaseConnection.getInstancia();
            CallableStatement statement = db.getConexion().prepareCall(query);
            statement.setString(1, sectionId);
            statement.setString(2, sectionName);
            statement.setString(3, evaluationName);
            statement.setDate(4, new java.sql.Date(date.getTime()));
            statement.setDouble(5, minimumToApprove);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt("evaluation_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer insertQuestion(int evaluationId, int number, double value) {
        String query = "CALL insertQuestion(?, ?, ?)";

        try {
            DatabaseConnection db = DatabaseConnection.getInstancia();
            CallableStatement statement = db.getConexion().prepareCall(query);
            statement.setInt(1, evaluationId);
            statement.setInt(2, number);
            statement.setDouble(3, value);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt("question_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setStudentWithNotes(int evaluationId, StudentDataImport student) {
        String query = "CALL setStudentWithNotes(?, ?, ?)";

        try {
            DatabaseConnection db = DatabaseConnection.getInstancia();
            CallableStatement statement = db.getConexion().prepareCall(query);
            statement.setString(1, student.getName());
            statement.setInt(2, evaluationId);
            statement.setString(3, student.getPointsAsJson().toString());
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
