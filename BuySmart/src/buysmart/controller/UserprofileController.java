/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.dao.UserprofileDAO;
import buysmart.model.UserModel;
import buysmart.view.UserprofileView;
import buysmart.view.userprofileview;

/**
 *
 * @author loq
 */
public class UserprofileController { 
    private UserprofileView view;
    private String username;
    private userprofileview view;

    public UserprofileController(userprofileview view){
        this.view = view;
        this.username=username;
        init();
    }

    public void open(){
        view.setVisible(true);
    }

    public void close(){
        view.dispose();
    }
    
    public void init (){
       UserprofileDAO dao = new UserprofileDAO();
       UserModel user = dao.getUserByUsername(username);
       
        if(user!=null){
        view.getUpdatename().setText(user.getUsername());
        view.getUpdateemail().setText(user.getEmail());
        view.getUpdateaddress().setText(user.getAddress());
        view.getUpdatecontact().setText(user.getContact());    
        }
        else{
            javax.swing.JOptionPane.showMessageDialog(view,"User Profile not found! ","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
            view.dispose();
        }
    
    }
}
