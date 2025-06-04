/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.database;

/**
 *
 * @author fahmi
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection1 {

    private static final String URL = "jdbc:mysql://localhost:3306/buysmart?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";    
    private static final String USER = "root";

    private static final String PASSWORD = "admin";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver bhetena ta. Aba connector JAR bhanne package hala project maa.", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try {
            Connection conn = MysqlConnection1.getConnection();
            System.out.println("Connection bhayo hai saathi!!");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Connection Bhayena ta babu!!");
        }
    }
}




