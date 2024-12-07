package pe.edu.utp.dwi.hackathon.hackathondwi.beans;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import pe.edu.utp.dwi.hackathon.hackathondwi.dao.SectionDAO;
import pe.edu.utp.dwi.hackathon.hackathondwi.dto.SectionData;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class SectionBean implements Serializable {
    private List<SectionData> sections;

    public SectionBean() {
        sections = new SectionDAO().getAllSections();
    }

    public List<SectionData> getSections() {
        return this.sections;
    }

    public void setSections(List<SectionData> sections) {
        System.out.println("was");
        this.sections = sections;
    }
}
