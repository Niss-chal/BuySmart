/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package test;

import buysmart.dao.SellerDAO;
import buysmart.model.SellerModel;
import java.sql.SQLException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author batas
 */
public class SellerDaoTestIT {
    private SellerDAO sellerDAO;
    private SellerModel testSeller;
  
    @Before
    public void setUp() {
        sellerDAO = new SellerDAO();

        testSeller = new SellerModel();
        testSeller.setEmail("test_seller@example.com");
        testSeller.setBusinessName("Test Business");
        testSeller.setBusinessAddress("123 Test Street");
        testSeller.setContactNumber("9876543210");
        testSeller.setPanNumber("PAN123456");
        testSeller.setBusinessType("Electronics");
        testSeller.setPassword("password123");
    }
     @Test
    public void testRegisterSeller() throws SQLException {
        boolean registered = sellerDAO.registerSeller(testSeller);
        Assert.assertTrue("Seller should be registered successfully", registered);
    }

    @Test
    public void testLoginSeller() throws SQLException {
        // Ensure seller is registered first
        sellerDAO.registerSeller(testSeller);

        SellerModel loggedInSeller = sellerDAO.loginSeller(
                testSeller.getEmail(),
                testSeller.getBusinessName(),
                testSeller.getPassword()
        );

        Assert.assertNotNull("Login should return a seller object", loggedInSeller);
        Assert.assertEquals("Email should match", testSeller.getEmail(), loggedInSeller.getEmail());
        Assert.assertEquals("Business name should match", testSeller.getBusinessName(), loggedInSeller.getBusinessName());
    }
  
}
