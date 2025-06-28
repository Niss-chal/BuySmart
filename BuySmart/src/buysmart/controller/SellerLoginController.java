package buysmart.controller;

import buysmart.dao.SellerDAO;
import buysmart.model.SellerModel;
import buysmart.view.AdminDashboard;
import buysmart.view.ForgetPasswordView;
import buysmart.view.LoginView;
import buysmart.view.SellerLoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

public class SellerLoginController {
    private SellerLoginView view;

    public SellerLoginController(SellerLoginView view) {
        this.view = view;

        this.view.loginUser(new LoginSeller());
        
        this.view.addPasswordToggleListeners(new ToggleSellerPassword());
        this.view.customerLogin(new CustomerLoginRedirect());
    }

    public void open() {
        view.setVisible(true);
    }

    public void close() {
        view.dispose();
    }

    // Handles login logic
    class LoginSeller implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = view.getEmail().getText().trim();
            String businessName = view.getBusinessNameBox().getText().trim();
            String password = String.valueOf(view.getPassword().getPassword()).trim();

            if (email.isEmpty() || businessName.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                SellerDAO dao = new SellerDAO();
                SellerModel seller = dao.loginSeller(email, businessName, password);

                if (seller != null) {
                    JOptionPane.showMessageDialog(view, "Seller Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    AdminDashboard dashboard = new AdminDashboard();  
                    AdminController adminController = new AdminController(dashboard, email);
                    adminController.open();
                    close();
                } else {
                    JOptionPane.showMessageDialog(view, "Invalid Credentials or Business Name", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Database Error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

 
    
    // Toggle show/hide password
    class ToggleSellerPassword implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean show = view.ShowPassword().isSelected();
            view.getPassword().setEchoChar(show ? (char) 0 : '•');
        }
    }
    
    class CustomerLoginRedirect implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose(); // Close SellerLoginView
            LoginView loginView = new LoginView();
            LoginController loginController = new LoginController(loginView);
            loginController.open(); // Open LoginView
        }
    }
}
