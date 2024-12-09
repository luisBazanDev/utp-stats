package pe.edu.utp.dwi.hackathon.hackathondwi.dao;

import pe.edu.utp.dwi.hackathon.hackathondwi.domain.upload.StudentDataImport;
import pe.edu.utp.dwi.hackathon.hackathondwi.dto.EvaluationData;
import pe.edu.utp.dwi.hackathon.hackathondwi.dto.QuestionData;
import pe.edu.utp.dwi.hackathon.hackathondwi.dto.StudentPoints;

import java.util.Date;
import java.util.List;

public interface IEvaluationDAO {
    List<EvaluationData> getAllEvaluationsOf(String sectionId);
    List<QuestionData> getAllQuestionsOf(int evaluationId);
    List<StudentPoints> getAllStudentPointsOf(int evaluationId);
    void deleteEvaluation(int evaluationId);
    Integer insertEvaluationAndSection(String sectionId, String sectionName, String evaluationName, Date date, double minimumToApprove);
    Integer insertQuestion(int evaluationId, int number, double value);
    void setStudentWithNotes(int evaluationId, StudentDataImport student);
}
