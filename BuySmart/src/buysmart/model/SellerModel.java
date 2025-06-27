/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.model;

/**
 *
 * @author batas
 */
public class SellerModel {
    private String email;
    private String businessName;
    private String businessAddress;
    private String contactNumber;
    private String panNumber;
    private String businessType;
    private String password;

    public SellerModel(String email, String businessName, String businessAddress, String contactNumber, String panNumber, String businessType, String password) {
        this.email = email;
        this.businessName = businessName;
        this.businessAddress = businessAddress;
        this.contactNumber = contactNumber;
        this.panNumber = panNumber;
        this.businessType = businessType;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public String getBusinessType() {
        return businessType;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
