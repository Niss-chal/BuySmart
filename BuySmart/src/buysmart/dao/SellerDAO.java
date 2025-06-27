/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.dao;

/**
 *
 * @author batas
 */


import buysmart.database.MysqlConnection1;
import buysmart.model.SellerModel;
import java.sql.*;

public class SellerDAO {

    // Register seller
    public boolean registerSeller(SellerModel seller) throws SQLException {
        String sql = "INSERT INTO sellers (email, business_name, business_address, contact_number, pan_number, business_type, password) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = MysqlConnection1.getConnection()) {
            conn.setAutoCommit(false); // Begin transaction

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, seller.getEmail());
                pstmt.setString(2, seller.getBusinessName());
                pstmt.setString(3, seller.getBusinessAddress());
                pstmt.setString(4, seller.getContactNumber());
                pstmt.setString(5, seller.getPanNumber());
                pstmt.setString(6, seller.getBusinessType());
                pstmt.setString(7, seller.getPassword()); 

                int rowsAffected = pstmt.executeUpdate();
                conn.commit(); // Commit only if insert succeeds

                return rowsAffected > 0;
            } catch (SQLException e) {
                conn.rollback(); // Rollback if anything fails
                throw e;
            }
        }
    }

    // Login seller using email, business name, and password
    public SellerModel loginSeller(String email, String businessName, String password) throws SQLException {
        String query = "SELECT * FROM sellers WHERE email = ? AND business_name = ? AND password = ?";

        try (Connection conn = MysqlConnection1.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, email);
            ps.setString(2, businessName);
            ps.setString(3, password); 

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new SellerModel(
                        rs.getString("email"),
                        rs.getString("business_name"),
                        rs.getString("business_address"),
                        rs.getString("contact_number"),
                        rs.getString("pan_number"),
                        rs.getString("business_type"),
                        rs.getString("password")
                    );
                }
            }
        }

        return null; // Login failed
    }
}

