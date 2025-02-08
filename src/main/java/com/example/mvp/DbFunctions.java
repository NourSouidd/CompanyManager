package com.example.mvp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbFunctions {
    private static final String URL = "jdbc:postgresql://localhost:5432/company";
            //System.getenv("DB_URL");

    private static final String USER = "admin@admin.com";
            //System.getenv("DB_USER");


    private static final String PASSWORD = "admin";
            //System.getenv("DB_PASSWORD");

    //

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}


