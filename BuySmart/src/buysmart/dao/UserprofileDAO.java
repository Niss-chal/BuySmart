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
     
    public UserModel getUserByUsername(String username) {
        UserModel user = null;
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = MysqlConnection1.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);

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
            System.err.println("Error fetching user from database.");
            e.printStackTrace();
        }

        return user;
    }
    }
    

