package pe.edu.utp.dwi.hackathon.hackathondwi.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named
@SessionScoped
public class FormatBean implements Serializable {
    public FormatBean() {}

    public String format2Digits(int number) {
        return String.format("%02d", number);
    }

    public String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }

    public String formatPoints(double points) {
        return String.format("%02.2f", points);
    }
}
