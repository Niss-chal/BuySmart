/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import buysmart.view.Dashboard;
import buysmart.view.ForgetPasswordView;
import buysmart.view.LoginView;
import buysmart.view.RegistrationView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author loq
 */
public class LoginController {
    private LoginView view;
    public LoginController(LoginView view){
        this.view=view;
        LoginUser loginUser = new LoginUser();
        this.view.loginUser(loginUser);
        
        ForgotPassword forgotPassword = new ForgotPassword();
        this.view.forgotPassword(forgotPassword);
        
       SignUp SignIn = new SignUp();
       this.view.SignIn(SignIn);
    }

    public void open(){
        view.setVisible(true);
    }
    public void close(){
        view.dispose();
    }

    class LoginUser implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String email = view.getEmail().getText();
            String password = String.valueOf(view.getPassword().getPassword());
            if(email.isEmpty() || password.isEmpty()){
                JOptionPane.showMessageDialog(view, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                Dashboard dashboard = new Dashboard();
                DashboardController dashboardController = new DashboardController(dashboard);
                dashboardController.open();
                close();
 
            }
            

            }
            
        }
    
    class ForgotPassword implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            view.dispose();
            ForgetPasswordView forgotpassword = new ForgetPasswordView();
            ForgotPasswordController forgotController = new ForgotPasswordController(forgotpassword);
            forgotController.open();
            
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
    
    class SignUp implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            view.dispose();
            RegistrationView reisterView = new RegistrationView();
            RegisterController registerController = new RegisterController(reisterView);
            registerController.open();
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
  
