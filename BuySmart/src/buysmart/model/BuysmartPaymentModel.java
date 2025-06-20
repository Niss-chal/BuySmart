/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.model;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.exception.StripeException;

/**
 *
 * @author fahmi
 */
public class BuysmartPaymentModel {
    
    

    public String createCheckoutSession(double totalAmount, String currency) {
        try {
            SessionCreateParams params = SessionCreateParams.builder()
                .addLineItem(
                    SessionCreateParams.LineItem.builder()
                        .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency(currency.toLowerCase())
                                .setUnitAmount((long) (totalAmount * 100))
                                .setProductData(
                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Cart Purchase")
                                        .build()
                                )
                                .build()
                        )
                        .setQuantity(1L)
                        .build()
                )
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://localhost:8080/cancel")
                .build();

            Session session = Session.create(params);
            return session.getUrl();
        } catch (StripeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkPaymentStatus(String sessionUrl) {
        try {
            String sessionId = extractSessionIdFromUrl(sessionUrl);
            if (sessionId == null || sessionId.isEmpty()) {
                System.out.println("Invalid session ID extracted.");
                return false;
            }
            System.out.println("Waiting 60 seconds before checking payment status...");
            Thread.sleep(35000); 
            Session session = Session.retrieve(sessionId);
            System.out.println("ID: " + session);
            System.out.println("Payment: " + session.getPaymentStatus());
            return "paid".equals(session.getPaymentStatus());
        } catch (StripeException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String extractSessionIdFromUrl(String sessionUrl) {
        try {
            String[] parts = sessionUrl.split("/pay/");
            if (parts.length < 2) return null;
            String afterPay = parts[1];
            int hashIndex = afterPay.indexOf('#');
            if (hashIndex != -1) {
                afterPay = afterPay.substring(0, hashIndex);
            }
            return afterPay.trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
