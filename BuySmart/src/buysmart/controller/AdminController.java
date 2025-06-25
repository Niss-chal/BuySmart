/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.dao.AdminDao;
import buysmart.model.ProductModel;
import buysmart.view.AdminDashboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author loq
 */
public class AdminController {
    AdminDashboard view;
    AdminDao adminDao;
    String selectedImagePath = "";
    String adminEmail;
    
    
public AdminController(AdminDashboard view, String adminEmail){
    this.view=view;
    this.adminEmail = adminEmail;
    this.adminDao = new AdminDao();
    
    
AddProduct  addProduct = new AddProduct();        
this.view.addProduct(addProduct);
 
UploadImage imageUpload=new UploadImage();
this.view.imageUpload(imageUpload); 

}


public void open(){
    view.setVisible(true);
   }
public void close(){
    view.dispose();
   }
   
class AddProduct implements ActionListener{        

    @Override
   public void actionPerformed(ActionEvent e) {
            try {
                // Get input values
                String category = (String) view.getCategoryname().getSelectedItem();
                String priceText = view.getPrice().getText().trim();
                String description = view.getDescription().getText().trim();
                
                // Validate inputs
                if (priceText.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Please enter a price!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (description.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Please enter a description!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (selectedImagePath.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Please select an image!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Parse price
                double price;
                try {
                    price = Double.parseDouble(priceText);
                    if (price <= 0) {
                        JOptionPane.showMessageDialog(view, "Price must be greater than 0!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Please enter a valid price!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Get quantity (default to 1 if not specified)
                int quantity = 1;
                
                // Create product object
                ProductModel product = new ProductModel(category, price, description, selectedImagePath, quantity);
                product.setUserEmail(adminEmail);
                
                // Add product to database (this adds to products table with image path)
                boolean success = adminDao.addProduct(product, adminEmail);

                // If it's a computer, also add to computers table
                if (success && "Computers".equals(category)) {
                    boolean computerSuccess = adminDao.addComputerProduct(product, adminEmail);
                    System.out.println("Added to computers table: " + computerSuccess);
                }

                System.out.println("Product added to products table with image path: " + selectedImagePath);
                
                if (success) {
                    JOptionPane.showMessageDialog(view, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to add product. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

class UploadImage implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Product Image");
            
            // Set file filter for images
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image files", "jpg", "jpeg", "png", "gif", "bmp");
            fileChooser.setFileFilter(filter);
            
            int result = fileChooser.showOpenDialog(view);
            
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                
                try {
                    // Create images directory if it doesn't exist
                    Path imagesDir = Paths.get("images");
                    if (!Files.exists(imagesDir)) {
                        Files.createDirectories(imagesDir);
                    }
                    
                    // Copy file to images directory
                    String fileName = System.currentTimeMillis() + "_" + selectedFile.getName();
                    Path targetPath = imagesDir.resolve(fileName);
                    Files.copy(selectedFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                    
                    // Store the relative path
                    selectedImagePath = "images/" + fileName;
                    
                    // Update the image display
                    ImageIcon imageIcon = new ImageIcon(targetPath.toString());
                    // Scale the image to fit the label
                    java.awt.Image image = imageIcon.getImage().getScaledInstance(
                        200, 200, java.awt.Image.SCALE_SMOOTH
                    );
                    view.getImage().setIcon(new ImageIcon(image));
                    view.getImage().setText(""); // Remove default text
                    
                    JOptionPane.showMessageDialog(view, "Image uploaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(view, "Failed to upload image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        }
    }

    private void clearForm() {
        view.getPrice().setText("");
        view.getDescription().setText("");
        view.getCategoryname().setSelectedIndex(0);
        setDefaultImage(); // Use the safe method to set default image
        selectedImagePath = "";
    }

    private void setDefaultImage() {
        try {
            // Try to load default image from resources
            java.net.URL imageUrl = getClass().getResource("/imag/imageloader (1).png");
            if (imageUrl != null) {
                view.getImage().setIcon(new ImageIcon(imageUrl));
            } else {
                // If resource not found, set text instead
                view.getImage().setIcon(null);
                view.getImage().setText("Click to upload image");
                view.getImage().setHorizontalAlignment(SwingConstants.CENTER);
            }
        } catch (Exception e) {
            // If any error occurs, just set text
            view.getImage().setIcon(null);
            view.getImage().setText("Click to upload image");
            view.getImage().setHorizontalAlignment(SwingConstants.CENTER);
            System.err.println("Could not load default image: " + e.getMessage());
        }
    }
}