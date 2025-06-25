/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.dao.computersDAO;
import buysmart.model.computersModel;
import buysmart.view.ComputersView;
import java.util.List;


/**
 *
 * @author fahmi
 */
public class computersController {
    
    private ComputersView computersView;
    private List<computersModel> computers;
    private String email;

    public computersController(ComputersView computersView, String email) {
        this.computersView = computersView;
        this.email = email;
        
    }

    public void loadComputers() {
        try {
            computers = computersDAO.getComputers();
            if (!computers.isEmpty()) {
                // 1st computer
                if (computers.size() > 0) {
                    computersModel computer0 = computers.get(0);
                    if (computer0 != null) {
                        computersView.getComputersPicture1().setText(computer0.getImagePath());
                        computersView.getComputersDescription1().setText(computer0.getDescription());
                        computersView.getComputersPrice1().setText("Rs. " + computer0.getPrice());
                    }
                }
               
            } else {
                computersView.getComputersPrice1().setText("No computers found");
            }
        } catch (Exception e) {
            computersView.getComputersPrice1().setText("Error loading computers");
            System.err.println("Error loading computers: " + e.getMessage());
        }
    }
    
}
