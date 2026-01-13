package com.shop.app;

import java.util.List;

import com.shop.dao.ProductDao;
import com.shop.model.Product;

/**
 * Test class to retrieve and display all products
 */
public class ProductTest {
    
    public static void main(String[] args) {
        try {
            ProductDao productDao = new ProductDao();
            List<Product> products = productDao.getAllProductsSortedByPriceAsc();
            
            System.out.printf("%-6s %-22s %-10s %-6s%n", "ID", "NAME", "PRICE", "QTY");
            System.out.printf("%-6s %-22s %-10s %-6s%n", "------", "----------------------", 
                            "----------", "------");
            
            for (Product product : products) {
                System.out.printf("%-6d %-22s %-10.2f %-6d%n",
                    product.getProductId(),
                    product.getName(),
                    product.getPrice(),
                    product.getQuantity());
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
