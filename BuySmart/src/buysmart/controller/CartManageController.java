/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.dao.ProductDAO;
import buysmart.view.CartManage;
import buysmart.view.Dashboard;
import buysmart.view.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author user
 */
public class CartManageController {
    private CartManage cartmanage;

    public CartManageController(CartManage cartmanage) {
    this.cartmanage = cartmanage;
    Logout logout = new Logout();
    this.cartmanage.logout(logout);
    Back back = new Back();
    this.cartmanage.back(back);
    this.cartmanage.getDeleteButton().addActionListener(new DeleteRowAction());
    cartmanage.loadCartData(); // Load initial data
}

// Add a method to refresh cart (call this after adding items if needed)
    public void refreshCart() {
    cartmanage.loadCartData();
}
    public void open(){
        cartmanage.setVisible(true);
        refreshCart();
    }

    public void close(){
        cartmanage.dispose();
    }
    private void clearCart() {
        cartmanage.getTotalMoneyCount().setText("Rs. 0.00"); // Update total to reflect empty cart
    }
    
    class Logout implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
        try {
            ProductDAO.clearCart(); // Clear database cart
            clearCart(); // Reset UI total
            cartmanage.dispose();
            LoginView loginview = new LoginView();
            LoginController loginController = new LoginController(loginview);
            loginController.open();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error clearing cart: " + ex.getMessage());
        }
    }
}

    class Back implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            cartmanage.dispose();
            Dashboard dashboard = new Dashboard();
            DashboardController dashboardController = new DashboardController(dashboard);
            dashboardController.open();
            
        }
    }
    class DeleteRowAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JTable table = cartmanage.getCartTable();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            String description = (String) model.getValueAt(selectedRow, 0);
            double price = (Double) model.getValueAt(selectedRow, 1);
            try {
                // Delete from database
                ProductDAO.deleteCartItem(description, price);
                // Remove from table
                model.removeRow(selectedRow);
                // Update total price
                cartmanage.updateTotalPrice();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error deleting item: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to delete.");
        }
    }
    }
}
