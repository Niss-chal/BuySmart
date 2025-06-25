/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.view.AdminDashboard;

/**
 *
 * @author loq
 */
public class AdminRegistration {
    AdminDashboard view;
    
    public AdminRegistration(AdminDashboard view){
        this.view=view;    
}
    public void open(){
        view.setVisible(true);
    }
    public void close(){
        view.dispose();
    }
    
    
}
