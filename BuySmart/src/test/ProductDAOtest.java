/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import buysmart.dao.ProductDAO;
import buysmart.model.CartItem;
import buysmart.model.ProductModel;
import java.sql.SQLException;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author batas
 */
public class ProductDAOtest {
    String correctUseremail="sarina@gmail.com";
    @Test
    public void testGetProduct() throws SQLException{
        List<ProductModel> products =ProductDAO.getProduct();
        Assert.assertNotNull("Product list should not be null",products);
        Assert.assertFalse("Product list should not be emplty",products.isEmpty());
       //Optionally check first product fields
       ProductModel p=products.get(0);
       Assert.assertNotNull("Product image path shouldnot be null",p.getImagePath());
       Assert.assertNotNull("Product description should not be null",p.getDescription());
       Assert.assertTrue("Product price should be>=0",p.getPrice()>=0);
    }
    @Test
    public void testAddToGetCartItems() throws SQLException{
        String description="Test Product";
        double price=10.5;
        int quantity=2;
        ProductDAO.addToCart(correctUseremail,description,price,quantity);
        List<CartItem> cartItems= ProductDAO.getCartItems(correctUseremail);
        Assert.assertEquals("Cart should have 1 item",1,cartItems.size());
        CartItem item =cartItems.get(0);
        Assert.assertEquals(description,item.getDescription());
        Assert.assertEquals(price,item.getPrice());
        Assert.assertEquals(quantity,item.getQuantity());
        int moreQuantity=3;
        ProductDAO.addToCart(correctUseremail,description,price,moreQuantity);
        cartItems=ProductDAO.getCartItems(correctUseremail);
        Assert.assertEquals("Cart should still have 1 item",1,cartItems.size());
        Assert.assertEquals(quantity+moreQuantity,cartItems.get(0).getQuantity());
        
    }
    
    @Test
    public void testUpdateCartItemQuantity() throws SQLException{
        String description="Test Product";
        double price=10.5;
        int quantity=2;
        ProductDAO.addToCart(correctUseremail,description,price,quantity);
        int newQuantity=5;
        ProductDAO.updateCartItemQuantity(correctUseremail, description, price, quantity);
        List<CartItem> cartItems=ProductDAO.getCartItems(correctUseremail);
        Assert.assertEquals("Cart should have 1 item",1,cartItems.size());
        Assert.assertEquals(newQuantity,cartItems.get(0).getQuantity());
        
    }
    @Test
    public void testDeleteCartItem() throws SQLException{
        String description="Test Product";
        double price=10.5;
        int quantity=2;
        ProductDAO.addToCart(correctUseremail,description,price,quantity);
        int newQuantity=5;
        ProductDAO.deleteCartItem(correctUseremail,description,price);
        List<CartItem> cartItems=ProductDAO.getCartItems(correctUseremail);
        Assert.assertTrue("Cart should be empty after delete",cartItems.isEmpty());
        
    }
    public void testClearCart() throws SQLException{
        ProductDAO.addToCart(correctUseremail,"Product1",5.0,1);
        ProductDAO.addToCart(correctUseremail,"Product2",10.0,1);
        ProductDAO.clearCart(correctUseremail);
        List<CartItem> cartItems=ProductDAO.getCartItems(correctUseremail);
        Assert.assertTrue("Cart should be empty after clear",cartItems.isEmpty());
    }
}
