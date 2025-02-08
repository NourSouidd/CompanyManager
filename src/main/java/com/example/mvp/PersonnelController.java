package com.example.mvp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.layout.FlowPane;



import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class PersonnelController {
    @FXML
    private VBox mainLayout;
    @FXML
    private TableView<Personnel> personnelTableView;

    @FXML
    private TableColumn<Personnel, Integer> idColumn;

    @FXML
    private TableColumn<Personnel, String> prenomColumn;

    @FXML
    private TableColumn<Personnel, String> nomColumn;

    @FXML
    private TableColumn<Personnel, Date> dateColumn;

    //    @FXML
//    private TableColumn<Personnel, Void> editColumn;
//
    @FXML
    private TableColumn<Personnel, Void> competencesColumn;

    @FXML
    private FlowPane competencesPane;

    @FXML
    private TableColumn<Personnel, Void> deleteColumn;
    @FXML
    private Button addCompetenceButton;


    private PersonnelData employeeD = new PersonnelData();
    private CompetenceData competenceD = new CompetenceData();


    public void initialize() {
        personnelTableView.setEditable(true);
        loadPersonnel();
        editPersonnel();
        showCompetenceButton();
        deleteButton();
        personnelTableView.refresh();


    }

    public void loadPersonnel() {
        // Fetch all employees
        List<Personnel> employees = employeeD.getAllEmployees();
        ObservableList<Personnel> personnels = FXCollections.observableArrayList(employees);
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateEntreeProperty());
        personnelTableView.setItems(personnels);
    }

    private boolean validateStringInput(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public void editPersonnel() {
        // Make the prenom column editable
        prenomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        prenomColumn.setOnEditCommit(event -> {
            Personnel person = event.getRowValue();
            String newFirstName = event.getNewValue();

            if (validateStringInput(newFirstName)) {
                person.setPrenom(newFirstName);
                boolean success = employeeD.updateEmployee(person.getId(), person.getPrenom(), person.getNom(), person.getDateEntree());
                if (!success) {
                    System.out.println("Failed to update first name for employee with ID: " + person.getId());
                }

            } else {
                System.out.println("Invalid first name. Update aborted.");
            }

        });

        // Make the nom column editable
        nomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nomColumn.setOnEditCommit(event -> {
            Personnel person = event.getRowValue();
            String newLastName = event.getNewValue();

            if (validateStringInput(newLastName)) {
                person.setNom(newLastName);

                boolean success = employeeD.updateEmployee(person.getId(), person.getPrenom(), person.getNom(), person.getDateEntree());
                if (!success) {
                    System.out.println("Failed to update last name for employee with ID: " + person.getId());
                }
            } else {
                System.out.println("Invalid last name. Update aborted.");
            }
        });
        // Make the date column editable
        dateColumn.setCellFactory(column -> new TableCell<>() {
            private final DatePicker datePicker = new DatePicker();

            {
                datePicker.setOnAction(event -> {
                    Personnel person = getTableView().getItems().get(getIndex());
                    LocalDate newDate = datePicker.getValue();

                    if (newDate != null) {
                        person.setDateEntree(Date.valueOf(newDate));
                        // Update the database
                        boolean success = employeeD.updateEmployee(person.getId(), person.getPrenom(), person.getNom(), person.getDateEntree());
                        if (!success) {
                            System.out.println("Failed to update entry date for employee with ID: " + person.getId());
                        }
                        commitEdit(Date.valueOf(newDate));
                    }
                });
            }

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(datePicker);
                    datePicker.setValue(item != null ? item.toLocalDate() : null);
                }
            }
        });

    }

    private void deleteButton() {
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Personnel person = getTableView().getItems().get(getIndex());

                    if (confirmDelete()) {
                        boolean success = employeeD.deleteEmployee(person.getId());
                        if (success) {
                            getTableView().getItems().remove(person);
                            System.out.println("Deleted personnel with ID: " + person.getId());
                        } else {
                            System.out.println("Failed to delete personnel with ID: " + person.getId());
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    private boolean confirmDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Delete Personnel");
        alert.setContentText("Are you sure you want to delete this record?");

        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }

    private void showCompetenceButton() {
        competencesColumn.setCellFactory(param -> new TableCell<>() {
            private final Button competencesButton = new Button("Competences");

            {
                competencesButton.setOnAction(event -> {
                    Personnel person = getTableView().getItems().get(getIndex());
                    if (person != null) {
                        List<String> competences = employeeD.getAllEmployeeCompetences(person.getId());

                        displayPersonnelCompetences(person.getId());
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(competencesButton);
                }
            }
        });
    }

    public void displayPersonnelCompetences(int personnelId) {
        List<String> competences = employeeD.getAllEmployeeCompetences(personnelId);

        competencesPane.getChildren().clear();

        for (String competence : competences) {
            Label competenceLabel = new Label(competence);
            competenceLabel.setStyle("-fx-background-color: #EAEAEA; -fx-border-color: #CCCCCC; " +
                    "-fx-padding: 5px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            competencesPane.getChildren().add(competenceLabel);
        }


        addCompetenceButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 5px;");

        addCompetenceButton.setOnAction(event -> {
            addCompetenceList(personnelId);
        });

    }

    private void addCompetenceList(int personnelId) {
        List<String> assignedCompetences = employeeD.getAllEmployeeCompetences(personnelId);
        List<String> allCompetences = competenceD.getAllCompetences();

        List<String> availableCompetences = allCompetences.stream()
                .filter(competence -> !assignedCompetences.contains(competence))
                .toList();

        if (availableCompetences.isEmpty()) {
            showErrorAlert("No new competences available to add.");

            return;
        }

        ContextMenu contextMenu = new ContextMenu();

        for (String competence : availableCompetences) {
            MenuItem menuItem = new MenuItem(competence);
            menuItem.setOnAction(event -> {
                if (employeeD.addPersonnelCompetence(personnelId, competence)) {
//                    showSuccessAlert();
                    displayPersonnelCompetences(personnelId);
                } else {
                    showErrorAlert("Failed to add competence.");
                }
            });
            contextMenu.getItems().add(menuItem);
        }

        // Show the dropdown next to the "Add Competence" button
        contextMenu.show(competencesPane, Side.BOTTOM, 0, 0);
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Competence added successfully.");
        alert.showAndWait();
    }



}
