/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;
import buysmart.dao.UserDAO;
import buysmart.model.LoginRequest;
import buysmart.model.UserModel;
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
       
       this.view.ShowPassword().addActionListener(new LoginController.ToggleNewPassword());
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
    try {
        LoginRequest loginData =new LoginRequest(email,password);
        UserDAO dao = new UserDAO();
        UserModel user =dao.loginUser(loginData);
 
        if (user!=null) {
            JOptionPane.showMessageDialog(view, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            Dashboard dashboard = new Dashboard(email);
            DashboardController dashboardController = new DashboardController(dashboard,email);
            dashboardController.open();
            close();
        } else {
            JOptionPane.showMessageDialog(view, "Invalid Email or Password", "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(view, "Database Error", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
 
            }
            

            }
            
    
    class ForgotPassword implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            view.dispose();
            ForgetPasswordView forgotpassword = new ForgetPasswordView();
            ForgetPasswordController forgotController = new ForgetPasswordController(forgotpassword);
            forgotController.open();
            
        }
    
        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
        
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
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
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
         
        }
          
    }
    class ToggleNewPassword implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean show=view.ShowPassword().isSelected();
            view.getPassword().setEchoChar(show ? (char) 0 : '•');

        }
        
    }
}

    
  
