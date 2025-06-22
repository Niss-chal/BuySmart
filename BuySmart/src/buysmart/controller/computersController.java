/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.dao.computersDAO;
import buysmart.model.computersModel;
import buysmart.view.ComputersView;
import java.util.List;
import javax.swing.JOptionPane;


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
                if (computers.isEmpty()) {
                    JOptionPane.showMessageDialog(computersView, "No computers found in the database.", "Warning", JOptionPane.WARNING_MESSAGE);
                    computersView.getComputersPrice1().setText("No computers found");
                    return;
                }
                
                // 1st computer
                if (computers.size() > 0) {
                    computersModel computer0 = computers.get(0);
                    if (computer0 != null) {
//                        computersView.getComputersPicture1().setText(computer0.getImagePath());
                        computersView.getComputersDescription1().setText(computer0.getDescription());
                        computersView.getComputersPrice1().setText("Rs. " + computer0.getPrice());
                    }
                }
                // 2nd computer
                if (computers.size() > 1) {
                    computersModel computer1 = computers.get(1);
                    if (computer1 != null) {
//                        computersView.getComputersPicture2().setText(computer1.getImagePath());
                        computersView.getComputersDescription2().setText(computer1.getDescription());
                        computersView.getComputersPrice2().setText("Rs. " + computer1.getPrice());
                    }
                }
                // 3rd computer
                if (computers.size() > 2) {
                    computersModel computer2 = computers.get(2);
                    if (computer2 != null) {
//                        computersView.getComputersPicture3().setText(computer2.getImagePath());
                        computersView.getComputersDescription3().setText(computer2.getDescription());
                        computersView.getComputersPrice3().setText("Rs. " + computer2.getPrice());
                    }
                }
                // 4th computer
                if (computers.size() > 3) {
                    computersModel computer3 = computers.get(3);
                    if (computer3 != null) {
//                        computersView.getComputersPicture4().setText(computer3.getImagePath());
                        computersView.getComputersDescription4().setText(computer3.getDescription());
                        computersView.getComputersPrice4().setText("Rs. " + computer3.getPrice());
                    }
                }
                // 5th computer
                if (computers.size() > 4) {
                    computersModel computer4 = computers.get(4);
                    if (computer4 != null) {
//                        computersView.getComputersPicture5().setText(computer4.getImagePath());
                        computersView.getComputersDescription5().setText(computer4.getDescription());
                        computersView.getComputersPrice5().setText("Rs. " + computer4.getPrice());
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
