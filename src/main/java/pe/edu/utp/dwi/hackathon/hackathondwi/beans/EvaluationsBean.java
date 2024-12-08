package pe.edu.utp.dwi.hackathon.hackathondwi.beans;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import pe.edu.utp.dwi.hackathon.hackathondwi.dao.EvaluationDAO;
import pe.edu.utp.dwi.hackathon.hackathondwi.domain.Evaluation;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class EvaluationsBean implements Serializable {
    private List<Evaluation> evaluations;

    public EvaluationsBean() {
        String sectionId = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("sectionId");

        this.evaluations = new EvaluationDAO().getAllEvaluationsOf(sectionId).stream().map(Evaluation::new).collect(Collectors.toList());
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void deleteEvaluation(int evaluationId, String sectionId) {
        new EvaluationDAO().deleteEvaluation(evaluationId);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/data/"+sectionId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
