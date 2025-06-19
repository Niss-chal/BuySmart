/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.dao.ProductDAO;
import buysmart.model.ProductModel;
import buysmart.view.CartManage;
import buysmart.view.Dashboard;
import buysmart.view.LoginView;
import buysmart.view.Profileview;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author loq
 */
public class DashboardController {
    private Dashboard dashboard;
    private List<ProductModel> products; // Store products for mapping buttons
    private String email;

    public DashboardController(Dashboard dashboard,String email) {
        this.dashboard = dashboard;
        
        // Load products to map buttons to product data
        try {
            products = ProductDAO.getProduct();
        } catch (SQLException e) {
            System.out.println("Error loading products: " + e.getMessage());
            products = null;
        }
        
        Logout logout = new Logout();
        this.dashboard.logout(logout);
        
        Cart cart = new Cart();
        this.dashboard.cart(cart);
        
        AddCart addCart = new AddCart();
        this.dashboard.addCart(addCart);
        
        this.dashboard=dashboard;
        this.email=email;
        OpenProfile openProfile = new OpenProfile();
        this.dashboard.openProfile(openProfile); 
    }


    public void open(){
        dashboard.setVisible(true);
    }

    public void close() {
        dashboard.dispose();
    }  
    
    // Logoutbutton Function
    class Logout implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dashboard.dispose();
            LoginView loginview = new LoginView();
            LoginController loginController = new LoginController(loginview);
            loginController.open();
        }
    }
    
    class Cart implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            dashboard.dispose();
            CartManage cartmanage = new CartManage();
            CartManageController cartmanageController = new CartManageController(cartmanage, email);
            cartmanageController.open();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
    
    class AddCart implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (products == null || products.isEmpty()) {
                JOptionPane.showMessageDialog(dashboard, "No products available!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JButton sourceButton = (JButton) e.getSource();
            int productIndex = -1;
            JButton[] cartButtons = {dashboard.getProductAddToCartButton(), dashboard.getProductAddToCartButton1(),
                                     dashboard.getProductAddToCartButton2(), dashboard.getProductAddToCartButton3(),
                                     dashboard.getProductAddToCartButton4()};
            
            // Find which button was clicked
            for (int i = 0; i < cartButtons.length; i++) {
                if (sourceButton == cartButtons[i]) {
                    productIndex = i;
                    break;
                }
            }

            if (productIndex >= 0 && productIndex < products.size()) {
                ProductModel product = products.get(productIndex);
                try {
                    ProductDAO.addToCart(product.getDescription(), product.getPrice(), 1); // Default quantity is 1
                    JOptionPane.showMessageDialog(dashboard, "Added to cart successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dashboard, "Error adding to cart: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(dashboard, "Product not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
    
     class OpenProfile implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            dashboard.setVisible(false);
            Profileview userprofileview = new Profileview();
            UserprofileController userprofilecontroller = new UserprofileController(userprofileview,email);
            userprofilecontroller.open();
            
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    
    }
}


