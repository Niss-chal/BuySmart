/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.model;

/**
 *
 * @author fahmi
 */
public class UserModel {
    
    private String username;
    private String email;
    private String password;
    private String contact;
    private String address;
    private String gender;
    private String securityQuestion;
    private String securityAnswer;
    
    public UserModel(String username, String email, String password, String contact, 
                String address, String gender, String securityQuestion, String securityAnswer) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.contact = contact;
        this.address = address;
        this.gender = gender;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    public UserModel() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }
    
    public UserModel(String username, String email, String address, String contact){
        this.username = username;
        this.email=email;
        this.address=address;
        this.contact=contact;   
    }
    
    public UserModel loginUser(String email, String password) {
 
       this.email=email;
       this.password=password;
        return null;

    
}
}
    

