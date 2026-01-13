package com.shop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.shop.model.CartItem;
import com.shop.util.DBUtil;

/**
 * Data Access Object for Order operations
 */
public class OrderDao {
    
    /**
     * Place an order for a user with transaction support
     * Creates order and order_items records, updates product quantities atomically
     * @param userId the user placing the order
     * @param cart list of CartItem objects
     * @return the generated order_id if successful
     * @throws SQLException if database error occurs or insufficient stock
     */
    public int placeOrder(int userId, List<CartItem> cart) throws SQLException {
        String insertOrderSql = "INSERT INTO orders(user_id, total_amount) VALUES(?, ?)";
        String insertItemSql = "INSERT INTO order_items(order_id, product_id, qty, price_at_purchase) " +
                               "VALUES(?, ?, ?, ?)";
        String updateStockSql = "UPDATE products SET quantity = quantity - ? " +
                                "WHERE product_id = ? AND quantity >= ?";
        
        // Calculate total amount
        double total = 0;
        for (CartItem cartItem : cart) {
            total += cartItem.getLineTotal();
        }
        
        try (Connection connection = DBUtil.getConnection()) {
            // Disable auto-commit to manage transactions manually
            connection.setAutoCommit(false);
            
            try {
                // Step 1: Insert order and get generated order_id
                int orderId;
                try (PreparedStatement ps = connection.prepareStatement(insertOrderSql, 
                                                                        Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, userId);
                    ps.setDouble(2, total);
                    ps.executeUpdate();
                    
                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                        if (!generatedKeys.next()) {
                            throw new SQLException("Order id not generated.");
                        }
                        orderId = generatedKeys.getInt(1);
                    }
                }
                
                // Step 2: For each cart item, reduce stock and insert order item
                for (CartItem cartItem : cart) {
                    // Step 2a: Update stock (fails if not enough quantity)
                    try (PreparedStatement ps = connection.prepareStatement(updateStockSql)) {
                        ps.setInt(1, cartItem.getQty());
                        ps.setInt(2, cartItem.getProductId());
                        ps.setInt(3, cartItem.getQty());
                        
                        int updated = ps.executeUpdate();
                        if (updated != 1) {
                            throw new SQLException("Not enough stock for product id " + 
                                                   cartItem.getProductId());
                        }
                    }
                    
                    // Step 2b: Insert order item
                    try (PreparedStatement ps = connection.prepareStatement(insertItemSql)) {
                        ps.setInt(1, orderId);
                        ps.setInt(2, cartItem.getProductId());
                        ps.setInt(3, cartItem.getQty());
                        ps.setDouble(4, cartItem.getPrice());
                        ps.executeUpdate();
                    }
                }
                
                // Commit transaction
                connection.commit();
                return orderId;
                
            } catch (SQLException e) {
                // Rollback transaction on error
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }
    
    /**
     * Print all orders for a specific user
     * @param userId the user identifier
     * @throws SQLException if database error occurs
     */
    public void printOrdersForUser(int userId) throws SQLException {
        String sql = "SELECT o.order_id, o.order_time, o.total_amount, " +
                     "  oi.product_id, p.name AS product_name, oi.qty, oi.price_at_purchase " +
                     "FROM orders o " +
                     "JOIN order_items oi ON o.order_id = oi.order_id " +
                     "JOIN products p ON oi.product_id = p.product_id " +
                     "WHERE o.user_id = ? " +
                     "ORDER BY o.order_id DESC";
        
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setInt(1, userId);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("\nORDER_ID | TIME | TOTAL | PID | PRODUCT | QTY | PRICE");
                while (resultSet.next()) {
                    System.out.println(
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
}
