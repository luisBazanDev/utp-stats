package pe.edu.utp.dwi.hackathon.hackathondwi.dto;

public class StudentPoints {
    private int studentId;
    private String name;
    private int number;
    private Double points;

    public StudentPoints(int studentId, String name, int number, Double points) {
        this.studentId = studentId;
        this.name = name;
        this.number = number;
        this.points = points == null ? 0.0 : points;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }
}
