package pe.edu.utp.dwi.hackathon.hackathondwi.dao;

import pe.edu.utp.dwi.hackathon.hackathondwi.dto.SectionData;
import pe.edu.utp.dwi.hackathon.hackathondwi.dto.SectionInfo;

import java.util.List;

public interface ISectionDAO {
    List<SectionData> getAllSections();
    SectionInfo getSectionInfo(String sectionId);
    void deleteSection(String sectionId);
}
