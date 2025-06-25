/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.dao.ProductDAO;
import buysmart.model.ProductModel;
import buysmart.view.Dashboard;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;



/**
 *
 * @author fahmi
 */
public class ProductController {
    
    private Dashboard dashboard;
    private List<ProductModel> currentProducts;
    private JPanel productsContainer;
    private String userEmail;

    public ProductController(Dashboard dashboard, String email) {
        this.dashboard = dashboard;
        this.userEmail = email; // pass this from Dashboard constructor
        
        // Create container for multiple products
        productsContainer = new JPanel();
        productsContainer.setLayout(new GridLayout(0, 3, 10, 10)); // 3 columns, auto rows, 10px gaps
        productsContainer.setBackground(Color.WHITE);
        
        // Set the container in the scroll pane
        dashboard.getproductPanel1().setViewportView(productsContainer);
    }

    /**
     * Load and display all products in the dashboard
     */
    public void loadProduct() {
        try {
            currentProducts = ProductDAO.getProduct();
            displayProducts();
        } catch (SQLException e) {
            System.err.println("Error loading products: " + e.getMessage());
            showErrorMessage("Error loading products: " + e.getMessage());
        }
    }
    
    /**
     * Load products by category
     */
    public void loadProductsByCategory(String category) {
        try {
            if ("All Products".equals(category) || category == null) {
                currentProducts = ProductDAO.getProduct();
            } else if ("Computers".equals(category)) {
                // Load specifically from computers table for computer category
                currentProducts = ProductDAO.getComputerProducts();
                System.out.println("Loading from computers table, found: " + currentProducts.size() + " products");
            } else {
                // Load from products table and filter by category
                currentProducts = ProductDAO.getProductsByCategory(category);
                System.out.println("Loading from products table for category: " + category + ", found: " + currentProducts.size() + " products");
            }
            displayProducts();
        } catch (SQLException e) {
            System.err.println("Error loading products by category: " + e.getMessage());
            showErrorMessage("Error loading products by category: " + e.getMessage());
        }
    }
    
    /**
     * Display all products by creating multiple product cards
     */
    private void displayProducts() {
        // Clear existing products
        productsContainer.removeAll();
        
        if (currentProducts != null && !currentProducts.isEmpty()) {
            for (int i = 0; i < currentProducts.size(); i++) {
                ProductModel product = currentProducts.get(i);
                if (product != null) {
                    // Create a product card for each product
                    JPanel productCard = createProductCard(product, i);
                    productsContainer.add(productCard);
                    
                    System.out.println("Created product card " + i + ": " + 
                                      product.getDescription() + ", Price: " + product.getPrice() + 
                                      ", Image Path: '" + product.getImagePath() + "'");
                }
            }
            
            System.out.println("Created " + currentProducts.size() + " product cards");
        } else {
            // Show "No products found" message
            JLabel noProductsLabel = new JLabel("No products found", SwingConstants.CENTER);
            noProductsLabel.setFont(new Font("Arial", Font.BOLD, 18));
            noProductsLabel.setForeground(Color.GRAY);
            productsContainer.add(noProductsLabel);
        }
        
        // Refresh the display
        refreshDisplay();
    }
    
    /**
     * Create a single product card panel
     */
    private JPanel createProductCard(ProductModel product, int index) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(250, 350));
        
        // Image panel
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(240, 200));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        // Load product image
        loadProductImage(imageLabel, product.getImagePath());
        
        // Info panel (description + price + button)
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Description
        JLabel descriptionLabel = new JLabel("<html><div style='text-align: center; width: 200px;'>" + 
                                           product.getDescription() + "</div></html>");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Price
        JLabel priceLabel = new JLabel(String.format("Rs. %.2f", product.getPrice()));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        priceLabel.setForeground(new Color(0, 128, 0)); // Green color
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add to Cart button
        JButton addToCartButton = new JButton("Add To Cart");
        addToCartButton.setBackground(new Color(153, 204, 255));
        addToCartButton.setForeground(Color.BLACK);
        addToCartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addToCartButton.setMaximumSize(new Dimension(120, 30));
        
        // Add action listener for Add to Cart
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart(product);
            }
        });
        
        // Add components to info panel
        infoPanel.add(descriptionLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(priceLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(addToCartButton);
        
        // Add to card
        card.add(imageLabel, BorderLayout.CENTER);
        card.add(infoPanel, BorderLayout.SOUTH);
        
        return card;
    }
    
    /**
     * Load and display product image with proper scaling
     */
    private void loadProductImage(JLabel imageLabel, String imagePath) {
        System.out.println("Loading image for path: '" + imagePath + "'");
        
        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                File imageFile = new File(imagePath);
                
                if (imageFile.exists()) {
                    // Load the image
                    ImageIcon originalIcon = new ImageIcon(imagePath);
                    
                    // Scale the image to fit the label (240x200)
                    Image scaledImage = originalIcon.getImage().getScaledInstance(
                        240, 200, Image.SCALE_SMOOTH
                    );
                    
                    // Set the scaled image
                    imageLabel.setIcon(new ImageIcon(scaledImage));
                    imageLabel.setText(""); // Clear text when image is loaded
                    
                } else {
                    // Image file doesn't exist
                    imageLabel.setIcon(null);
                    imageLabel.setText("<html><center>Image<br>not found</center></html>");
                    System.err.println("Image file not found: " + imagePath);
                }
            } else {
                // No image path provided
                imageLabel.setIcon(null);
                imageLabel.setText("<html><center>No<br>Image</center></html>");
            }
        } catch (Exception e) {
            // Error loading image
            imageLabel.setIcon(null);
            imageLabel.setText("<html><center>Error loading<br>image</center></html>");
            System.err.println("Error loading image " + imagePath + ": " + e.getMessage());
        }
    }
    
    /**
     * Add product to cart
     */
    private void addToCart(ProductModel product) {
        try {
            ProductDAO.addToCart(userEmail, product.getDescription(), product.getPrice(), 1);
            JOptionPane.showMessageDialog(dashboard, 
                "Added to cart successfully!\n" + product.getDescription(), 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(dashboard, 
                "Error adding to cart: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Show error message to user
     */
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
            dashboard, 
            message, 
            "Error", 
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Refresh the display
     */
    private void refreshDisplay() {
        productsContainer.revalidate();
        productsContainer.repaint();
        dashboard.getproductPanel1().revalidate();
        dashboard.getproductPanel1().repaint();
    }
    
    /**
     * Get current products list
     */
    public List<ProductModel> getCurrentProducts() {
        return currentProducts;
    }
    
    /**
     * Get total number of loaded products
     */
    public int getProductCount() {
        return currentProducts != null ? currentProducts.size() : 0;
    }
    
    /**
     * Check if products are loaded
     */
    public boolean hasProducts() {
        return currentProducts != null && !currentProducts.isEmpty();
    }
}
    
    
