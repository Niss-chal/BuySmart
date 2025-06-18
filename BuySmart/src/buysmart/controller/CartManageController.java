/*
 * Click nbfs://SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.model.UserModel;
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
        // Add action listeners for quantity buttons
        this.cartmanage.getIncreaseQuantityButton().addActionListener(new IncreaseQuantityAction());
        this.cartmanage.getDecreaseQuantityButton().addActionListener(new DecreaseQuantityAction());
        cartmanage.loadCartData(); // Load initial data
        cartmanage.customizeTableHeader();
    }

    public void refreshCart() {
        cartmanage.loadCartData();
        cartmanage.customizeTableHeader();
    }

    public void open() {
        cartmanage.setVisible(true);
        refreshCart();
    }

    public void close() {
        cartmanage.dispose();
    }

    private void clearCart() {
        cartmanage.getTotalMoneyCount().setText("Rs. 0.00"); // Update total to reflect empty cart
    }

    class Logout implements ActionListener {
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

    class Back implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UserModel usermodel = new UserModel();
            String username = usermodel.getUsername();
            cartmanage.dispose();
            Dashboard dashboard = new Dashboard();
            DashboardController dashboardController = new DashboardController(dashboard,username);
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
                    ProductDAO.deleteCartItem(description, price);
                    model.removeRow(selectedRow);
                    cartmanage.updateTotalPrice();
                    cartmanage.customizeTableHeader();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error deleting item: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to delete.");
            }
        }
    }

    class IncreaseQuantityAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = cartmanage.getCartTable();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int selectedRow = table.getSelectedRow();

            if (selectedRow != -1) {
                String description = (String) model.getValueAt(selectedRow, 0);
                double price = (Double) model.getValueAt(selectedRow, 1);
                int currentQuantity = (Integer) model.getValueAt(selectedRow, 2);
                try {
                    ProductDAO.updateCartItemQuantity(description, price, currentQuantity + 1);
                    cartmanage.loadCartData(); // Refresh table and total price
                    cartmanage.customizeTableHeader();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error increasing quantity: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to increase quantity.");
            }
        }
    }

    class DecreaseQuantityAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = cartmanage.getCartTable();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int selectedRow = table.getSelectedRow();

            if (selectedRow != -1) {
                String description = (String) model.getValueAt(selectedRow, 0);
                double price = (Double) model.getValueAt(selectedRow, 1);
                int currentQuantity = (Integer) model.getValueAt(selectedRow, 2);
                try {
                    if (currentQuantity > 1) {
                        ProductDAO.updateCartItemQuantity(description, price, currentQuantity - 1);
                    } else {
                        ProductDAO.deleteCartItem(description, price);
                    }
                    cartmanage.loadCartData(); // Refresh table and total price
                    cartmanage.customizeTableHeader();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error decreasing quantity: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to decrease quantity.");
            }
        }
    }
}
