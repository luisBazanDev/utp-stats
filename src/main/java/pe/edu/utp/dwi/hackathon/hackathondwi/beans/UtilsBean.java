package pe.edu.utp.dwi.hackathon.hackathondwi.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class UtilsBean implements Serializable {
    public List<Integer> generateNumberList(int size) {
        List<Integer> list = new ArrayList<Integer>();

        for (int i = 1; i <= size; i++) {
            list.add(i);
        }

        return list;
    }
}
