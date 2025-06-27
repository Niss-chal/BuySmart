/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.model;

/**
 *
 * @author batas
 */
public class SellerLoginRequest {
    private String email;
    private String BusinessName;
    private String password;

    public SellerLoginRequest(String email, String BusinessName, String password) {
        this.email = email;
        this.BusinessName = BusinessName;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBusinessName(String BusinessName) {
        this.BusinessName = BusinessName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
