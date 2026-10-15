package com.prashant1.operarion;

import com.prashant1.Connection.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class ReturnBookOperation {

    public void returnBook(int issueId) {

        String findIssue =
                "SELECT book_id FROM Issue_Book "
                        + "WHERE issue_id = ? AND status = 'ISSUED' FOR UPDATE";

        String updateIssue =
                "UPDATE Issue_Book "
                        + "SET status = 'RETURNED', return_date = ? "
                        + "WHERE issue_id = ? AND status = 'ISSUED'";

        String updateBook =
                "UPDATE Book SET quantity = quantity + 1 "
                        + "WHERE book_id = ?";

        try (Connection con = MySQLConnection.getConnection()) {

            con.setAutoCommit(false);

            try {

                int bookId;

                // Find active issue.
                try (PreparedStatement ps =
                             con.prepareStatement(findIssue)) {

                    ps.setInt(1, issueId);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (!rs.next()) {
                            throw new SQLException(
                                    "Active issue not found. "
                                            + "The book may already be returned."
                            );
                        }

                        bookId = rs.getInt("book_id");
                    }
                }

                // Update issue status and return date.
                try (PreparedStatement ps =
                             con.prepareStatement(updateIssue)) {

                    ps.setDate(1, new Date(System.currentTimeMillis()));
                    ps.setInt(2, issueId);

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException(
                                "Unable to update issue record."
                        );
                    }
                }

                // Increase available quantity.
                try (PreparedStatement ps =
                             con.prepareStatement(updateBook)) {

                    ps.setInt(1, bookId);

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException(
                                "Unable to update book quantity."
                        );
                    }
                }

                con.commit();

                System.out.println("Book returned successfully.");

            } catch (SQLException e) {

                con.rollback();

                System.out.println("Book return failed: " + e.getMessage());

            } finally {

                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}