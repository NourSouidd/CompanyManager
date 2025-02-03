package com.example.mvp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbFunctions {
    private static final String URL = "jdbc:postgresql://localhost:5432/company";
    private static final String USER = "admin@admin.com";
    private static final String PASSWORD = "admin";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}


