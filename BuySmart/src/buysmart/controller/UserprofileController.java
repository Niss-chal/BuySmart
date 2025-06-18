/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.dao.UserprofileDAO;
import buysmart.model.UserModel;
import buysmart.view.Dashboard;
import buysmart.view.Profileview;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author loq
 */
public class UserprofileController { 
    private Profileview view;
    private String username;

    public UserprofileController(Profileview view,String username){
        this.view = view;
        this.username = username;
        init();
        
        openDashboard backDashboard = new openDashboard();
        this.view.backDashboard(backDashboard);
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
        
        public class openDashboard implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();
            Dashboard dashboard = new Dashboard();
            DashboardController dashboardcontroller = new DashboardController(dashboard,username);
            dashboardcontroller.open();
        }
            
        }
    
    }
    
    

