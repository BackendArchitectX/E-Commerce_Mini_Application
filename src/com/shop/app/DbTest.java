package com.shop.app;

import java.sql.Connection;

import com.shop.util.DBUtil;

/**
 * Test class to verify database connectivity
 */
public class DbTest {
    
    public static void main(String[] args) {
        try (Connection connection = DBUtil.getConnection()) {
            boolean connected = connection != null && !connection.isClosed();
            System.out.println("CONNECTED: " + connected);
        } catch (Exception e) {
            System.err.println("Connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
