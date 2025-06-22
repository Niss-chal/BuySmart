/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.dao.UserprofileDAO;
import buysmart.model.UserModel;
import buysmart.view.Dashboard;
import buysmart.view.Profileview;
import buysmart.view.ChangePassword;
import buysmart.view.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


/**
 *
 * @author loq
 */
public class UserprofileController { 
   private Profileview view;
    private String email; 
    private UserModel user;

    public UserprofileController(Profileview view, String email) {
        this.view = view;
        this.email = email;
        init();
        
        openDashboard backDashboard = new openDashboard();
        this.view.backDashboard(backDashboard);
        
        ChangeProfile changeProfile = new ChangeProfile();
        this.view.changeProfile(changeProfile);
        
        ChangePass changePassword = new ChangePass();
        this.view.changePassword(changePassword); 
        
        Deleteaccount deleteAccount=new Deleteaccount();
        this.view.deleteAccount(deleteAccount);
    }

    public void open() {
        view.setVisible(true);
    }

    public void close() {
        view.dispose();
    }
    
    public void init() {
        UserprofileDAO dao = new UserprofileDAO();
        UserModel user = dao.getUserByEmail(email); // Use getUserByEmail
        
        if (user != null) {
            view.getUpdatename().setText(user.getUsername() != null ? user.getUsername() : "");
            view.getUpdateemail().setText(user.getEmail() != null ? user.getEmail() : "");
            view.getUpdateaddress().setText(user.getAddress() != null ? user.getAddress() : "");
            view.getUpdatecontact().setText(user.getContact() != null ? user.getContact() : "");    
        } else {
            JOptionPane.showMessageDialog(view, "User Profile not found!", "Error", JOptionPane.ERROR_MESSAGE);
            view.dispose();
        }
    }
        
    public class openDashboard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();
            Dashboard dashboard = new Dashboard();
            DashboardController dashboardController = new DashboardController(dashboard, email); // Pass email
            dashboardController.open();
        }
    }
        
    public class ChangeProfile implements ActionListener {
        private boolean isEditing = false;
        private UserModel originalUser;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isEditing) {
                originalUser = new UserModel(
                    view.getUpdatename().getText(),
                    view.getUpdateemail().getText(),
                    view.getUpdateaddress().getText(),
                    view.getUpdatecontact().getText()
                );
                view.setFieldsEnabled(true);
                isEditing = true;
                view.setChangeProfileButtonText("Save Changes");
            } else {
                int response = JOptionPane.showConfirmDialog(view, "Save changes?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
                if (response == JOptionPane.OK_OPTION) {
                    boolean hasChanges = !view.getUpdatename().getText().equals(originalUser.getUsername()) ||
                                        !view.getUpdateemail().getText().equals(originalUser.getEmail()) ||
                                        !view.getUpdateaddress().getText().equals(originalUser.getAddress()) ||
                                        !view.getUpdatecontact().getText().equals(originalUser.getContact());

                    if (hasChanges) {
                        UserModel updatedUser = new UserModel(
                            view.getUpdatename().getText(),
                            view.getUpdateemail().getText(),
                            view.getUpdateaddress().getText(),
                            view.getUpdatecontact().getText()
                        );
                        UserprofileDAO dao = new UserprofileDAO();
                        if (dao.updateUser(updatedUser)) {
                            JOptionPane.showMessageDialog(view, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            originalUser = updatedUser;
                            // Update the controller's email if it was changed
                            email = updatedUser.getEmail();
                        } else {
                            JOptionPane.showMessageDialog(view, "Failed to update profile!", "Error", JOptionPane.ERROR_MESSAGE);
                            if (originalUser != null) {
                                view.getUpdatename().setText(originalUser.getUsername());
                                view.getUpdateemail().setText(originalUser.getEmail());
                                view.getUpdateaddress().setText(originalUser.getAddress());
                                view.getUpdatecontact().setText(originalUser.getContact());
                            }
                        }
                    }
                    view.setFieldsEnabled(false);
                    isEditing = false;
                    view.setChangeProfileButtonText("Change Profile");
                } else {
                    if (originalUser != null) {
                        view.getUpdatename().setText(originalUser.getUsername());
                        view.getUpdateemail().setText(originalUser.getEmail());
                        view.getUpdateaddress().setText(originalUser.getAddress());
                        view.getUpdatecontact().setText(originalUser.getContact());
                    }
                    view.setFieldsEnabled(false);
                    isEditing = false;
                    view.setChangeProfileButtonText("Change Profile");
                }
            }
        }
    }
    
    class ChangePass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           view.dispose();
            ChangePassword changePasswordView = new ChangePassword();
            ChangePasswordController changePassController = new ChangePasswordController(changePasswordView, email);
            changePassController.open();
        }
    }
    
    
    class Deleteaccount implements ActionListener {

        
        @Override
        public void actionPerformed(ActionEvent e) {
        int response = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete your Account?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                UserprofileDAO dao = new UserprofileDAO();
                UserModel userToDelete = dao.getUserByEmail(email); // Fetch user based on email
                if (userToDelete == null) {
                    JOptionPane.showMessageDialog(view, "User not found or error retrieving profile!", "Error", JOptionPane.ERROR_MESSAGE);
                    System.err.println("Debug: getUserByEmail returned null for email: " + email);
                    return; // Exit if user is not found
                }
                if (dao.deleteAccount(userToDelete)) {
                    JOptionPane.showMessageDialog(view, "Account deleted Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    view.dispose();
                    LoginView loginview = new LoginView();
                    LoginController loginController = new LoginController(loginview);
                    loginController.open();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to delete Account", "Error", JOptionPane.ERROR_MESSAGE);
                    System.err.println("Debug: deleteAccount failed for email: " + email);
                }
            }           
        }
    
    }
}