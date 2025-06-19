/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
            System.out.println("Database Error in getProducts: " + e.getMessage());
            throw e;
        }
    }
    
}
