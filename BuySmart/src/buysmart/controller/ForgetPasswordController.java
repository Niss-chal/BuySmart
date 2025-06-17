/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.controller.mail.SMTPSMailSender;
import buysmart.dao.UserDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import buysmart.view.ForgetPasswordView;
import buysmart.view.LoginView;
import java.util.Random;

/**
 *
 * @author loq
 */
public class ForgetPasswordController {
    private ForgetPasswordView view;
    private String generatedOtp;
    private  UserDAO dao;

    public ForgetPasswordController(ForgetPasswordView view){
        this.view = view;
        this.dao=new UserDAO();
        
        ForgotUser ChangePass = new ForgotUser();
        this.view.ChangePass(ChangePass);

        LoginBack loginBack = new LoginBack();
        this.view.loginBack(loginBack);
        
        this.view.sendOtpListener(new SendOtpHandler());
        this.view.verifyOtpListener(new VerifyOtpHandler());
        this.view.getNpasswordCheckbox().addActionListener(new ToggleNewPassword());
        this.view.getCpasswordCheckbox().addActionListener(new ToggleConfirmPassword());

    }
    public void open() {
        view.setVisible(true);
    }

    public void close() {
        view.dispose();
    }
    private static final String email_Regex = "^[\\w.-]+@[\\w.-]+\\.\\w{2,}$";
    private static final String password_Regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    private class SendOtpHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
         String email=view.getemail().getText();
         String security_question=(String)view.getSecurityBox().getSelectedItem();
         String security_answer=view.getanswer().getText();
         
         if(email.isEmpty()||security_answer.isEmpty()){
             JOptionPane.showMessageDialog(view,"Please fill both email and answer");
             return;
         }
             boolean isValid=dao.validateSecurityAnswer(email,security_question,security_answer);  
             
             if(!isValid){
                 JOptionPane.showMessageDialog(view,"Donot match");
                 return;
             }
             generatedOtp=generateOtp();
             String subject="Your Otp for Resetting Password";
             String message="Your otp is:"+generatedOtp;
             
             boolean sent=SMTPSMailSender.send(email,subject,message);
             if(sent){
                 JOptionPane.showMessageDialog(view,"OTP has sent to you email");
                 view.enableOtpInputs();
             } else{
                 JOptionPane.showMessageDialog(view,"Otp is failed");
             }
        }
    }
    private class VerifyOtpHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String enteredOtp=view.getotpBox().getText();
            
            if(enteredOtp.equals(generatedOtp)){
                JOptionPane.showMessageDialog(view,"OTP verified");
                view.enablePasswordInputs();
            } else{
                JOptionPane.showMessageDialog(view,"Inccorect OTP");
            }
        }
        
    }
    private class ConfirmHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String newPass=new String(view.getnewpass().getPassword());
            String confirmPass=new String(view.getconfirmpass().getPassword());
            
            if(!newPass.equals(confirmPass)){
                JOptionPane.showMessageDialog(view,"Password donot matched");
                return;
            }
            boolean updated=dao.updatePassword(view.getemail().getText(),newPass);
            
            if(updated){
                JOptionPane.showMessageDialog(view,"Password changed successfully");
                view.dispose();
            } else{
                JOptionPane.showMessageDialog(view,"ERROR");
            }
        }
        
    }
    private String generateOtp(){
        Random rand=new Random();
        int otp=100000+rand.nextInt(900000);
        return String.valueOf(otp);
    }

    
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
            ForgetPasswordController.this.close();
        }
    }
    class ToggleNewPassword implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean show=view.getNpasswordCheckbox().isSelected();
            view.getnewpass().setEchoChar(show ? (char) 0 : '•');

        }
        
    }
    class ToggleConfirmPassword implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean show=view.getCpasswordCheckbox().isSelected();
            view.getconfirmpass().setEchoChar(show ? (char) 0 : '•');

        }
        
    }
}
