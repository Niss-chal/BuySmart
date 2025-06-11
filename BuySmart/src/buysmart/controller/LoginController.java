/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;
import buysmart.dao.UserDAO;
import buysmart.model.loginRequest;
import buysmart.view.Dashboard;
import buysmart.view.ForgetPasswordView;
import buysmart.view.LoginView;
import buysmart.view.RegistrationView;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author loq
 */
public class LoginController {
    private LoginView view;
    UserDAO dao= new UserDAO();

    public LoginController(LoginView view) {
        this.view = view;

        this.view.loginUser(new LoginUser());
        this.view.forgotPassword(new ForgotPassword());
        this.view.SignIn(new SignUp());
    }

    public void open() {
        view.setVisible(true);
    }

    public void close() {
        view.dispose();
    }

    class LoginUser implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = view.getEmail().getText();
            String password = String.valueOf(view.getPassword().getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                loginRequest req = new loginRequest(email, password);

                try {
                    
                    boolean success = dao.loginUser(req);

                    if (success) {
                        JOptionPane.showMessageDialog(view, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                        Dashboard dashboard = new Dashboard();
                        DashboardController dashboardController = new DashboardController(dashboard);
                        dashboardController.open();
                        close();
                    } else {
                        JOptionPane.showMessageDialog(view, "Login failed", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (HeadlessException ex) {
                    JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    class ForgotPassword implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            view.dispose();
            ForgetPasswordView forgotPassword = new ForgetPasswordView();
            ForgotPasswordController forgotController = new ForgotPasswordController(forgotPassword);
            forgotController.open();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
        }
    }

    class SignUp implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            view.dispose();
            RegistrationView registerView = new RegistrationView();
            RegisterController registerController = new RegisterController(registerView);
            registerController.open();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
        }
    }
}
