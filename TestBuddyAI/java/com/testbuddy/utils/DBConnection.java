package com.testbuddy.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/testbuddy_db";
    private static final String USERNAME = "root";

    public static Connection getConnection() throws SQLException {
        String password = System.getenv("DB_PASSWORD");
        if (password == null || password.isEmpty()) {
            throw new RuntimeException("DB_PASSWORD environment variable not set!");
        }
        return DriverManager.getConnection(URL, USERNAME, password);
    }
}