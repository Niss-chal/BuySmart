/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.view.UserprofileView;

/**
 *
 * @author loq
 */
public class UserprofileController { 
    private UserprofileView view;

    public UserprofileController(UserprofileView view){
        this.view = view;
    }

    public void open(){
        view.setVisible(true);
    }

    public void close(){
        view.dispose();
    }
}
