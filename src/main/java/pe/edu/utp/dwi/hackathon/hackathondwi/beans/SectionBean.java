package pe.edu.utp.dwi.hackathon.hackathondwi.beans;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import pe.edu.utp.dwi.hackathon.hackathondwi.dao.SectionDAO;
import pe.edu.utp.dwi.hackathon.hackathondwi.dto.SectionData;
import pe.edu.utp.dwi.hackathon.hackathondwi.dto.SectionInfo;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class SectionBean implements Serializable {
    private List<SectionData> sections;
    private SectionInfo sectionInfo;

    public SectionBean() {
        sections = new SectionDAO().getAllSections();

        String sectionId = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("sectionId");

        if(sectionId != null) {
            loadSectionInfo(sectionId);
        }
    }

    public List<SectionData> getSections() {
        return this.sections;
    }

    public void setSections(List<SectionData> sections) {
        this.sections = sections;
    }

    private void loadSectionInfo(String id) {
        this.sectionInfo = new SectionDAO().getSectionInfo(id);
    }

    public void setSectionInfo(SectionInfo sectionInfo) {
        this.sectionInfo = sectionInfo;
    }

    public SectionInfo getSectionInfo() {
        return sectionInfo;
    }

    public void deleteSection() {
        new SectionDAO().deleteSection(sectionInfo.getId());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
