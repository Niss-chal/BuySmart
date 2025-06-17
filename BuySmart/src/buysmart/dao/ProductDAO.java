/*
 * Click nbfs://SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.dao;

import buysmart.database.MysqlConnection1;
import buysmart.model.ProductModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fahmi
 */
public class ProductDAO {
    
    public static List<ProductModel> getProduct() throws SQLException {
        String sql = "SELECT image_path, description, price FROM products"; 
        List<ProductModel> products = new ArrayList<>();
        try (Connection conn = MysqlConnection1.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                products.add(new ProductModel(
                    rs.getString("image_path"),
                    rs.getString("description"),
                    rs.getDouble("price")
                ));
            }
            return products;
        } catch (SQLException e) {
            System.err.println("Database Error in getProducts: " + e.getMessage());
            throw e;
        }
    }
    
   public static void addToCart(String description, double price) throws SQLException {
    System.out.println("Attempting to add to cart: " + description + ", " + price);
    String sql = "INSERT INTO cart (description, price) VALUES (?, ?)";
    Connection conn = null;
    try {
        conn = MysqlConnection1.getConnection();
        System.out.println("Connection established: " + (conn != null));
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, description);
            pstmt.setDouble(2, price);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Rows affected in cart: " + rowsAffected);
        }
    } catch (SQLException e) {
        System.err.println("Database Error in addToCart: " + e.getMessage());
        throw e;
    } finally {
        if (conn != null) try { conn.close(); } catch (SQLException e) { /* ignore */ }
    }
}
    
    public static List<ProductModel> getCartItems() throws SQLException {
        String sql = "SELECT description, price FROM cart";
        List<ProductModel> cartItems = new ArrayList<>();
        try (Connection conn = MysqlConnection1.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                cartItems.add(new ProductModel(
                    null, // image_path not needed for cart
                    rs.getString("description"),
                    rs.getDouble("price")
                ));
            }
            return cartItems;
        } catch (SQLException e) {
            System.err.println("Database Error in getCartItems: " + e.getMessage());
            throw e;
        }
    }
}