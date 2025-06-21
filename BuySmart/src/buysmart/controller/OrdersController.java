/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.view.Dashboard;
import buysmart.view.LoginView;
import buysmart.view.OrdersView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        
         OrdersLogout ordersLogout = new OrdersLogout();
        this.ordersView.ordersLogout(ordersLogout);
    
        BackDashboard backDashboard = new BackDashboard();
        this.ordersView.backDashboard(backDashboard);
    }
    
    public void open(){
        ordersView.setVisible(true);
    }

    public void close() {
        ordersView.dispose();
    }  
    
   
    
    
    
    
    class OrdersLogout implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ordersView.dispose();
            LoginView loginview = new LoginView();
            LoginController loginController = new LoginController(loginview);
            loginController.open();
        }
        
    }
    
    class BackDashboard implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ordersView.dispose();
            Dashboard dashboard = new Dashboard();
            DashboardController dashboardController = new DashboardController(dashboard, email);
            dashboardController.open();
        }
        
    }
}
