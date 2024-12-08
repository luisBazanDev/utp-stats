package pe.edu.utp.dwi.hackathon.hackathondwi.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class RouterBean implements Serializable {
    public RouterBean() {

    }

    public void statsOfSection(String section) {
        FacesContext context = FacesContext.getCurrentInstance();
        // Redirect
        try {
            context.getExternalContext().redirect("/stats/"+section);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void statsOfData(String section) {
        FacesContext context = FacesContext.getCurrentInstance();
        // Redirect
        try {
            context.getExternalContext().redirect("/data/"+section);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
