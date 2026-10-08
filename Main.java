package com.mounika.issuetracker;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Issue Tracking System ===");
        System.out.println("Before running: create the database using sql/schema.sql and set DB credentials.");
        try (Connection connection = Database.getConnection()) {
            boolean running = true;
            while (running) {
                printMenu();
                String choice = scanner.nextLine().trim();
                try {
                    switch (choice) {
                        case "1" -> createIssue(connection);
                        case "2" -> listIssues(connection);
                        case "3" -> updateIssueStatus(connection);
                        case "4" -> updateIssuePriority(connection);
                        case "5" -> running = false;
                        default -> System.out.println("Please choose a number from 1 to 5.");
                    }
                } catch (SQLException ex) {
                    System.out.println("Database operation failed: " + ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Could not connect to MySQL. Check DB settings and make sure MySQL is running.");
            System.out.println("Details: " + ex.getMessage());
        }
        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\n1. Create issue");
        System.out.println("2. View all issues");
        System.out.println("3. Update issue status");
        System.out.println("4. Update issue priority");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    private static void createIssue(Connection connection) throws SQLException {
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        if (title.isBlank()) {
            System.out.println("Title cannot be empty.");
            return;
        }
        System.out.print("Description: ");
        String description = scanner.nextLine().trim();
        System.out.print("Priority (LOW/MEDIUM/HIGH): ");
        String priority = scanner.nextLine().trim().toUpperCase();
        if (!isOneOf(priority, "LOW", "MEDIUM", "HIGH")) {
            System.out.println("Invalid priority. Use LOW, MEDIUM, or HIGH.");
            return;
        }

        String sql = "INSERT INTO issues (title, description, priority, status) VALUES (?, ?, ?, 'OPEN')";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, priority);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) System.out.println("Issue created. ID: " + keys.getInt(1));
                else System.out.println("Issue created.");
            }
        }
    }

    private static void listIssues(Connection connection) throws SQLException {
        String sql = "SELECT id, title, priority, status, created_at FROM issues ORDER BY id DESC";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            System.out.println("\nID | PRIORITY | STATUS | TITLE | CREATED");
            System.out.println("---------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %s | %s%n",
                        rs.getInt("id"), rs.getString("priority"), rs.getString("status"),
                        rs.getString("title"), rs.getTimestamp("created_at"));
            }
        }
    }

    private static void updateIssueStatus(Connection connection) throws SQLException {
        System.out.print("Issue ID: ");
        int id = readPositiveInt();
        System.out.print("New status (OPEN/IN_PROGRESS/RESOLVED): ");
        String status = scanner.nextLine().trim().toUpperCase();
        if (!isOneOf(status, "OPEN", "IN_PROGRESS", "RESOLVED")) {
            System.out.println("Invalid status.");
            return;
        }
        updateField(connection, "status", status, id);
    }

    private static void updateIssuePriority(Connection connection) throws SQLException {
        System.out.print("Issue ID: ");
        int id = readPositiveInt();
        System.out.print("New priority (LOW/MEDIUM/HIGH): ");
        String priority = scanner.nextLine().trim().toUpperCase();
        if (!isOneOf(priority, "LOW", "MEDIUM", "HIGH")) {
            System.out.println("Invalid priority.");
            return;
        }
        updateField(connection, "priority", priority, id);
    }

    private static void updateField(Connection connection, String field, String value, int id) throws SQLException {
        // field is selected internally from fixed values above, never directly from user input.
        String sql = "UPDATE issues SET " + field + " = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, value);
            ps.setInt(2, id);
            int updated = ps.executeUpdate();
            System.out.println(updated == 1 ? "Issue updated." : "No issue found with that ID.");
        }
    }

    private static int readPositiveInt() {
        while (true) {
            String raw = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(raw);
                if (value > 0) return value;
            } catch (NumberFormatException ignored) { }
            System.out.print("Enter a valid positive numeric ID: ");
        }
    }

    private static boolean isOneOf(String value, String... allowed) {
        for (String option : allowed) if (option.equals(value)) return true;
        return false;
    }
}
