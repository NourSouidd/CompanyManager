package com.example.mvp;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.util.List;

public class PersonnelController {
    @FXML
    private VBox mainLayout;
    @FXML
    private ListView<String> employeeListView;

    private PersonnelData employeeD = new PersonnelData();

    public void initialize() {
        List<Personnel> employees = employeeD.getAllEmployees();

        // Add employee names to ListView
        for (Personnel employee : employees) {
            employeeListView.getItems().add(employee.getId() + " - " + employee.getPrenom() + " | " + employee.getNom() + " | " + employee.getDateEntree());
        }
    }
}
