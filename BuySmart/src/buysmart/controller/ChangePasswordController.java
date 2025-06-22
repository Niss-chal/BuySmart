/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.view.ChangePassword;
import buysmart.view.Dashboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author User
 */
public class ChangePasswordController {
    
    private ChangePassword view;
    private String email;
    
    public ChangePasswordController(ChangePassword view,String email){
        this.view=view;
        this.email=email;
        PasswordBack changeBack = new PasswordBack();
        this.view.changeBack(changeBack);
        
        
        
    }
    
    
    class PasswordBack implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();
            Dashboard dashboard = new Dashboard();
            DashboardController dashboardcontroller = new DashboardController(dashboard,email);
            dashboardcontroller.open();
        }
        
    }
    
    
}
