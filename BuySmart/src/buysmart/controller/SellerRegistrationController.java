package buysmart.controller;

import buysmart.dao.SellerDAO;
import buysmart.model.SellerModel;
import buysmart.view.SellerRegistrationView;
import buysmart.view.SellerLoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.sql.SQLException;

public class SellerRegistrationController {
    private SellerRegistrationView view;

    private static final String email_Regex = "^[\\w.-]+@[\\w.-]+\\.\\w{2,}$";
    private static final String phoneRegex = "^[0-9]{10}$";
    private static final String panRegex = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$";
    private static final String password_Regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    public SellerRegistrationController(SellerRegistrationView view) {
        this.view = view;
        RegisterSeller registerSeller = new RegisterSeller();
        this.view.registerSeller(registerSeller);
        this.view.getPcheckbox().addActionListener(new ToggleNewPassword());
    }

    class RegisterSeller implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String businessName = view.getBusinessnameField().getText();
            String businessAddress = view.getBusinessAddressfield().getText();
            String contactNumber = view.getContactfield().getText();
            String panNumber = view.getPanNumberfield().getText();
            String businessType = (String) view.getBusinessTypeBox().getSelectedItem();
            String email = view.getSelleremailbox().getText();
            String password = new String(view.getSellerPasswordbox().getPassword());

            // Validate all required fields
            if (businessName.isEmpty() || businessAddress.isEmpty() || contactNumber.isEmpty() ||
                panNumber.isEmpty() || businessType == null || businessType.isEmpty() ||
                email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!email.matches(email_Regex)) {
                JOptionPane.showMessageDialog(view, "Invalid email format", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!contactNumber.matches(phoneRegex)) {
                JOptionPane.showMessageDialog(view, "Invalid contact number. Enter 10 digits only", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!panNumber.matches(panRegex)) {
                JOptionPane.showMessageDialog(view, "Invalid PAN number. Format: 5 letters, 4 digits, 1 letter (e.g., ABCDE1234F)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.matches(password_Regex)) {
                JOptionPane.showMessageDialog(view, "Password must be minimum 8 characters, include uppercase, lowercase, digit, and special character", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SellerModel seller = new SellerModel(email, businessName, businessAddress, contactNumber, panNumber, businessType, password);

            try {
                SellerDAO dao = new SellerDAO();
                boolean registered = dao.registerSeller(seller);

                if (registered) {
                    JOptionPane.showMessageDialog(view, "Registration Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                    view.dispose();

                    SellerLoginView loginView = new SellerLoginView();
                    SellerLoginController loginController = new SellerLoginController(loginView);
                    loginController.open();
                } else {
                    JOptionPane.showMessageDialog(view, "Registration Failed! Try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class ToggleNewPassword implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isSelected = view.getPcheckbox().isSelected();
            if (isSelected) {
                view.getSellerPasswordbox().setEchoChar((char) 0); // show password
            } else {
                view.getSellerPasswordbox().setEchoChar('•'); // hide password
            }
        }
    }

    public void open() {
        view.setVisible(true);
    }

    public void close() {
        view.dispose();
    }
}
