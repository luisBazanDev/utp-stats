package pe.edu.utp.dwi.hackathon.hackathondwi.dto;

public class QuestionData {
    private int id;
    private int number;
    private double value;

    public QuestionData(int id, int number, double value) {
        this.id = id;
        this.number = number;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
