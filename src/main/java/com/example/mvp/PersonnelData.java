package com.example.mvp;
import java.sql.*;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PersonnelData {
    public List<Personnel> getAllEmployees() {
        List<Personnel> employees = new ArrayList<>();
        String query = "SELECT * FROM personnel";  // Adjust the table name if needed

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

}
