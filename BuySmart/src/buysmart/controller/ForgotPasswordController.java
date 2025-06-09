/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import buysmart.view.ForgetPasswordView;
import buysmart.view.LoginView;

/**
 *
 * @author loq
 */
public class ForgotPasswordController {
    private ForgetPasswordView view;

    public ForgotPasswordController(ForgetPasswordView view){
        this.view = view;
        ForgotUser ChangePass = new ForgotUser();
        this.view.ChangePass(ChangePass);

        LoginBack loginBack = new LoginBack();
        this.view.loginBack(loginBack);
    }

    public void open() {
        view.setVisible(true);
    }

    public void close() {
        view.dispose();
    }

    private static final String email_Regex = "^[\\w.-]+@[\\w.-]+\\.\\w{2,}$";
    private static final String password_Regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    class ForgotUser implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = view.getemail().getText().trim();
            String answer = view.getanswer().getText().trim();
            String pass = String.valueOf(view.getnewpass().getPassword()).trim();
            String confirmpass = String.valueOf(view.getconfirmpass().getPassword()).trim();

            if (email.isEmpty() || answer.isEmpty() || pass.isEmpty() || confirmpass.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!email.matches(email_Regex)) {
                JOptionPane.showMessageDialog(view, "Invalid email format", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!pass.matches(password_Regex)) {
                JOptionPane.showMessageDialog(view, "Password must be 8+ characters with uppercase, lowercase, digit, and special character", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!pass.equals(confirmpass)) {
                JOptionPane.showMessageDialog(view, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            JOptionPane.showMessageDialog(view, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            LoginView loginView = new LoginView();
            LoginController loginController = new LoginController(loginView);
            loginController.open();
            close();
        }
    }

    class LoginBack implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LoginView loginView = new LoginView();
            LoginController loginController = new LoginController(loginView);
            loginController.open();
            ForgotPasswordController.this.close();
        }
    }
}
