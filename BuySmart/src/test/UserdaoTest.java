/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import buysmart.dao.UserDAO;
import buysmart.model.LoginRequest;
import buysmart.model.UserModel;
import java.sql.SQLException;
import org.junit.Assert;

import org.junit.Test;

/**
 *
 * @author batas
 */
public class UserdaoTest {
    String correctUsername="Simran";
    String correctemail="batassarina0@gmail.com";
    String password="Sarina@011";
    String contact="1235467891";
    String address="Damauli";
    String security_question="What is my nickname ?";
    String security_answer="sary";
    String gender="female";
    @Test
    public void registerWithNewCredentials() throws SQLException{
        UserModel user =new UserModel(correctUsername,correctemail,password,contact,address,gender,security_question,security_answer);
        UserDAO dao=new UserDAO();
        boolean result=dao.registerUser(user);
        Assert.assertTrue("Register should be successful",result);
    }
    @Test
    public void registerWithExistingCredentials() throws SQLException{
        UserModel user =new UserModel(correctUsername,correctemail,password,contact,address,gender,security_question,security_answer);
        UserDAO dao=new UserDAO();
        boolean result=dao.registerUser(user);
        Assert.assertFalse("Register should fail for existing details",result);
    }
     @Test
    public void loginWithCorrectCreds() throws SQLException{
        LoginRequest req=new LoginRequest(correctemail,password);
        UserDAO dao= new UserDAO();
        UserModel user = dao.loginUser(req);
        Assert.assertNotNull("User should not be null",user);
        Assert.assertEquals("Correct email should be retrieved",correctemail,user.getEmail());
        Assert.assertEquals("Correct name should be retrieved",correctUsername,user.getUsername());
    }

}
