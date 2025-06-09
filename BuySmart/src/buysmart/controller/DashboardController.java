/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.view.Dashboard;

/**
 *
 * @author loq
 */
public class DashboardController {
    private Dashboard dashboard;

    public DashboardController(Dashboard dashboard){
        this.dashboard=dashboard;
    }

    public void open(){
        dashboard.setVisible(true);
    }

    public void close(){
        dashboard.dispose();
    }

    
    
}
