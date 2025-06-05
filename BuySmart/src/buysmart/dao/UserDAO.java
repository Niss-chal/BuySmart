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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    
    public static boolean registerUser(String username, String email, String password, 
                                     String contact, String address, String gender, 
                                     String securityQuestion, String securityAnswer) throws SQLException {
        
        String sql = "INSERT INTO users (username, email, password, contact, address, gender, security_question, security_answer) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = MysqlConnection1.getConnection()){
                
            conn.setAutoCommit(false);
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setString(4, contact);
            pstmt.setString(5, address);
            pstmt.setString(6, gender);
            pstmt.setString(7, securityQuestion);
            pstmt.setString(8, securityAnswer);
            
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
public boolean validateSecurityAnswer(String email,String security_question,String security_answer){
    try(Connection conn=MysqlConnection1.getConnection()){
        String query="SELECT * FROM users WHERE " +
                "LOWER(TRIM(email))= ? AND " +
                "LOWER(TRIM(security_question))= ? AND " +
                "LOWER(TRIM(security_answer))= ? ";
        PreparedStatement stmt=conn.prepareStatement(query);
        stmt.setString(1, email.trim().toLowerCase());
        stmt.setString(2, security_question.trim().toLowerCase());
        stmt.setString(3, security_answer.trim().toLowerCase());
        ResultSet rs=stmt.executeQuery();
        return rs.next();
    } catch(Exception e) {
        e.printStackTrace();
        return false;
    }
}
public boolean updatePassword(String email,String newPassword){
    try(Connection conn=MysqlConnection1.getConnection()){
        String query="UPDATE users SET password=? WHERE email=?";
        PreparedStatement stmt=conn.prepareStatement(query);
        stmt.setString(1,newPassword);
        stmt.setString(2,email);
        int rows=stmt.executeUpdate();
        return rows>0;
    } catch (Exception e){
        e.printStackTrace();
        return false;
    }
}
}


