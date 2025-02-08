package com.example.mvp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompetenceData {
    public List<String> getAllCompetences() {
        String query = "SELECT nom_fr FROM competence";
        List<String> competences = new ArrayList<>();
        try (Connection conn = DbFunctions.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                competences.add(rs.getString("nom_fr"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching competences: " + e.getMessage());
        }
        return competences;
    }
}
