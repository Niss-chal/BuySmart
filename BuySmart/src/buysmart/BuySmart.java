/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package buysmart;

import buysmart.controller.LoginController;
import buysmart.view.LoginView;

/**
 *
 * @author loq
 */
public class BuySmart {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LoginView view = new LoginView();
        LoginController controller = new LoginController(view);
        controller.open();
        // TODO code application logic here
        
        
    }
    
}
