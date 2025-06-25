/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.dao.ComputersDAO;
import buysmart.model.ComputersModel;
import buysmart.view.ComputersView;
import java.awt.event.MouseListener;
import java.util.List;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author fahmi
 */
public class ComputersController {
    
    private ComputersView computersView;
    private List<ComputersModel> computers;
    private String email;

    public ComputersController(ComputersView computersView, String email) {
        this.computersView = computersView;
        this.email = email;
        // Initialize and attach AddCart listener
        AddCart addCart = new AddCart();
        this.computersView.addCart(addCart);
        // Load computers data
        loadComputers();
    }
    
    public void open(){
        computersView.setVisible(true);
    }
    
    public void close(){
        computersView.dispose();
    }

    public void loadComputers() {
        try {
            computers = ComputersDAO.getComputers();
            if (!computers.isEmpty()) {
                // 1st computer
                if (computers.size() > 0) {
                    ComputersModel computer0 = computers.get(0);
                    if (computer0 != null) {
                        computersView.getComputersPicture1().setText(computer0.getImagePath());
                        computersView.getComputersDescription1().setText(computer0.getDescription());
                        computersView.getComputersPrice1().setText("Rs. " + computer0.getPrice());
                    }
                }
                // 2nd computer
                if (computers.size() > 1) {
                    ComputersModel computer1 = computers.get(1);
                    if (computer1 != null) {
                        computersView.getComputersPicture2().setText(computer1.getImagePath());
                        computersView.getComputersDescription2().setText(computer1.getDescription());
                        computersView.getComputersPrice2().setText("Rs. " + computer1.getPrice());
                    }
                }
                // 3rd computer
                if (computers.size() > 2) {
                    ComputersModel computer2 = computers.get(2);
                    if (computer2 != null) {
                        computersView.getComputersPicture3().setText(computer2.getImagePath());
                        computersView.getComputersDescription3().setText(computer2.getDescription());
                        computersView.getComputersPrice3().setText("Rs. " + computer2.getPrice());
                    }
                }
                // 4th computer
                if (computers.size() > 3) {
                    ComputersModel computer3 = computers.get(3);
                    if (computer3 != null) {
                        computersView.getComputersPicture4().setText(computer3.getImagePath());
                        computersView.getComputersDescription4().setText(computer3.getDescription());
                        computersView.getComputersPrice4().setText("Rs. " + computer3.getPrice());
                    }
                }
                // 5th computer
                if (computers.size() > 4) {
                    ComputersModel computer4 = computers.get(4);
                    if (computer4 != null) {
                        computersView.getComputersPicture5().setText(computer4.getImagePath());
                        computersView.getComputersDescription5().setText(computer4.getDescription());
                        computersView.getComputersPrice5().setText("Rs. " + computer4.getPrice());
                    }
                }
            } else {
                computersView.getComputersPrice1().setText("No computers found");
            }
        } catch (Exception e) {
            computersView.getComputersPrice1().setText("Error loading computers");
            System.err.println("Error loading computers: " + e.getMessage());
        }
    }

    class AddCart implements MouseListener {

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            if (computers == null || computers.isEmpty()) {
                JOptionPane.showMessageDialog(computersView, "No computers available!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JButton sourceButton = (JButton) e.getSource();
            int computerIndex = -1;
            JButton[] cartButtons = {
                computersView.getProductAddToCartButton(),
                computersView.getProductAddToCartButton1(),
                computersView.getProductAddToCartButton2(),
                computersView.getProductAddToCartButton3(),
                computersView.getProductAddToCartButton4()
            };
            
            // Find which button was clicked
            for (int i = 0; i < cartButtons.length; i++) {
                if (sourceButton == cartButtons[i]) {
                    computerIndex = i;
                    break;
                }
            }

            if (computerIndex >= 0 && computerIndex < computers.size()) {
                ComputersModel computer = computers.get(computerIndex);
                try {
                    ComputersDAO.addToCart(email, computer.getDescription(), computer.getPrice(), 1); // Default quantity is 1
                    JOptionPane.showMessageDialog(computersView, "Added to cart successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(computersView, "Error adding to cart: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(computersView, "Computer not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {
        }

        @Override
        public void mouseReleased(java.awt.event.MouseEvent e) {
        }

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
        }
    }
}