package pe.edu.utp.dwi.hackathon.hackathondwi.dao;

import pe.edu.utp.dwi.hackathon.hackathondwi.dto.EvaluationData;
import pe.edu.utp.dwi.hackathon.hackathondwi.dto.QuestionData;
import pe.edu.utp.dwi.hackathon.hackathondwi.dto.StudentPoints;

import java.util.List;

public interface IEvaluationDAO {
    List<EvaluationData> getAllEvaluationsOf(String sectionId);
    List<QuestionData> getAllQuestionsOf(int evaluationId);
    List<StudentPoints> getAllStudentPointsOf(int evaluationId);
    void deleteEvaluation(int evaluationId);
}
