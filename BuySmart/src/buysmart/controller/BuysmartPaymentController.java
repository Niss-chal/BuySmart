/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.dao.ProductDAO;
import buysmart.model.BuysmartPaymentModel;
import buysmart.view.CartManage;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author fahmi
 */
public class BuysmartPaymentController {
    
    private CartManage view;
    private BuysmartPaymentModel model;
    private String email;

    public BuysmartPaymentController(CartManage view, BuysmartPaymentModel model, String email) {
        this.view = view;
        this.model = model;
        this.email = email;
        view.getPlaceOrderButton().addActionListener(new PlaceOrderListener());
    }

    public void processPayment() {
        new PlaceOrderListener().actionPerformed(new ActionEvent(view.getPlaceOrderButton(), ActionEvent.ACTION_PERFORMED, "PlaceOrder"));
    }

    private class PlaceOrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!"Credit Card".equals(view.getPaymentOptionDrop().getSelectedItem())) {
                view.showPaymentFailure("Please select 'Credit Card' for Stripe payment.");
                return;
            }

            JOptionPane.showMessageDialog(view, "Processing payment...", "Payment Status", JOptionPane.INFORMATION_MESSAGE);

            try {
                String totalText = view.getTotalMoneyCount().getText().replace("Rs.", "").trim();
                double totalAmount = Double.parseDouble(totalText);
                String currency = "npr";

                String sessionUrl = model.createCheckoutSession(totalAmount, currency);
                System.out.println("Session URL: " + sessionUrl);

                if (sessionUrl != null) {
                    JOptionPane.showMessageDialog(view, "Redirecting to payment page...", "Payment Status", JOptionPane.INFORMATION_MESSAGE);
                    view.getPlaceOrderButton().setEnabled(false);

                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().browse(new java.net.URI(sessionUrl));
                    } else {
                        view.showPaymentFailure("Browser not supported.");
                        view.getPlaceOrderButton().setEnabled(true);
                        return;
                    }

                    boolean paymentStatus = model.checkPaymentStatus(sessionUrl);
                    view.getPlaceOrderButton().setEnabled(true);
                    if (paymentStatus) {
                        // Clear cart on successful payment
                        try {
                            ProductDAO.clearCart(email);
                            // Verify cart is empty
                            if (ProductDAO.getCartItems(email).isEmpty()) {
                                view.loadCartData(email);
                                view.getTotalMoneyCount().setText("Rs. 0.00");
                                view.showPaymentSuccess();
                            } else {
                                view.showPaymentFailure("Cart clearing incomplete. Please contact support.");
                                Logger.getLogger(BuysmartPaymentController.class.getName()).log(Level.SEVERE, "Cart not fully cleared for user: " + email);
                            }
                        } catch (SQLException ex) {
                            view.showPaymentFailure("Error clearing cart: " + ex.getMessage());
                            Logger.getLogger(BuysmartPaymentController.class.getName()).log(Level.SEVERE, "SQLException during cart clear: ", ex);
                        }
                    } else {
                        view.showPaymentFailure("Payment failed or not completed.");
                    }
                } else {
                    view.showPaymentFailure("Failed to create checkout session.");
                    view.getPlaceOrderButton().setEnabled(true);
                }
            } catch (IOException | URISyntaxException | NumberFormatException ex) {
                view.showPaymentFailure("Error: " + ex.getMessage());
                view.getPlaceOrderButton().setEnabled(true);
                Logger.getLogger(BuysmartPaymentController.class.getName()).log(Level.SEVERE, "Exception during payment: ", ex);
            }
        }
    }

    public void open() {
        view.setVisible(true);
    }
    
}
