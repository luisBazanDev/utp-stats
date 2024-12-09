package pe.edu.utp.dwi.hackathon.hackathondwi.domain.upload;

import org.json.JSONArray;

import java.util.ArrayList;

public class StudentDataImport {
    private final String name;
    private ArrayList<Double> points;

    public StudentDataImport(String name, ArrayList<Double> points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Double> getPoints() {
        return points;
    }

    public JSONArray getPointsAsJson() {
        JSONArray jsonArray = new JSONArray();
        for (Double point : points) {
            jsonArray.put(point);
        }
        return jsonArray;
    }

    public void setPoints(ArrayList<Double> points) {
        this.points = points;
    }
}
