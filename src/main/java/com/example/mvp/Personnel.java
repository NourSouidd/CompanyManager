package com.example.mvp;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.property.*;


public class Personnel {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty prenom;
    private final SimpleStringProperty nom;
    private final SimpleObjectProperty<Date> dateEntree;

    public Personnel(int id, String prenom, String nom, Date dateEntree) {
        this.id = new SimpleIntegerProperty(id);
        this.prenom = new SimpleStringProperty(prenom);
        this.nom = new SimpleStringProperty(nom);
        this.dateEntree = new SimpleObjectProperty<>(dateEntree);
    }

    public IntegerProperty idProperty() {return id;}

    public StringProperty prenomProperty() {return prenom;}

    public StringProperty nomProperty() {return nom;}

    public ObjectProperty<Date> dateEntreeProperty() {return dateEntree;}



//    public Personnel(String nom, String prenom, Date dateEntree) {
//        this.id++;
//        this.prenom = new SimpleStringProperty(prenom);
//        this.nom = new SimpleStringProperty(nom);
//        this.dateEntree = new SimpleObjectProperty<>(dateEntree);
//    }

    public int getId() { return id.get(); }
    public String getNom() { return nom.get(); }
    public String getPrenom() { return prenom.get(); }
    public Date getDateEntree() { return dateEntree.get(); }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
        PersonnelData.updateEmployeeField(this.getId(), "prenom", prenom);


    }

    public void setNom(String nom) {
        this.nom.set(nom);
        PersonnelData.updateEmployeeField(this.getId(), "nom", nom);
    }

    public void setDateEntree(Date dateEntree) {
        this.dateEntree.set(dateEntree);
        PersonnelData.updateEmployeeField(this.getId(), "date_entree", dateEntree);
    }

}
