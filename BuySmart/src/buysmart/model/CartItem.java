/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.model;

/**
 *
 * @author loq
 */
public class CartItem {
    private int id;
    private String description;
    private double price;
    private int quantity;

    public CartItem(int id, String description, double price, int quantity) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() { return id; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
}
