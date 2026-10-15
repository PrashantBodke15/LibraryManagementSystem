package com.prashant1.operarion;

import com.prashant1.Connection.MySQLConnection;
import com.prashant1.Model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookOperation {

    // 1. Add Book
    public void addBook(Book book) {

        String sql = """
                INSERT INTO Book
                (book_name, author_name, publisher, price, quantity)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection con = MySQLConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, book.getBookName());
            ps.setString(2, book.getAuthorName());
            ps.setString(3, book.getPublisher());
            ps.setDouble(4, book.getPrice());
            ps.setInt(5, book.getQuantity());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Book added successfully.");
            }

        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    // 2. Display All Books
    public void displayBooks() {

        String sql = "SELECT * FROM Book ORDER BY book_id";

        try (Connection con = MySQLConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n========== BOOK LIST ==========");

            while (rs.next()) {

                System.out.println("Book ID: " + rs.getInt("book_id"));
                System.out.println("Book Name: " + rs.getString("book_name"));
                System.out.println("Author: " + rs.getString("author_name"));
                System.out.println("Publisher: " + rs.getString("publisher"));
                System.out.println("Price: " + rs.getDouble("price"));
                System.out.println("Available Quantity: "
                        + rs.getInt("quantity"));

                System.out.println("-------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error displaying books: " + e.getMessage());
        }
    }

    // 3. Search Book
    public void searchBook(int bookId) {

        String sql = "SELECT * FROM Book WHERE book_id = ?";

        try (Connection con = MySQLConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    System.out.println("Book ID: " + rs.getInt("book_id"));
                    System.out.println("Book Name: "
                            + rs.getString("book_name"));
                    System.out.println("Author: "
                            + rs.getString("author_name"));
                    System.out.println("Publisher: "
                            + rs.getString("publisher"));
                    System.out.println("Price: " + rs.getDouble("price"));
                    System.out.println("Quantity: "
                            + rs.getInt("quantity"));

                } else {
                    System.out.println("Book not found.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error searching book: " + e.getMessage());
        }
    }

    // 4. Update Book
    public void updateBook(Book book) {

        String sql = """
                UPDATE Book
                SET book_name = ?, author_name = ?,
                    publisher = ?, price = ?, quantity = ?
                WHERE book_id = ?
                """;

        try (Connection con = MySQLConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, book.getBookName());
            ps.setString(2, book.getAuthorName());
            ps.setString(3, book.getPublisher());
            ps.setDouble(4, book.getPrice());
            ps.setInt(5, book.getQuantity());
            ps.setInt(6, book.getBookId());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Book updated successfully.");
            } else {
                System.out.println("Book not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error updating book: " + e.getMessage());
        }
    }

    // 5. Delete Book
    public void deleteBook(int bookId) {

        String sql = "DELETE FROM Book WHERE book_id = ?";

        try (Connection con = MySQLConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Book deleted successfully.");
            } else {
                System.out.println("Book not found.");
            }

        } catch (SQLException e) {
            System.out.println(
                    "Cannot delete book. It may have issue history. "
                            + e.getMessage()
            );
        }
    }
}