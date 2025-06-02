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

        LogBack logBack = new LogBack();
        this.view.logBack(logBack);
    }

    public void open() {
        view.setVisible(true);
    }

    public void close() {
        view.dispose();
    }

    private static final String email_Regex="^[\\w.-]+@[\\w.-]+\\.\\w{2,}$";
    private static final String password_Regex="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    private static final String phoneRegex = "^[0-9]{10}$";


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
        return;
    }

    int genderCount = 0;
    if (male) genderCount++;
    if (female) genderCount++;
    if (others) genderCount++;

    if (genderCount == 0) {
        JOptionPane.showMessageDialog(view, "Please select a gender", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    } else if (genderCount > 1) {
        JOptionPane.showMessageDialog(view, "Please select only one gender", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (!email.matches(email_Regex)) {
        JOptionPane.showMessageDialog(view, "Invalid email format", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (!password.matches(password_Regex)) {
        JOptionPane.showMessageDialog(view, "Password must be minimum 8 characters, include uppercase, lowercase, digit, and special character", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if(!phone.matches(phoneRegex)){
        JOptionPane.showMessageDialog(view, "Invalid phone number. Enter a valid 10 digit number", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (!password.equals(confirmPassword)) {
        JOptionPane.showMessageDialog(view, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    JOptionPane.showMessageDialog(view, "Registration Successful", "Success", JOptionPane.INFORMATION_MESSAGE);

    LoginView loginView = new LoginView();
    LoginController logincontroller = new LoginController(loginView);
    logincontroller.open();
    close();
        }
    }

    class LogBack implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            LoginView loginView = new LoginView();
            LoginController loginController = new LoginController(loginView);
            loginController.open();
            close();          
            
        }

    }
}