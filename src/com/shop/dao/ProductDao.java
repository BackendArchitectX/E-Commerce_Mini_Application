package com.shop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shop.model.Product;
import com.shop.util.DBUtil;

/**
 * Data Access Object for Product operations
 */
public class ProductDao {
    
    /**
     * Retrieve all products sorted by price in ascending order
     * @return List of Product objects sorted by price
     * @throws SQLException if database error occurs
     */
    public List<Product> getAllProductsSortedByPriceAsc() throws SQLException {
        String sql = "SELECT product_id, name, description, price, quantity " +
                     "FROM products ORDER BY price ASC";
        
        List<Product> productList = new ArrayList<>();
        
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("product_id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                product.setQuantity(resultSet.getInt("quantity"));
                productList.add(product);
            }
        }
        return productList;
    }
    
    /**
     * Get a product by its ID
     * @param productId the product identifier
     * @return Product object if found, null otherwise
     * @throws SQLException if database error occurs
     */
    public Product getById(int productId) throws SQLException {
        String sql = "SELECT product_id, name, description, price, quantity FROM products WHERE product_id = ?";
        
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setInt(1, productId);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Product product = new Product();
                    product.setProductId(resultSet.getInt("product_id"));
                    product.setName(resultSet.getString("name"));
                    product.setDescription(resultSet.getString("description"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setQuantity(resultSet.getInt("quantity"));
                    return product;
                }
            }
        }
        return null;
    }
    
    /**
     * Reduce product quantity if sufficient stock exists
     * @param productId the product identifier
     * @param qty quantity to reduce
     * @return true if quantity was reduced successfully, false if insufficient stock
     * @throws SQLException if database error occurs
     */
    public boolean reduceQuantity(int productId, int qty) throws SQLException {
        String sql = "UPDATE products SET quantity = quantity - ? " +
                     "WHERE product_id = ? AND quantity >= ?";
        
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setInt(1, qty);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, qty);
            
            return preparedStatement.executeUpdate() == 1;
        }
    }
}
