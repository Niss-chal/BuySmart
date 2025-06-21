/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.view.OrdersView;

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
    }
    
    public void open(){
        ordersView.setVisible(true);
    }

    public void close() {
        ordersView.dispose();
    }  
    
    
    
}
