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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 *
 * @author loq
 */
public class DashboardController {
    private Dashboard dashboard;

    public DashboardController(Dashboard dashboard){
        this.dashboard=dashboard;
        
        Logout logout = new Logout();
        this.dashboard.logout(logout);
        
        Cart cart = new Cart();
        this.dashboard.cart(cart);
                
    }

    public void open(){
        dashboard.setVisible(true);
    }

    public void close(){
        dashboard.dispose();
    }  
    
    // Logoutbutton Function
    class Logout implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            dashboard.dispose();
            LoginView loginview = new LoginView();
            LoginController loginController = new LoginController(loginview);
            loginController.open();
            
        }
    }
    
    class Cart implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            dashboard.dispose();
            CartManage cartmanage = new CartManage();
            CartManageController cartmanageController = new CartManageController(cartmanage);
            cartmanageController.open();
            
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
    }

    