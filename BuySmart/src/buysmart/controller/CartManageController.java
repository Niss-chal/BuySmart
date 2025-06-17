/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.view.CartManage;
import buysmart.view.Dashboard;
import buysmart.view.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



/**
 *
 * @author user
 */
public class CartManageController {
    private CartManage cartmanage;

    public CartManageController(CartManage cartmanage){
        this.cartmanage=cartmanage;
        Logout logout = new Logout();
        this.cartmanage.logout(logout);
        Back back = new Back();
        this.cartmanage.back(back);
}

    public void open(){
        cartmanage.setVisible(true);
    }

    public void close(){
        cartmanage.dispose();
    }
    class Logout implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            cartmanage.dispose();
            LoginView loginview = new LoginView();
            LoginController loginController = new LoginController(loginview);
            loginController.open();
            
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
}