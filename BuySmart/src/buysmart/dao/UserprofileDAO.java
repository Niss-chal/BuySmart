/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.dao;

/**
 *
 * @author loq
 */

import buysmart.database.MysqlConnection1;
import buysmart.model.UserModel;
import java.sql.*;
public class UserprofileDAO {

     public UserModel getUserByEmail(String email) {
    UserModel user = null;
    String query = "SELECT * FROM users WHERE email = ?";

    try (Connection conn = MysqlConnection1.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, email);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                user = new UserModel(
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getString("contact")
                );
            }
        }

    } catch (SQLException e) {
        System.err.println("Error fetching user by email from database.");
        e.printStackTrace();
    }

    return user;
}
     
    public boolean updateUser(UserModel user) {
    String query = "UPDATE users SET username = ?, address = ?, contact = ? WHERE email = ?";
    try (Connection conn = MysqlConnection1.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getAddress());
        stmt.setString(3, user.getContact());
        stmt.setString(4, user.getEmail());
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error updating user in database.");
        e.printStackTrace();
        return false;
    }
}
    
    public boolean changePassword(UserModel user){
        String query ="UPDATE users SET password=? WHERE email=?";
        try(Connection conn =MysqlConnection1.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, user.getPassword());
            stmt.setString(2,user.getEmail());
            return stmt.executeUpdate()>0;            
        }catch(SQLException e){
            System.err.println("Error changing password in database.");
        e.printStackTrace();
        return false;
        }
    }
    public boolean deleteAccount(UserModel user) {
    if (user == null || user.getEmail() == null) {
        System.err.println("Debug: deleteAccount received null user or email.");
        return false;
    }
    Connection conn = null;
    try {
        conn = MysqlConnection1.getConnection();
        conn.setAutoCommit(false); // Start transaction

        // Delete related cart entries first
        String deleteCartQuery = "DELETE FROM cart WHERE user_email = ?";
        try (PreparedStatement cartStmt = conn.prepareStatement(deleteCartQuery)) {
            cartStmt.setString(1, user.getEmail());
            cartStmt.executeUpdate();
        }

        // Delete the user
        String deleteUserQuery = "DELETE FROM users WHERE email = ?";
        try (PreparedStatement userStmt = conn.prepareStatement(deleteUserQuery)) {
            userStmt.setString(1, user.getEmail());
            int rowsAffected = userStmt.executeUpdate();
            conn.commit(); // Commit transaction if successful
            return rowsAffected > 0;
        }

    } catch (SQLException e) {
        System.err.println("Error deleting user in database.");
        e.printStackTrace();
        if (conn != null) {
            try {
                conn.rollback(); // Roll back transaction on error
            } catch (SQLException ex) {
                System.err.println("Error rolling back transaction.");
                ex.printStackTrace();
            }
        }
        return false;
    } finally {
        if (conn != null) {
            try {
                conn.setAutoCommit(true); // Reset auto-commit
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection.");
                e.printStackTrace();
            }
        }
    }
    }
}