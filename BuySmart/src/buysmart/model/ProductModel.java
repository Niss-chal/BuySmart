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
private String imagePath;
private String description;
private double price;

public ProductModel(String imagePath, String description, double price) {
    this.imagePath = imagePath;
    this.description = description;
    this.price = price;
}
public String getImagePath() {
    return imagePath;
}

public String getDescription() {
    return description;
}

public double getPrice() {
    return price;
}
    
}