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
public class UserDAO1Test {
    String correctemail="sarina@gmail.com";
    String correctsecurity_question="What is my favorite color ?";
    String correctsecurity_answer="blue";
    String newPassword="Sarina@1";
    @Test
    public void testValidateSecurityAnswer(){
        UserDAO dao=new UserDAO();
        boolean result=dao.validateSecurityAnswer(correctemail, correctsecurity_question, correctsecurity_answer);
        Assert.assertTrue("Security answer should be valid",result);
    }
    @Test
    public void testUpdatePassword() throws SQLException{
        UserDAO dao =new UserDAO();
        boolean result=dao.updatePassword(correctemail, newPassword);
        Assert.assertTrue("Password update should be successful",result);
        
        //optional
        LoginRequest login =new LoginRequest(correctemail,newPassword);
        UserModel user =dao.loginUser(login);
        Assert.assertNotNull("User should log in with updated password",user);
    }
}
