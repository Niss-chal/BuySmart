/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.dao.ProductDAO;
import buysmart.dao.SellerDAO;
import buysmart.dao.UserDAO;
import buysmart.model.ProductModel;
import buysmart.view.AdminDashboard;
import buysmart.view.CartManage;
import buysmart.view.Dashboard;
import buysmart.view.LoginView;
import buysmart.view.OrdersView;
import buysmart.view.Profileview;
import buysmart.view.SellerRegistrationView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.Timer;

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
    private ProductController productController; // Add ProductController field
    private boolean isProcessingClick = false;
    
    public DashboardController(Dashboard dashboard,String email) {
        this.dashboard = dashboard;
        this.email = email;
        productController = new ProductController(this.dashboard,email);
        dashboard.getAdminProductAdd().setVisible(false);
        checkSellerStatusAndSetButtonVisibility();
       
        
        // Load products to map buttons to product data
        try {
            products = ProductDAO.getProduct();
            productController.loadProduct(); // Load products initially
        } catch (SQLException e) {
            System.err.println("Error loading products: " + e.getMessage());
            products = null;
        }

        
        
        Logout logout = new Logout();
        this.dashboard.logout(logout);

        Cart cart = new Cart();
        this.dashboard.cart(cart);
    
        OpenProfile openProfile = new OpenProfile();
        this.dashboard.openProfile(openProfile); 
        
        OpenOrders openOrdersHistory = new OpenOrders();
        this.dashboard.openOrdersHistory(openOrdersHistory);
        
        SellerRegistration sellerRegister = new SellerRegistration();
        this.dashboard.sellerRegister(sellerRegister);

        AddCart addCart = new AddCart();
        this.dashboard.addCart(addCart);
        
        ShowComputers getComputers = new ShowComputers();
        this.dashboard.getComputers(getComputers);
        
        ShowAll getAll = new ShowAll();
        this.dashboard.getAll(getAll);
        
                
        for (ActionListener al : dashboard.getAdminProductAdd().getActionListeners()) {
            dashboard.getAdminProductAdd().removeActionListener(al);
        }
        this.dashboard.toAdminDashboard(new ToAdminDashboard());
        
        
    }


    
    private void checkSellerStatusAndSetButtonVisibility() {
    try {
        boolean isSeller = SellerDAO.isSeller(email);
        boolean isBuyer=UserDAO.isBuyer(email);
        dashboard.getAdminProductAdd().setVisible(isSeller);
        dashboard.getsellerRegister().setVisible(isBuyer);
    } catch (SQLException ex) {
        // If there's an error checking seller status, hide the button by default
        dashboard.getAdminProductAdd().setVisible(false);
        System.err.println("Error checking seller status: " + ex.getMessage());
        }
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
        private static boolean isOpen = false;
        @Override
        public void mouseClicked(MouseEvent e) {
            if (isOpen) return; // Stop if already open
        
            isOpen = true;
            dashboard.dispose();
            CartManage cartmanage = new CartManage(email);
            CartManageController cartmanageController = new CartManageController(cartmanage, email);
            cartmanageController.open();

             // Reset after 2 seconds
            Timer timer = new Timer(2000, ae -> isOpen = false);
            timer.setRepeats(false);
            timer.start();
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
            JButton[] cartButtons = {dashboard.getProductAddToCartButton()};
            
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
                    ProductDAO.addToCart(email,product.getDescription(), product.getPrice(), 1); // Default quantity is 1
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
        private static boolean isOpen = false;

        @Override
        public void mouseClicked(MouseEvent e) {
            if (isOpen) return; // Stop if already open
        
            isOpen = true;
            dashboard.setVisible(false);
            Profileview userprofileview = new Profileview();
            UserprofileController userprofilecontroller = new UserprofileController(userprofileview, email);
            userprofilecontroller.open();

             // Reset after 2 seconds
            Timer timer = new Timer(2000, ae -> isOpen = false);
            timer.setRepeats(false);
            timer.start();
                
            
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
     
     class OpenOrders implements MouseListener{
        private static boolean isOpen = false;

        @Override
        public void mouseClicked(MouseEvent e) {
            if (isOpen) return; // Stop if already open
        
            isOpen = true;
            dashboard.setVisible(false);
            OrdersView ordersview = new OrdersView();
            OrdersController ordersController = new OrdersController(ordersview, email);
            ordersController.open();

             // Reset after 2 seconds
            Timer timer = new Timer(2000, ae -> isOpen = false);
            timer.setRepeats(false);
            timer.start();
           
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
     
    class ShowComputers implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            showComputersView();
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
    
    class ShowAll implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            showAllView();
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
     
     // New method to show Computers view
    public void showComputersView() {
        System.out.println("Switching to Computers view");
        productController.loadProductsByCategory("Computers");
    }

    // ✅ FIXED - Show All products view using ProductController
    public void showAllView() {
        System.out.println("Switching to All Products view");
        productController.loadProductsByCategory("All Products");
    }
    
    //Show products by category using ProductController
    public void showCategoryView(String category) {
        System.out.println("Switching to " + category + " view");
        productController.loadProductsByCategory(category);
    }
    
    class SellerRegistration implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            dashboard.setVisible(false);
             SellerRegistrationView sellerregister = new SellerRegistrationView();
            SellerRegistrationController sellerController = new SellerRegistrationController(sellerregister);
            sellerController.open();
        }
        
    }
    
    class ToAdminDashboard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isProcessingClick) return; // Debounce
            isProcessingClick = true;
            try {
                if (SellerDAO.isSeller(email)) {
                    dashboard.dispose();
                    AdminDashboard adminDashboard = new AdminDashboard();
                    AdminController adminController = new AdminController(adminDashboard, email);
                    adminController.open();
                } else {
                    JOptionPane.showMessageDialog(dashboard, "Please register as seller", "Access Denied", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dashboard, "Error checking seller status: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                // Reset debounce after a short delay
                Timer timer = new Timer(500, ae -> isProcessingClick = false);
                timer.setRepeats(false);
                timer.start();
            }
        }
    }
    
}


