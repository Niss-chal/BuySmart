/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.model;

/**
 *

 * @author user

 */
public class ProductModel {
    
     private int productId;
    private String userEmail;
    private String category;
    private double price;
    private String description;
    private String imagePath;
    private int quantity;
    
    // Default constructor
    public ProductModel() {
        this.quantity = 1; // Default quantity
    }
    
    // Constructor for display purposes (without ID and user email)
    public ProductModel(String imagePath, String description, double price) {
        this.imagePath = imagePath;
        this.description = description;
        this.price = price;
        this.quantity = 1;
    }
    
    // Constructor with basic parameters
    public ProductModel(String category, double price, String description, String imagePath) {
        this.category = category;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
        this.quantity = 1;
    }
    
    // Constructor with quantity
    public ProductModel(String category, double price, String description, String imagePath, int quantity) {
        this.category = category;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
        this.quantity = quantity;
    }
    
    // Constructor with all fields
    public ProductModel(int productId, String userEmail, String category, double price, String description, String imagePath, int quantity) {
        this.productId = productId;
        this.userEmail = userEmail;
        this.category = category;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
        this.quantity = quantity;
    }
    
    // Legacy constructor for backward compatibility
    public ProductModel(int productId, String category, double price, String description, String imagePath) {
        this.productId = productId;
        this.category = category;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
        this.quantity = 1;
    }
    
    // Getters and Setters
    public int getProductId() {
        return productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public String getUserEmail() {
        return userEmail;
    }
    
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getImagePath() {
        return imagePath;
    }
    
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

