/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.controller;

import buysmart.dao.ProductDAO;
import buysmart.model.ProductModel;
import buysmart.view.Dashboard;
import java.sql.SQLException;
import java.util.List;



/**
 *
 * @author fahmi
 */
public class ProductController {
    
    private Dashboard dashboard;

    public ProductController(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public void loadProduct() {
        try {
            List<ProductModel> products = ProductDAO.getProduct();
            if (!products.isEmpty()) {
                
                // 1st product ko lagi
                if (products.size() > 0) {
                    ProductModel product0 = products.get(0);
                    if (product0 != null) {
                        dashboard.getPicProduct().setText(product0.getImagePath());
                        dashboard.getDescriptionProductCard().setText(product0.getDescription());
                        dashboard.getProductCardPrice().setText("Rs. " + product0.getPrice());
                    }
                }
                // 2nd product ko lagi
                if (products.size() > 1) {
                    ProductModel product1 = products.get(1);
                    if (product1 != null) {
                        dashboard.getPicProduct1().setText(product1.getImagePath());
                        dashboard.getDescriptionProductCard1().setText(product1.getDescription());
                        dashboard.getProductCardPrice1().setText("Rs. " + product1.getPrice());
                    }
                }

                // 3rd product ko lagi
                if (products.size() > 2) {
                    ProductModel product2 = products.get(2);
                    if (product2 != null) {
                        dashboard.getPicProduct2().setText(product2.getImagePath());
                        dashboard.getDescriptionProductCard2().setText(product2.getDescription());
                        dashboard.getProductCardPrice2().setText("Rs. " + product2.getPrice());
                    }
                }
                
                // 4th product ko lagi
                if (products.size() > 3) {
                    ProductModel product3 = products.get(3);
                    if (product3 != null) {
                        dashboard.getPicProduct3().setText(product3.getImagePath());
                        dashboard.getDescriptionProductCard3().setText(product3.getDescription());
                        dashboard.getProductCardPrice3().setText("Rs. " + product3.getPrice());
                    }
                }
                
                // 5th product ko lagi
                if (products.size() > 4) {
                    ProductModel product4 = products.get(4);
                    if (product4 != null) {
                        dashboard.getPicProduct4().setText(product4.getImagePath());
                        dashboard.getDescriptionProductCard4().setText(product4.getDescription());
                        dashboard.getProductCardPrice4().setText("Rs. " + product4.getPrice());
                    }
                }
            } else {
                dashboard.getProductCardPrice().setText("No products found");
            }
        } catch (SQLException e) {
            dashboard.getProductCardPrice().setText("Error loading product");
        }
    }
}

