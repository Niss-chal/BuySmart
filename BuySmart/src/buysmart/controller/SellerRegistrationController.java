/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;


import buysmart.view.SellerRegistrationView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


/**
 *
 * @author batas
 */
public class SellerRegistrationController {
    private SellerRegistrationView view;
    
    private static final String phoneRegex= "^[0-9]{10}";
    private static final String panRegex="[A-Z]{5}[0-9]{4}[A-Z}{1}";
    
    public SellerRegistrationController(SellerRegistrationView view){
        this.view=view;
        
        this.view.registerSeller(new RegisterSeller());
    }
    public void open(){
        view.setVisible(true);
    }
    public void close(){
        view.dispose();
    }
    
    class RegisterSeller implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String businessName = view.getBusinessname().getText();
            String businessAddress = view.getBusinessAddress().getText();
            String contactNumber = view.getContactNumber().getText();
            String panNumber = view.getPanNumber().getText();
            String businessTypebox = (String) view.getBusinessTypeBox().getSelectedItem();

            
            if (businessName.isEmpty() || businessAddress.isEmpty() || contactNumber.isEmpty() ||
                panNumber.isEmpty() || businessTypebox == null || businessTypebox.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        }
            if (!contactNumber.matches(phoneRegex)) {
                JOptionPane.showMessageDialog(view, "Invalid contact number. Enter 10 digits only", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!panNumber.matches(panRegex)) {
                JOptionPane.showMessageDialog(view, "Invalid pan number. Format:5 letters , 4 digits, 1 letters (e.g., ABCDE1234F)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
           JOptionPane.showMessageDialog(view, "Registration Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
        
    }
}
