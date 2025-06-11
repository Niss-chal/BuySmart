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

//import buysmart.controller.DashboardController;
import buysmart.database.MysqlConnection1;
import buysmart.model.UserModel;
import buysmart.model.loginRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import javax.swing.JOptionPane;

public class UserDAO {
    
    public boolean registerUser(UserModel usermodel) throws SQLException {
        
        String sql = "INSERT INTO users (username, email, password, contact, address, gender, security_question, security_answer) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = MysqlConnection1.getConnection()){
                
            
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

            throw e;
        }
    }
    }
    
    public boolean loginUser(loginRequest loginReq) {
    String query = "SELECT * FROM users WHERE email=? AND password=?";
    Connection conn = null;

    try {
        conn = MysqlConnection1.getConnection();
        PreparedStatement stmnt = conn.prepareStatement(query);
        stmnt.setString(1, loginReq.getEmail());
        stmnt.setString(2, loginReq.getPassword());

        ResultSet result = stmnt.executeQuery();
        return result.next(); // returns true if user exists
    } catch (SQLException e) {
        e.printStackTrace(); 
        return false;
    } finally {
        MysqlConnection1.closeConnection(conn); 
    }
}


}




