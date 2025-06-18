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
    
    public static void addToCart(String description, double price, int quantity) throws SQLException {
        System.out.println("Attempting to add to cart: " + description + ", " + price + ", Quantity: " + quantity);
        String sqlCheck = "SELECT quantity FROM cart WHERE description = ? AND price = ?";
        String sqlUpdate = "UPDATE cart SET quantity = quantity + ? WHERE description = ? AND price = ?";
        String sqlInsert = "INSERT INTO cart (description, price, quantity) VALUES (?, ?, ?)";
        Connection conn = null;
        try {
            conn = MysqlConnection1.getConnection();
            // Check if item already exists in cart
            try (PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck)) {
                pstmtCheck.setString(1, description);
                pstmtCheck.setDouble(2, price);
                ResultSet rs = pstmtCheck.executeQuery();
                if (rs.next()) {
                    // Item exists, update quantity
                    int currentQuantity = rs.getInt("quantity");
                    try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {
                        pstmtUpdate.setInt(1, quantity);
                        pstmtUpdate.setString(2, description);
                        pstmtUpdate.setDouble(3, price);
                        int rowsAffected = pstmtUpdate.executeUpdate();
                        System.out.println("Updated cart quantity, rows affected: " + rowsAffected);
                    }
                } else {
                    // Item does not exist, insert new item
                    try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {
                        pstmtInsert.setString(1, description);
                        pstmtInsert.setDouble(2, price);
                        pstmtInsert.setInt(3, quantity);
                        int rowsAffected = pstmtInsert.executeUpdate();
                        System.out.println("Inserted into cart, rows affected: " + rowsAffected);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Database Error in addToCart: " + e.getMessage());
            throw e;
        } finally {
            if (conn != null) try { conn.close(); } catch (SQLException e) { /* ignore */ }
        }
    }
    
    public static List<ProductModel> getCartItems() throws SQLException {
        String sql = "SELECT description, price, quantity FROM cart";
        List<ProductModel> cartItems = new ArrayList<>();
        try (Connection conn = MysqlConnection1.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                cartItems.add(new ProductModel(
                    null, // image_path not needed for cart
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("quantity")
                ));
            }
            return cartItems;
        } catch (SQLException e) {
            System.err.println("Database Error in getCartItems: " + e.getMessage());
            throw e;
        }
    }
    
    public static void clearCart() throws SQLException {
        String sql = "DELETE FROM cart";
        Connection conn = null;
        try {
            conn = MysqlConnection1.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Cleared cart, rows affected: " + rowsAffected);
            }
        } catch (SQLException e) {
            System.err.println("Database Error in clearCart: " + e.getMessage());
            throw e;
        } finally {
            if (conn != null) try { conn.close(); } catch (SQLException e) { /* ignore */ }
        }
    }
    
    public static void deleteCartItem(String description, double price) throws SQLException {
        String sql = "DELETE FROM cart WHERE description = ? AND price = ?";
        Connection conn = null;
        try {
            conn = MysqlConnection1.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, description);
                pstmt.setDouble(2, price);
                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Deleted cart item, rows affected: " + rowsAffected);
            }
        } catch (SQLException e) {
            System.err.println("Database Error in deleteCartItem: " + e.getMessage());
            throw e;
        } finally {
            if (conn != null) try { conn.close(); } catch (SQLException e) { /* ignore */ }
        }
    }
    
    public static void updateCartItemQuantity(String description, double price, int quantity) throws SQLException {
        String sql = "UPDATE cart SET quantity = ? WHERE description = ? AND price = ?";
        Connection conn = null;
        try {
            conn = MysqlConnection1.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, quantity);
                pstmt.setString(2, description);
                pstmt.setDouble(3, price);
                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Updated cart item quantity, rows affected: " + rowsAffected);
            }
        } catch (SQLException e) {
            System.err.println("Database Error in updateCartItemQuantity: " + e.getMessage());
            throw e;
        } finally {
            if (conn != null) try { conn.close(); } catch (SQLException e) { /* ignore */ }
        }
    }
}