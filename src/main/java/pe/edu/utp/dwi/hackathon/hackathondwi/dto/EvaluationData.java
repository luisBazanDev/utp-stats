package pe.edu.utp.dwi.hackathon.hackathondwi.dto;

import org.json.JSONObject;

import java.util.Date;

public class EvaluationData {
    private int id;
    private String name;
    private int questionsCount;
    private double minPoints;
    private Date date;
    private double averageStudentsPoints;

    public EvaluationData(int id, String name, int questionsCount, double minPoints, Date date, double averageStudentsPoints) {
        this.id = id;
        this.name = name;
        this.questionsCount = questionsCount;
        this.minPoints = minPoints;
        this.date = date;
        this.averageStudentsPoints = averageStudentsPoints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuestionsCount() {
        return questionsCount;
    }

    public void setQuestionsCount(int questionsCount) {
        this.questionsCount = questionsCount;
    }

    public double getMinPoints() {
        return minPoints;
    }

    public void setMinPoints(double minPoints) {
        this.minPoints = minPoints;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAverageStudentsPoints() {
        return averageStudentsPoints;
    }

    public void setAverageStudentsPoints(double averageStudentsPoints) {
        this.averageStudentsPoints = averageStudentsPoints;
    }

    public JSONObject toJSON() {
        return new JSONObject(this);
    }
}
