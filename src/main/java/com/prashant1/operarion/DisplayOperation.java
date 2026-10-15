package com.prashant1.operarion;

import com.prashant1.Connection.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DisplayOperation {

    // 1. Display Available Books
    public void displayAvailableBooks() {

        String sql =
                "SELECT * FROM Book WHERE quantity > 0 ORDER BY book_id";

        try (Connection con = MySQLConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n========== AVAILABLE BOOKS ==========");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println(
                        rs.getInt("book_id") + " | "
                                + rs.getString("book_name") + " | "
                                + rs.getString("author_name") + " | Available: "
                                + rs.getInt("quantity")
                );
            }

            if (!found) {
                System.out.println("No books are currently available.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 2. Display Issued Books
    public void displayIssuedBooks() {

        String sql = """
                SELECT i.issue_id, b.book_name, m.member_name,
                       i.issue_date, i.status
                FROM Issue_Book i
                JOIN Book b ON i.book_id = b.book_id
                JOIN Member m ON i.member_id = m.member_id
                WHERE i.status = 'ISSUED'
                ORDER BY i.issue_id
                """;

        displayIssueRecords(sql, "ISSUED BOOKS");
    }

    // 3. Display Returned Books
    public void displayReturnedBooks() {

        String sql = """
                SELECT i.issue_id, b.book_name, m.member_name,
                       i.issue_date, i.status, i.return_date
                FROM Issue_Book i
                JOIN Book b ON i.book_id = b.book_id
                JOIN Member m ON i.member_id = m.member_id
                WHERE i.status = 'RETURNED'
                ORDER BY i.issue_id
                """;

        try (Connection con = MySQLConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n========== RETURNED BOOKS ==========");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("Issue ID: " + rs.getInt("issue_id"));
                System.out.println("Book: " + rs.getString("book_name"));
                System.out.println("Member: " + rs.getString("member_name"));
                System.out.println("Issue Date: " + rs.getDate("issue_date"));
                System.out.println("Return Date: " + rs.getDate("return_date"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("-----------------------------------");
            }

            if (!found) {
                System.out.println("No returned books found.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void displayIssueRecords(String sql, String heading) {

        try (Connection con = MySQLConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n========== " + heading + " ==========");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("Issue ID: " + rs.getInt("issue_id"));
                System.out.println("Book: " + rs.getString("book_name"));
                System.out.println("Member: " + rs.getString("member_name"));
                System.out.println("Issue Date: " + rs.getDate("issue_date"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("-----------------------------------");
            }

            if (!found) {
                System.out.println("No records found.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}