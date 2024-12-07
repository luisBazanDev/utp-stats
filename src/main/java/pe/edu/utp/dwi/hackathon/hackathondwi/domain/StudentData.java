package pe.edu.utp.dwi.hackathon.hackathondwi.domain;

import java.util.ArrayList;

public class StudentData {
    private final int id;
    private final String name;
    private double totalPoints;
    private ArrayList<Double> points;

    public StudentData(int id, String name, ArrayList<Double> points) {
        this.id = id;
        this.name = name;
        this.points = points;
        points.forEach(p -> totalPoints += p);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getTotalPoints() {
        return totalPoints;
    }

    public ArrayList<Double> getPoints() {
        return points;
    }

    public void addPoint(double point) {
        totalPoints += point;
        points.add(point);
    }
}
