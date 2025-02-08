package com.example.mvp;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Competence {
    private final SimpleStringProperty id;
    private final SimpleStringProperty nom_en;
    private final SimpleStringProperty nom_fr;

    public Competence(String id, String nom_en, String nom_fr) {
        this.id = new SimpleStringProperty(id);
        this.nom_en = new SimpleStringProperty(nom_en);
        this.nom_fr = new SimpleStringProperty(nom_fr);
    }

    public String getId() { return id.get(); }
    public String getNomEn() { return nom_en.get(); }
    public String getNomFr() { return nom_fr.get(); }
}
