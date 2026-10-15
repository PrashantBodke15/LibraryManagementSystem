package com.prashant1.operarion;

import com.prashant1.Connection.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class IssueBookOperation {

    public void issueBook(int bookId, int memberId) {

        String checkBook =
                "SELECT quantity FROM Book WHERE book_id = ? FOR UPDATE";

        String checkMember =
                "SELECT member_id FROM Member WHERE member_id = ?";

        String updateBook =
                "UPDATE Book SET quantity = quantity - 1 "
                        + "WHERE book_id = ? AND quantity > 0";

        String insertIssue =
                "INSERT INTO Issue_Book "
                        + "(book_id, member_id, issue_date, status) "
                        + "VALUES (?, ?, ?, 'ISSUED')";

        try (Connection con = MySQLConnection.getConnection()) {

            con.setAutoCommit(false);

            try {

                // Lock and check book availability.
                try (PreparedStatement ps =
                             con.prepareStatement(checkBook)) {

                    ps.setInt(1, bookId);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (!rs.next()) {
                            throw new SQLException("Book does not exist.");
                        }

                        if (rs.getInt("quantity") <= 0) {
                            throw new SQLException(
                                    "No copies of this book are available."
                            );
                        }
                    }
                }

                // Check member.
                try (PreparedStatement ps =
                             con.prepareStatement(checkMember)) {

                    ps.setInt(1, memberId);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (!rs.next()) {
                            throw new SQLException("Member does not exist.");
                        }
                    }
                }

                // Decrease quantity.
                try (PreparedStatement ps =
                             con.prepareStatement(updateBook)) {

                    ps.setInt(1, bookId);

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException(
                                "Unable to update book quantity."
                        );
                    }
                }

                // Insert issue record.
                try (PreparedStatement ps =
                             con.prepareStatement(insertIssue)) {

                    ps.setInt(1, bookId);
                    ps.setInt(2, memberId);
                    ps.setDate(3, new Date(System.currentTimeMillis()));

                    ps.executeUpdate();
                }

                con.commit();

                System.out.println("Book issued successfully.");

            } catch (SQLException e) {

                con.rollback();

                System.out.println("Book issue failed: " + e.getMessage());

            } finally {

                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
