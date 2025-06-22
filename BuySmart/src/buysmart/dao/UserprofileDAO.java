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
            System.err.println("Error updating user in database.");
        e.printStackTrace();
        return false;
        }
    }
    
    }
    

