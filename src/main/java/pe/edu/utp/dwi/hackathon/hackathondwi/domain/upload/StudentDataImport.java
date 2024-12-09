package pe.edu.utp.dwi.hackathon.hackathondwi.domain.upload;

import java.util.ArrayList;

public class StudentDataImport {
    private final String name;
    private double totalPoints;
    private ArrayList<Double> points;

    public StudentDataImport(String name, ArrayList<Double> points) {
        this.name = name;
        points.forEach(p -> this.totalPoints += p);
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public ArrayList<Double> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Double> points) {
        this.points = points;
    }
}
