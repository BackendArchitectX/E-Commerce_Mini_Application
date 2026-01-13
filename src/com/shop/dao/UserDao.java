package com.shop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.shop.model.User;
import com.shop.util.DBUtil;

/**
 * Data Access Object for User operations
 */
public class UserDao {
    
    /**
     * Register a new user
     * @param user User object with registration details
     * @return true if registration successful, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean register(User user) throws SQLException {
        String sql = "INSERT INTO users(role, first_name, last_name, username, password, city, email, mobile) " +
                     "VALUES('USER', ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getCity());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setString(7, user.getMobile());
            
            return preparedStatement.executeUpdate() == 1;
        }
    }
    
    /**
     * Authenticate user login
     * @param username user's username
     * @param password user's password
     * @return User object if login successful, null otherwise
     * @throws SQLException if database error occurs
     */
    public User login(String username, String password) throws SQLException {
        String sql = "SELECT user_id, role, first_name, last_name, username " +
                     "FROM users WHERE username = ? AND password = ?";
        
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setUserId(resultSet.getInt("user_id"));
                    user.setRole(resultSet.getString("role"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setUsername(resultSet.getString("username"));
                    return user;
                }
            }
        }
        return null;
    }
}
