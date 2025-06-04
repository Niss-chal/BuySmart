/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.view.CartManage;



/**
 *
 * @author user
 */
public class CartManageController {
    private CartManage cartmanage;

    public CartManageController(CartManage cartmanage){
        this.cartmanage=cartmanage;
}

    public void open(){
        cartmanage.setVisible(true);
    }

    public void close(){
        cartmanage.dispose();
    }
    
}