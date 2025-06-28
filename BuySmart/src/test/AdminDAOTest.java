/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import buysmart.dao.AdminDao;
import buysmart.model.ProductModel;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author batas
 */
public class AdminDAOTest {
    private AdminDao adminDao;
    private String testAdminEmail;
    private ProductModel testProduct;
    @Before
            public void setUp(){
    adminDao=new AdminDao();
    testAdminEmail="admin@gmail.com";
    
    testProduct=new ProductModel();
    testProduct.setCategory("Electronics");
    testProduct.setPrice(299.99);
    testProduct.setDescription("JUnit test Product");
    testProduct.setImagePath("images/test.jpg");
    testProduct.setQuantity(10);
    } 
      @Test
      public void testAddProduct(){
          boolean added=adminDao.addProduct(testProduct, testAdminEmail);
          Assert.assertTrue("Product should be added successfully",added);
      }
      @Test
      public void testGetAllProducts(){
          List<ProductModel> products=adminDao.getAllProducts();
          Assert.assertNotNull("Product list should not be null",products);
          Assert.assertFalse("Product list should mot be empty",products.isEmpty());
          
          ProductModel product=products.get(0);
          Assert.assertNotNull("Image path should not be null",product.getImagePath());
          Assert.assertTrue("Price should be positive",product.getPrice()>0);
      }
      @Test
      
            public void testGetProductsByCategory() {
        List<ProductModel> products = adminDao.getProductsByCategory("Electronics");
        Assert.assertNotNull("Product list by category should not be null", products);
        Assert.assertFalse("Product list by category should not be empty", products.isEmpty());
    }
     @Test
    public void testGetProductsByAdmin() {
        List<ProductModel> products = adminDao.getProductsByAdmin(testAdminEmail);
        Assert.assertNotNull("Product list by admin should not be null", products);
        Assert.assertFalse("Product list by admin should not be empty", products.isEmpty());
    }
    @Test
    public void testUpdateProduct() {
        List<ProductModel> products = adminDao.getProductsByAdmin(testAdminEmail);
        if (!products.isEmpty()) {
            ProductModel product = products.get(0);
            product.setPrice(product.getPrice() + 20);
            product.setQuantity(product.getQuantity() + 2);
            boolean updated = adminDao.updateProduct(product);
            Assert.assertTrue("Product should be updated", updated);
        } else {
            Assert.fail("No product found to update");
        }
    }
    @Test
    public void testDeleteProduct() {
        List<ProductModel> products = adminDao.getProductsByAdmin(testAdminEmail);
        if (!products.isEmpty()) {
            int productId = products.get(0).getProductId();
            boolean deleted = adminDao.deleteProduct(productId);
            Assert.assertTrue("Product should be deleted", deleted);
        } else {
            Assert.fail("No product found to delete");
        }
    }

    @Test
    public void testAddComputerProduct() {
        boolean added = adminDao.addComputerProduct(testProduct, testAdminEmail);
        Assert.assertTrue("Computer product should be added successfully", added);
    }
}

