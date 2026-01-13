package com.shop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.shop.model.Product;
import com.shop.util.DBUtil;

/**
 * Data Access Object for Admin operations
 */
public class AdminDao {
    
    /**
     * Add a new product to the inventory
     * @param product Product object to add
     * @return true if product added successfully, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products(product_id, name, description, price, quantity) " +
                     "VALUES(?, ?, ?, ?, ?)";
        
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setInt(1, product.getProductId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setInt(5, product.getQuantity());
            
            return preparedStatement.executeUpdate() == 1;
        }
    }
    
    /**
     * Print user's order history
     * @param username the username to search for
     * @throws SQLException if database error occurs
     */
    public void printUserHistoryByUsername(String username) throws SQLException {
        String sql = "SELECT u.username, o.order_id, o.order_time, o.total_amount, " +
                     "  oi.product_id, p.name AS product_name, oi.qty, oi.price_at_purchase " +
                     "FROM users u " +
                     "JOIN orders o ON u.user_id = o.user_id " +
                     "JOIN order_items oi ON o.order_id = oi.order_id " +
                     "JOIN products p ON oi.product_id = p.product_id " +
                     "WHERE u.username = ? " +
                     "ORDER BY o.order_id DESC";
        
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setString(1, username);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("\nUSERNAME | ORDER_ID | TIME | TOTAL | PID | PRODUCT | QTY | PRICE");
                while (resultSet.next()) {
                    System.out.println(
                        resultSet.getString("username") + " | " +
                        resultSet.getInt("order_id") + " | " +
                        resultSet.getTimestamp("order_time") + " | " +
                        resultSet.getDouble("total_amount") + " | " +
                        resultSet.getInt("product_id") + " | " +
                        resultSet.getString("product_name") + " | " +
                        resultSet.getInt("qty") + " | " +
                        resultSet.getDouble("price_at_purchase")
                    );
                }
            }
        }
    }
    
    /**
     * Print all registered users
     * @throws SQLException if database error occurs
     */
    public void printAllUsers() throws SQLException {
        String sql = "SELECT user_id, role, first_name, last_name, username, email, mobile, city " +
                     "FROM users ORDER BY user_id";
        
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            
            System.out.println("\nID | ROLE | NAME | USERNAME | EMAIL | MOBILE | CITY");
            boolean found = false;
            
            while (resultSet.next()) {
                found = true;
                System.out.println(
                    resultSet.getInt("user_id") + " | " +
                    resultSet.getString("role") + " | " +
                    resultSet.getString("first_name") + " " + resultSet.getString("last_name") + " | " +
                    resultSet.getString("username") + " | " +
                    resultSet.getString("email") + " | " +
                    resultSet.getString("mobile") + " | " +
                    resultSet.getString("city")
                );
            }
            
            if (!found) {
                System.out.println("No users found.");
            }
        }
    }
    
    /**
     * Check the quantity of a product
     * @param productId the product identifier
     * @return quantity available, null if product not found
     * @throws SQLException if database error occurs
     */
    public Integer checkQuantity(int productId) throws SQLException {
        String sql = "SELECT quantity FROM products WHERE product_id = ?";
        
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setInt(1, productId);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("quantity");
                }
            }
        }
        return null;
    }
}
