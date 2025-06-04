/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.view.Dashboard;
import buysmart.view.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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
}