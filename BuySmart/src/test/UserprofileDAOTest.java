/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import buysmart.dao.UserprofileDAO;
import buysmart.model.UserModel;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author batas
 */
public class UserprofileDAOTest {
    String correctemail="sarina@gmail.com";
    @Test
    public void testGetUserByEmail(){
        UserprofileDAO dao=new UserprofileDAO();
            UserModel user=dao.getUserByEmail(correctemail);
            Assert.assertNotNull("User should be found by email",user);
            Assert.assertEquals("Email should match",correctemail,user.getEmail());
        }
   @Test
public void testUpdateUser(){
    UserprofileDAO dao=new UserprofileDAO();
    UserModel user =dao.getUserByEmail(correctemail);
    Assert.assertNotNull("User should be update",user);
    
    //modify fields to test
    user.setUsername("Sarina");
    user.setAddress("kathmandu");
    user.setContact("9817125566");
    boolean result=dao.updateUser(user);
    Assert.assertTrue("user update should succeed",result);    
}
}

