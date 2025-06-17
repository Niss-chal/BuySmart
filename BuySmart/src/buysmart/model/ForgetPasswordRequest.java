/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.model;

/**
 *
 * @author batas
 */
public class ForgetPasswordRequest {
    private String email;
    private String securityQuestion;
    private String securityAnswer;
    private String newPassword;
    private String confirmPassword;
    
    //constructor

    public ForgetPasswordRequest(String email, String securityQuestion, String securityAnswer, String newPassword, String confirmPassword) {
        this.email = email;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }
    //getters

    public String getEmail() {
        return email;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
    
    //setters

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    }
