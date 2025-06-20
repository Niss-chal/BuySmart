/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.dao;

import buysmart.database.MysqlConnection1;
import buysmart.model.CartItem;
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
    
    public static void addToCart(String userEmail, String description, double price, int quantity) throws SQLException {
        System.out.println("Attempting to add to cart: " + description + ", " + price + ", Quantity: " + quantity + ", User: " + userEmail);
        String sqlCheck = "SELECT quantity FROM cart WHERE user_email = ? AND description = ? AND price = ?";
        String sqlUpdate = "UPDATE cart SET quantity = quantity + ? WHERE user_email = ? AND description = ? AND price = ?";
        String sqlInsert = "INSERT INTO cart (user_email, description, price, quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = MysqlConnection1.getConnection()) {
            // Check if item already exists in cart
            try (PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck)) {
                pstmtCheck.setString(1, userEmail);
                pstmtCheck.setString(2, description);
                pstmtCheck.setDouble(3, price);
                ResultSet rs = pstmtCheck.executeQuery();
                if (rs.next()) {
                    // Item exists, update quantity
                    try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {
                        pstmtUpdate.setInt(1, quantity);
                        pstmtUpdate.setString(2, userEmail);
                        pstmtUpdate.setString(3, description);
                        pstmtUpdate.setDouble(4, price);
                        int rowsAffected = pstmtUpdate.executeUpdate();
                        System.out.println("Updated cart quantity, rows affected: " + rowsAffected);
                    }
                } else {
                    // Item does not exist, insert new item
                    try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {
                        pstmtInsert.setString(1, userEmail);
                        pstmtInsert.setString(2, description);
                        pstmtInsert.setDouble(3, price);
                        pstmtInsert.setInt(4, quantity);
                        int rowsAffected = pstmtInsert.executeUpdate();
                        System.out.println("Inserted into cart, rows affected: " + rowsAffected);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Database Error in addToCart: " + e.getMessage());
            throw e;
        }
    }
    
    public static List<CartItem> getCartItems(String userEmail) throws SQLException {
        String sql = "SELECT id, description, price, quantity FROM cart WHERE user_email = ?";
        List<CartItem> cartItems = new ArrayList<>();
        try (Connection conn = MysqlConnection1.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userEmail);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cartItems.add(new CartItem(
                        rs.getInt("id"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Database Error in getCartItems: " + e.getMessage());
            throw e;
        }
        return cartItems;
    }
    
    public static void clearCart(String userEmail) throws SQLException {
        String sql = "DELETE FROM cart WHERE user_email = ?";
        try (Connection conn = MysqlConnection1.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userEmail);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Cleared cart for user " + userEmail + ", rows affected: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Database Error in clearCart: " + e.getMessage());
            throw e;
        }
    }
    
    public static void deleteCartItem(String userEmail, String description, double price) throws SQLException {
        String sql = "DELETE FROM cart WHERE user_email = ? AND description = ? AND price = ?";
        try (Connection conn = MysqlConnection1.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userEmail);
            pstmt.setString(2, description);
            pstmt.setDouble(3, price);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Deleted cart item for user " + userEmail + ", rows affected: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Database Error in deleteCartItem: " + e.getMessage());
            throw e;
        }
    }
    
    public static void updateCartItemQuantity(String userEmail, String description, double price, int quantity) throws SQLException {
        String sql = "UPDATE cart SET quantity = ? WHERE user_email = ? AND description = ? AND price = ?";
        try (Connection conn = MysqlConnection1.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quantity);
            pstmt.setString(2, userEmail);
            pstmt.setString(3, description);
            pstmt.setDouble(4, price);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Updated cart item quantity for user " + userEmail + ", rows affected: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Database Error in updateCartItemQuantity: " + e.getMessage());
            throw e;
        }
    }
}