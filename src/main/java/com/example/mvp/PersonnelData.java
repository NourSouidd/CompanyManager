package com.example.mvp;
import java.sql.*;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PersonnelData {
    public List<Personnel> getAllEmployees() {
        List<Personnel> employees = new ArrayList<>();
        String query = "SELECT * FROM personnel ORDER BY id";

        try (Connection conn = DbFunctions.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("prenom");
                String lastName = rs.getString("nom");
                Date entryDate = rs.getDate("date_entree");

                Personnel employee = new Personnel(id, firstName, lastName, entryDate);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public boolean updateEmployee(int id, String firstName, String lastName, Date entryDate) {
        String query = "UPDATE personnel SET prenom = ?, nom = ?, date_entree = ? WHERE id = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setDate(3, entryDate);
            pstmt.setInt(4, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            // Log the error
            System.err.println("Error updating employee with ID " + id + ": " + e.getMessage());
            // Throw application-specific exception
            return false;

        }
    }

    public static boolean updateEmployeeField(int id, String fieldName, Object fieldValue) {
        String query = "UPDATE personnel SET " + fieldName + " = ? WHERE id = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setObject(1, fieldValue); // Set the value dynamically
            pstmt.setInt(2, id);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(fieldName + " updated successfully for employee ID: " + id);
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating " + fieldName + " for employee ID: " + id);
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEmployee(int id)  {
        String query = "DELETE FROM personnel WHERE id = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getAllEmployeeCompetences(int personnelId) {
        String query = "SELECT c.nom_fr " +
                "FROM Competence c " +
                "INNER JOIN Competences_Personnel cp ON c.id = cp.id_competence " +
                "WHERE cp.id_personnel = ?";
        List<String> competences = new ArrayList<>();

        try (Connection conn = DbFunctions.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, personnelId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                competences.add(rs.getString("nom_fr"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching competences: " + e.getMessage());
        }
        return competences;
    }

    public boolean addPersonnelCompetence(int personnelId, String competenceName) {
        String query = "INSERT INTO Competences_Personnel (id_personnel, id_competence) " +
                "SELECT ?, c.id FROM Competence c WHERE c.nom_fr = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, personnelId);
            stmt.setString(2, competenceName);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
