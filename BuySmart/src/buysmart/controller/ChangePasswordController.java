/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.dao.UserDAO;
import buysmart.model.LoginRequest;
import buysmart.model.UserModel;
import buysmart.view.ChangePassword;
import buysmart.view.Dashboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class ChangePasswordController {
    
   
    private ChangePassword password;
    private String email;
    private UserDAO userDAO;

    public ChangePasswordController(ChangePassword view, String email) {
        this.password = view;
        this.email = email;
        this.userDAO = new UserDAO();

        if (email != null && !email.trim().isEmpty()) {
            password.getchangeEmail().setText(email);
        }

        password.changeBack(new PasswordBack());
        view.confirmChange(new ChangePasswordAction());
    }
    
    public void open() {
        password.setVisible(true);
    }
    
    public void close() {
        password.dispose();
    }

    class PasswordBack implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            password.dispose();
            Dashboard dashboard = new Dashboard();
            DashboardController dashboardController = new DashboardController(dashboard, email);
            dashboardController.open();
        }
    }
    
    class ChangePasswordAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentEmail = password.getchangeEmail().getText().trim();
            String oldPassword = new String(password.getOldPass().getPassword()).trim();
            String newPassword = new String(password.getNewPass().getPassword()).trim();
            String confirmPassword = new String(password.getConfirmPass().getPassword()).trim();

            if (currentEmail.isEmpty() || oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(password, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(password, "New password and confirm password do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                LoginRequest loginRequest = new LoginRequest(currentEmail, oldPassword);
                UserModel user = userDAO.loginUser(loginRequest);
                if (user == null) {
                    JOptionPane.showMessageDialog(password, "Invalid email or old password.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean updated = userDAO.updatePassword(currentEmail, newPassword);
                if (updated) {
                    JOptionPane.showMessageDialog(password, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    password.dispose();
                    DashboardController dashboardController = new DashboardController(new Dashboard(), currentEmail);
                    dashboardController.open();
                } else {
                    JOptionPane.showMessageDialog(password, "Failed to change password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(password, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
