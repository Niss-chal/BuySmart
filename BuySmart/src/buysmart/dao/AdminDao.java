/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import buysmart.model.ProductModel;

import buysmart.database.MysqlConnection1;

/**
 *
 * @author loq
 */
public class AdminDao {
    
    // Add a new product with admin email
    public boolean addProduct(ProductModel product, String adminEmail) {
        String insertSQL = "INSERT INTO products (user_email, category, price, description, image_path, quantity) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = MysqlConnection1.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            
            pstmt.setString(1, adminEmail);
            pstmt.setString(2, product.getCategory());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setString(4, product.getDescription());
            pstmt.setString(5, product.getImagePath()); // Make sure this is not null
            pstmt.setInt(6, product.getQuantity());
            
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Product added to products table - Category: " + product.getCategory() + 
                          ", Image Path: " + product.getImagePath() + ", rows affected: " + rowsAffected);
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding product: " + e.getMessage());
            return false;
        }
    }
    
    
    // Get all products
    public List<ProductModel> getAllProducts() {
        List<ProductModel> products = new ArrayList<>();
        String selectSQL = "SELECT * FROM products ORDER BY created_at DESC";
        
        try (Connection conn = MysqlConnection1.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            
            while (rs.next()) {
                ProductModel product = new ProductModel(
                    rs.getInt("id"),
                    rs.getString("user_email"),
                    rs.getString("category"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getString("image_path"),
                    rs.getInt("quantity")
                );
                products.add(product);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving products: " + e.getMessage());
        }
        
        return products;
    }
    
    // Get products by category
    public List<ProductModel> getProductsByCategory(String category) {
        List<ProductModel> products = new ArrayList<>();
        String selectSQL = "SELECT * FROM products WHERE category = ? ORDER BY created_at DESC";
        
        try (Connection conn = MysqlConnection1.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            
            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ProductModel product = new ProductModel(
                    rs.getInt("id"),
                    rs.getString("user_email"),
                    rs.getString("category"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getString("image_path"),
                    rs.getInt("quantity")
                );
                products.add(product);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving products by category: " + e.getMessage());
        }
        
        return products;
    }
    
    // Get products by admin email
    public List<ProductModel> getProductsByAdmin(String adminEmail) {
        List<ProductModel> products = new ArrayList<>();
        String selectSQL = "SELECT * FROM products WHERE user_email = ? ORDER BY created_at DESC";
        
        try (Connection conn = MysqlConnection1.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            
            pstmt.setString(1, adminEmail);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ProductModel product = new ProductModel(
                    rs.getInt("id"),
                    rs.getString("user_email"),
                    rs.getString("category"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getString("image_path"),
                    rs.getInt("quantity")
                );
                products.add(product);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving products by admin: " + e.getMessage());
        }
        
        return products;
    }
    
    // Add product to computers table specifically
   public boolean addComputerProduct(ProductModel product, String adminEmail) {
        String insertSQL = "INSERT INTO computers (user_email, image_path, description, price, category, quantity) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = MysqlConnection1.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            
            pstmt.setString(1, adminEmail);
            pstmt.setString(2, product.getImagePath());
            pstmt.setString(3, product.getDescription());
            pstmt.setDouble(4, product.getPrice());
            pstmt.setString(5, "Computers");
            pstmt.setInt(6, product.getQuantity());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding computer product: " + e.getMessage());
            return false;
        }
    }
}
