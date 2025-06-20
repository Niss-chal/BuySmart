/*
 * Click nbfs://SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.dao.ProductDAO;
import buysmart.model.BuysmartPaymentModel;
import buysmart.view.CartManage;
import buysmart.view.Dashboard;
import buysmart.view.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author user
 */
    public class CartManageController {
    private CartManage cartmanage;
    private String email;
    private BuysmartPaymentController paymentController;

    public CartManageController(CartManage cartmanage, String email) {
        this.cartmanage = cartmanage;
        this.email = email;
        this.paymentController = new BuysmartPaymentController(cartmanage, new BuysmartPaymentModel(), email);
        Logout logout = new Logout();
        this.cartmanage.logout(logout);
        Back back = new Back();
        this.cartmanage.back(back);
        this.cartmanage.getDeleteButton().addActionListener(new DeleteRowAction());
        this.cartmanage.getIncreaseQuantityButton().addActionListener(new IncreaseQuantityAction());
        this.cartmanage.getDecreaseQuantityButton().addActionListener(new DecreaseQuantityAction());
        this.cartmanage.getPlaceOrderButton().addActionListener(new PlaceOrderAction());
        refreshCart();
    }

    public void refreshCart() {
        try {
            cartmanage.loadCartData(email);
            cartmanage.customizeTableHeader();
            updateTotalPrice();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(cartmanage, "Error refreshing cart: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void open() {
        cartmanage.setVisible(true);
        refreshCart();
    }

    public void close() {
        cartmanage.dispose();
    }

    private void clearCart() {
        try {
            ProductDAO.clearCart(email);
            cartmanage.getTotalMoneyCount().setText("Rs. 0.00");
            cartmanage.loadCartData(email);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(cartmanage, "Error clearing cart UI: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateQuantity(String userEmail, String description, double price, int newQuantity) {
        try {
            ProductDAO.updateCartItemQuantity(userEmail, description, price, newQuantity);
            refreshCart();
        } catch (SQLException e) {
            System.out.println("Error updating quantity: " + e.getMessage());
            JOptionPane.showMessageDialog(cartmanage, "Error updating quantity: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCartItem(String userEmail, String description, double price) {
        try {
            ProductDAO.deleteCartItem(userEmail, description, price);
            refreshCart();
        } catch (SQLException e) {
            System.out.println("Error deleting cart item: " + e.getMessage());
            JOptionPane.showMessageDialog(cartmanage, "Error deleting cart item: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public double calculateTotalPrice() {
        DefaultTableModel model = (DefaultTableModel) cartmanage.getCartTable().getModel();
        double total = 0.0;
        for (int i = 0; i < model.getRowCount(); i++) {
            Object priceObj = model.getValueAt(i, 1);
            Object quantityObj = model.getValueAt(i, 2);
            if (priceObj instanceof Double && quantityObj instanceof Integer) {
                total += (Double) priceObj * (Integer) quantityObj;
            }
        }
        return total;
    }

    public void updateTotalPrice() {
        double total = calculateTotalPrice();
        cartmanage.getTotalMoneyCount().setText("Rs. " + String.format("%.2f", total));
    }

    private void placeOrder() {
        if (!"Credit Card".equals(cartmanage.getPaymentOptionDrop().getSelectedItem())) {
            cartmanage.showPaymentFailure("Please select 'Credit Card' for Stripe payment.");
            return;
        }

        // Delegate to BuysmartPaymentController's PlaceOrderListener
        paymentController.processPayment();
    }

    class Logout implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ProductDAO.clearCart(email);
                clearCart();
                cartmanage.dispose();
                LoginView loginview = new LoginView();
                LoginController loginController = new LoginController(loginview);
                loginController.open();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error clearing cart: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class Back implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cartmanage.dispose();
            Dashboard dashboard = new Dashboard();
            DashboardController dashboardController = new DashboardController(dashboard, email);
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
                deleteCartItem(email, description, price);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to delete.", "Info", JOptionPane.INFORMATION_MESSAGE);
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
                updateQuantity(email, description, price, currentQuantity + 1);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to increase quantity.", "Info", JOptionPane.INFORMATION_MESSAGE);
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
                if (currentQuantity > 1) {
                    updateQuantity(email, description, price, currentQuantity - 1);
                } else {
                    deleteCartItem(email, description, price);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to decrease quantity.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    class PlaceOrderAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            placeOrder();
        }
    }
}
