package com.prashant1.operarion;



import com.prashant1.Connection.MySQLConnection;
import com.prashant1.Model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberOperation {

    // 1. Add Member
    public void addMember(Member member) {

        String sql = """
                INSERT INTO Member
                (member_name, email, mobile, address)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection con = MySQLConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, member.getMemberName());
            ps.setString(2, member.getEmail());
            ps.setString(3, member.getMobile());
            ps.setString(4, member.getAddress());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Member added successfully.");
            }

        } catch (SQLException e) {
            System.out.println("Error adding member: " + e.getMessage());
        }
    }

    // 2. Display Members
    public void displayMembers() {

        String sql = "SELECT * FROM Member ORDER BY member_id";

        try (Connection con = MySQLConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n========== MEMBER LIST ==========");

            while (rs.next()) {

                System.out.println("Member ID: "
                        + rs.getInt("member_id"));
                System.out.println("Name: "
                        + rs.getString("member_name"));
                System.out.println("Email: "
                        + rs.getString("email"));
                System.out.println("Mobile: "
                        + rs.getString("mobile"));
                System.out.println("Address: "
                        + rs.getString("address"));

                System.out.println("-------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error displaying members: " + e.getMessage());
        }
    }

    // 3. Search Member
    public void searchMember(int memberId) {

        String sql = "SELECT * FROM Member WHERE member_id = ?";

        try (Connection con = MySQLConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, memberId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    System.out.println("Member ID: "
                            + rs.getInt("member_id"));
                    System.out.println("Name: "
                            + rs.getString("member_name"));
                    System.out.println("Email: "
                            + rs.getString("email"));
                    System.out.println("Mobile: "
                            + rs.getString("mobile"));
                    System.out.println("Address: "
                            + rs.getString("address"));

                } else {
                    System.out.println("Member not found.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error searching member: " + e.getMessage());
        }
    }

    // 4. Update Member
    public void updateMember(Member member) {

        String sql = """
                UPDATE Member
                SET member_name = ?, email = ?,
                    mobile = ?, address = ?
                WHERE member_id = ?
                """;

        try (Connection con = MySQLConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, member.getMemberName());
            ps.setString(2, member.getEmail());
            ps.setString(3, member.getMobile());
            ps.setString(4, member.getAddress());
            ps.setInt(5, member.getMemberId());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Member updated successfully.");
            } else {
                System.out.println("Member not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error updating member: " + e.getMessage());
        }
    }

    // 5. Delete Member
    public void deleteMember(int memberId) {

        String sql = "DELETE FROM Member WHERE member_id = ?";

        try (Connection con = MySQLConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, memberId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Member deleted successfully.");
            } else {
                System.out.println("Member not found.");
            }

        } catch (SQLException e) {
            System.out.println(
                    "Cannot delete member. The member may have issue history. "
                            + e.getMessage()
            );
        }
    }
}


