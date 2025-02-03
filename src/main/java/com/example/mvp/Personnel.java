package com.example.mvp;

import java.sql.Date;
import java.time.LocalDate;

public class Personnel {

    private int id;
    private String nom;
    private String prenom;
    private Date dateEntree;

    public Personnel(int id, String nom, String prenom, Date dateEntree) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateEntree = dateEntree;
    }

    public Personnel(String nom, String prenom, Date dateEntree) {

        this.nom = nom;
        this.prenom = prenom;
        this.dateEntree = dateEntree;
        this.id++;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public Date getDateEntree() { return dateEntree; }

    public void setId(int id) { this.id = id; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setNom(String nom) { this.nom = nom; }
    public void setDateEntree(Date dateEntree) { this.dateEntree = dateEntree; }


}
