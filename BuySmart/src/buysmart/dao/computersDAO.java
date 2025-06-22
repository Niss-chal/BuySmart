/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.dao;

import buysmart.database.MysqlConnection1;
import buysmart.model.computersModel;
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
public class computersDAO {
    
    public static List<computersModel> getComputers() throws SQLException {
        String sql = "SELECT image_path, description, price FROM computers WHERE category = 'Computers'";
        List<computersModel> computers = new ArrayList<>();
        try (Connection conn = MysqlConnection1.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                computers.add(new computersModel(
                    rs.getString("image_path"),
                    rs.getString("description"),
                    rs.getDouble("price")
                ));
            }
            return computers;
        } catch (SQLException e) {
            System.err.println("Database Error in getComputers: " + e.getMessage());
            throw e;
        }
    }
    
}
