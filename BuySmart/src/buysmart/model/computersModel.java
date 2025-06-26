/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.model;

/**
 *
 * @author fahmi
 */
public class computersModel {
    private String imagePath;
    private String description;
    private double price;
    private int quantity;

    public computersModel(String imagePath, String description, double price, int quantity) {
        this.imagePath = imagePath;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
    public computersModel(String imagePath, String description, double price) {
        this(imagePath, description, price, 1); // Default quantity is 1
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
