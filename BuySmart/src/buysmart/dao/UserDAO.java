/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author fahmi
 */

package buysmart.dao;

//import javax.swing.JOptionPane;

import buysmart.database.MysqlConnection1;
import buysmart.model.UserModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO {
    
    public static boolean registerUser(UserModel usermodel) throws SQLException {
        
        String sql = "INSERT INTO users (username, email, password, contact, address, gender, security_question, security_answer) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = MysqlConnection1.getConnection()){
                
            conn.setAutoCommit(false);
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usermodel.getUsername());
                pstmt.setString(2, usermodel.getEmail());
                pstmt.setString(3, usermodel.getPassword());
                pstmt.setString(4, usermodel.getContact());
                pstmt.setString(5, usermodel.getAddress());
                pstmt.setString(6, usermodel.getGender());
                pstmt.setString(7, usermodel.getSecurityQuestion());
                pstmt.setString(8, usermodel.getSecurityAnswer());
            
            int rowsAffected = pstmt.executeUpdate();
            conn.commit();
            return rowsAffected > 0;
        } catch (SQLException e){
            conn.rollback();
            throw e;
        }
    }
    }
    
//    public static void main(String[] args) {
//    try (Connection conn = MysqlConnection1.getConnection()) {
//        JOptionPane.showMessageDialog(null, "database connect bhayo mazzale");
//    } catch (SQLException e) {
//        JOptionPane.showMessageDialog(null, "Connection bhayena: " + e.getMessage());
//    }
//}

}




