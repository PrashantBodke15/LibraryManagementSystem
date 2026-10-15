
package com.prashant1.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/library_management";

    private static final String USER = "root";

    // Replace with your actual MySQL password.
    private static final String PASSWORD = "12345678";

    public static Connection getConnection() {

        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    URL, USER, PASSWORD
            );
            System.out.println("Connection Successful");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection not Successful");
            e.printStackTrace();
        }

        return con;
    }
}

