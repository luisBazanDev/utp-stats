package pe.edu.utp.dwi.hackathon.hackathondwi.domain;

import pe.edu.utp.dwi.hackathon.hackathondwi.dao.EvaluationDAO;
import pe.edu.utp.dwi.hackathon.hackathondwi.dto.EvaluationData;
import pe.edu.utp.dwi.hackathon.hackathondwi.dto.QuestionData;
import pe.edu.utp.dwi.hackathon.hackathondwi.dto.StudentPoints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Evaluation {
    private EvaluationData data;
    private List<QuestionData> questions;
    private HashMap<Integer, StudentData> studentDataHashMap;

    public Evaluation(EvaluationData data) {
        this.data = data;
        this.studentDataHashMap = new HashMap<>();

        EvaluationDAO dao = new EvaluationDAO();
        this.questions = dao.getAllQuestionsOf(data.getId());

        for (StudentPoints studentPoints : dao.getAllStudentPointsOf(data.getId())) {
            StudentData studentData = studentDataHashMap.get(studentPoints.getStudentId());

            if(studentData != null) {
                studentData.addPoint(studentPoints.getPoints());
            } else {
                studentDataHashMap.put(
                        studentPoints.getStudentId(),
                        new StudentData(
                                studentPoints.getStudentId(),
                                studentPoints.getName(),
                                new ArrayList<>(List.of(studentPoints.getPoints()))
                        )
                );
            }
        }
    }

    public EvaluationData getData() {
        return data;
    }

    public List<QuestionData> getQuestions() {
        return questions;
    }

    public HashMap<Integer, StudentData> getStudentDataHashMap() {
        return studentDataHashMap;
    }
}
