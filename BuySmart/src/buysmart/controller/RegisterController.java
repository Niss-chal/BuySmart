/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import buysmart.view.LoginView;
import buysmart.view.RegistrationView;

/**
 *
 * @author loq
 */
public class RegisterController {
    
    private RegistrationView view;

    public RegisterController(RegistrationView view) {
        this.view = view;

        RegisterUser register = new RegisterUser();
        this.view.registeruser(register);  
    }

    public void open() {
        view.setVisible(true);
    }

    public void close() {
        view.dispose();
    }

    class RegisterUser implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.getUsername().getText();
            String email = view.getEmail().getText();
            String password = String.valueOf(view.getPassword().getPassword());
            String confirmPassword = String.valueOf(view.getconfirmPassword().getPassword());
            String phone = view.getContact().getText();
            String address = view.getAddress().getText();
            String answer = view.getAnswer().getText();
            boolean male = view.getMale().isSelected();
            boolean female = view.getFemale().isSelected();
            boolean others = view.getOthers().isSelected();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
                    || phone.isEmpty() || address.isEmpty() || answer.isEmpty() || (!male && !female && !others)) {
                JOptionPane.showMessageDialog(view, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(view, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, "Registration Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                
                LoginView loginView = new LoginView();
                LoginController logincontroller = new LoginController(loginView);
                logincontroller.open();
                close();
            }
        }
    }
}