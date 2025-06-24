/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.dao.ProductDAO;
import buysmart.model.CartItem;
import buysmart.view.Dashboard;
import buysmart.view.LoginView;
import buysmart.view.OrdersView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author loq
 */
public class OrdersController {

    private OrdersView ordersView;
    private String email;

    public OrdersController(OrdersView ordersView, String email) {
        this.ordersView = ordersView;
        this.email = email;
        initController();
        loadOrderHistory(); // Call to populate table on initialization
    }

    private void initController() {
        ordersView.backDashboard(new BackDashboard());
        ordersView.ordersLogout(new OrdersLogout());
        ordersView.ordersDelete(new DeleteOrders());
        
    }

    public void open() {
        ordersView.setVisible(true);
    }

    public void close() {
        ordersView.dispose();
    }

    private void loadOrderHistory() {
        try {
            List<CartItem> orders = ProductDAO.getOrderHistory(email);
            DefaultTableModel model = (DefaultTableModel) ordersView.ordersTable().getModel();
            model.setRowCount(0); // Clear existing rows
            for (CartItem item : orders) {
                model.addRow(new Object[]{
                    item.getDescription(),
                    item.getPrice(),
                    item.getQuantity()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(ordersView, "Error loading order history: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(OrdersController.class.getName()).log(Level.SEVERE, "SQLException in loadOrderHistory", ex);
        }
    }

    class OrdersLogout implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ordersView.dispose();
            LoginView loginView = new LoginView();
            LoginController loginController = new LoginController(loginView);
            loginController.open();
        }
    }

    class BackDashboard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ordersView.dispose();
            Dashboard dashboard = new Dashboard(email);
            DashboardController dashboardController = new DashboardController(dashboard, email);
            dashboardController.open();
        }
    }

    class DeleteOrders implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = ordersView.ordersTable();
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(ordersView, "Please select an order to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String description = (String) table.getValueAt(selectedRow, 0);
            double price = (Double) table.getValueAt(selectedRow, 1);
            int quantity = (Integer) table.getValueAt(selectedRow, 2);
            int confirm = JOptionPane.showConfirmDialog(ordersView,
                "Are you sure you want to delete this order?\n" +
                "Item: " + description + ", Price: Rs. " + String.format("%.2f", price) + ", Quantity: " + quantity,
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // Find order_id by matching description, price, and quantity
                    List<CartItem> orders = ProductDAO.getOrderHistory(email);
                    int orderId = -1;
                    for (CartItem item : orders) {
                        if (item.getDescription().equals(description) &&
                            Math.abs(item.getPrice() - price) < 0.01 &&
                            item.getQuantity() == quantity) {
                            orderId = item.getId();
                            break;
                        }
                    }
                    if (orderId != -1) {
                        ProductDAO.deleteOrderItem(orderId);
                        loadOrderHistory(); // Refresh table
                        JOptionPane.showMessageDialog(ordersView, "Order deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(ordersView, "Order not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(ordersView, "Error deleting order: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(OrdersController.class.getName()).log(Level.SEVERE, "SQLException in deleteOrder", ex);
                }
            }
        }
    }
        	

  
}

  