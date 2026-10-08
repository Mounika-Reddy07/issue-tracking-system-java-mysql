package com.mounika.issuetracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    // For a local learning project, set these environment variables before running.
    // Do not commit real passwords to a public GitHub repository.
    private static final String URL = System.getenv().getOrDefault(
            "ISSUE_DB_URL", "jdbc:mysql://localhost:3306/issue_tracker");
    private static final String USER = System.getenv().getOrDefault("ISSUE_DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("ISSUE_DB_PASSWORD", "");

    private Database() { }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
