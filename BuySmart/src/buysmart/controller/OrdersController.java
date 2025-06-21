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
            Dashboard dashboard = new Dashboard();
            DashboardController dashboardController = new DashboardController(dashboard, email);
            dashboardController.open();
        }
    }

  
}

  