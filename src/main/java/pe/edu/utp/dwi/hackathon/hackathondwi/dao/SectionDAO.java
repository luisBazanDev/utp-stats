package pe.edu.utp.dwi.hackathon.hackathondwi.dao;

import pe.edu.utp.dwi.hackathon.hackathondwi.dto.SectionData;
import pe.edu.utp.dwi.hackathon.hackathondwi.dto.SectionInfo;
import pe.edu.utp.dwi.hackathon.hackathondwi.singleton.DatabaseConnection;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SectionDAO implements ISectionDAO {
    @Override
    public List<SectionData> getAllSections() {
        String query = "CALL getSections()";
        List<SectionData> sections = new ArrayList<SectionData>();

        try {
            DatabaseConnection db = DatabaseConnection.getInstancia();
            CallableStatement statement = db.getConexion().prepareCall(query);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                sections.add(new SectionData(
                        rs.getString("id"),
                        rs.getString("name")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sections;
    }

    @Override
    public SectionInfo getSectionInfo(String sectionId) {
        String query = "CALL getSectionInfo(?)";

        try {
            DatabaseConnection db = DatabaseConnection.getInstancia();
            CallableStatement statement = db.getConexion().prepareCall(query);
            statement.setString(1, sectionId);
            ResultSet rs = statement.executeQuery();

            if (rs.next())
                return new SectionInfo(
                        rs.getString("section_id"),
                        rs.getString("section_name"),
                        rs.getInt("total_evaluations"),
                        rs.getInt("total_students")
                );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteSection(String sectionId) {
        String query = "CALL deleteSection(?)";

        try {
            DatabaseConnection db = DatabaseConnection.getInstancia();
            CallableStatement statement = db.getConexion().prepareCall(query);
            statement.setString(1, sectionId);
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
