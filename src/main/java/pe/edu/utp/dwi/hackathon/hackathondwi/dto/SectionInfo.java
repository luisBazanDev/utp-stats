package pe.edu.utp.dwi.hackathon.hackathondwi.dto;

public class SectionInfo {
    private String id;
    private String name;
    private int totalEvaluations;
    private int totalStudents;

    public SectionInfo(String id, String name, int totalEvaluations, int totalStudents) {
        this.id = id;
        this.name = name;
        this.totalEvaluations = totalEvaluations;
        this.totalStudents = totalStudents;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalEvaluations() {
        return totalEvaluations;
    }

    public void setTotalEvaluations(int totalEvaluations) {
        this.totalEvaluations = totalEvaluations;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }
}
