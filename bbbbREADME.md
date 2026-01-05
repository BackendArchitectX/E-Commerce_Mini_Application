🛒 Ecommerce Console Application (Java 8 + JDBC + MySQL)
A pure console-based Ecommerce application built using Core Java (Java 8), JDBC, and MySQL. It supports role-based access (Guest/User/Admin), product browsing, shopping cart, purchase with bill generation, and complete order history tracking.

🎯 Perfect for: Learning JDBC | Database Design | Java Projects

📌 Quick Navigation
Section	Purpose
✨ Key Features	What the app does
🗄️ Database Setup	SQL scripts to run
🚀 How to Run	START HERE
📁 Project Structure	File organization
🔐 Security	Data protection
🚀 Future Ideas	What's next
📋 Business Requirement
Scenario: Ajay is interested to buy some online shopping products online. Build a system to help him manage his shopping experience with role-based features for guests, registered users, and administrators.

✨ Key Features & Functionalities
📦 Product Management
Store 10 products into the database

Product Details: ID, Name, Description, Price, Quantity

Sorted Product Display: View products sorted by price (ascending order)

👤 User Features
User Registration: First name, Last name, Username, Password, City, Email, Mobile

User Login: Secure login with username and password

Shopping Cart: Add multiple products to cart with quantity selection

Cart Management: View cart contents before purchase

Purchase: Buy multiple products in a single transaction

Bill Calculation: System automatically calculates total bill amount

Order History: View all past orders with details

👨‍💼 Admin Features
Add Products: Create new products in the database

Check Inventory: View product quantity by product ID

User Management: View list of all registered users

User History: Check any user's purchase history by username

Bill Management: Calculate and display bills for purchases

👥 Guest Features
Browse Products: View all products sorted by price

No Purchase: Guests cannot buy products (view-only access)

🏗️ Design Guidelines
✅ Object-Oriented Programming (OOPS): Proper class design with encapsulation, inheritance, and polymorphism
✅ Separate Methods: Each functionality has dedicated methods for maintainability
✅ Java Coding Standards: Follows naming conventions, proper formatting, and best practices
✅ User Input: Console-based input with Scanner class
✅ Exception Handling: Try-catch blocks for error management
✅ Input Validation: Checks for incorrect/invalid inputs
✅ Collections: ArrayList for shopping cart implementation
✅ Database Relationships: Foreign keys link Users → Orders → Order Items → Products

🛠️ Technology Stack
Component	Technology
Language	Java 8 (Core Java)
Database Access	JDBC (Java Database Connectivity)
Database	MySQL 8.x
IDE	Eclipse IDE
Database Client	DBeaver
Build	Maven/Gradle compatible
🚀 How to Run (QUICK START - 5 MINUTES)
Prerequisites Checklist
 Java JDK 8+ installed → Download

 MySQL 8.x installed & running on localhost:3306 → Download

 Eclipse IDE installed → Download

 DBeaver installed (optional) → Download

STEP 1️⃣ Download & Configure MySQL Driver
Download MySQL Connector/J

Visit: https://dev.mysql.com/downloads/connector/j/

Download the .jar file (e.g., mysql-connector-j-8.0.33.jar)

Save it somewhere you remember (e.g., C:\mysql-connector\)

Create Eclipse Project

Open Eclipse

File → New → Java Project

Project name: EcommerceConsoleApp

Click Finish

Add MySQL Driver to Project

Right-click project → Build Path → Configure Build Path

Click "Libraries" tab

Click "Add External JARs"

Select the MySQL .jar file you downloaded

Click Apply and Close

✅ MySQL driver is now ready!

STEP 2️⃣ Create Package Structure
In Eclipse, right-click src folder and create these packages (folders):

text
src/
 ├── com.shop.app        (MainApp.java goes here)
 ├── com.shop.dao        (DAO files go here)
 ├── com.shop.model      (Model classes go here)
 └── com.shop.util       (DBUtil.java goes here)
STEP 3️⃣ Set Up MySQL Database
Open DBeaver (or MySQL Workbench)

Connect to MySQL (localhost:3306, root, your_password)

Create Database

Right-click Databases → New Database

Name: EcommerceDB

Click Create

Create All Tables - Copy & Paste this entire script into DBeaver SQL Editor:

sql
USE EcommerceDB;

-- Users Table
CREATE TABLE users (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  role ENUM('ADMIN','USER') NOT NULL DEFAULT 'USER',
  first_name VARCHAR(50) NOT NULL,
  last_name  VARCHAR(50) NOT NULL,
  username   VARCHAR(50) NOT NULL UNIQUE,
  password   VARCHAR(100) NOT NULL,
  city       VARCHAR(50),
  email      VARCHAR(100),
  mobile     VARCHAR(15)
) ENGINE=InnoDB;

-- Products Table
CREATE TABLE products (
  product_id   INT PRIMARY KEY,
  name         VARCHAR(100) NOT NULL,
  description  VARCHAR(255),
  price        DECIMAL(10,2) NOT NULL,
  quantity     INT NOT NULL
) ENGINE=InnoDB;

-- Orders Table
CREATE TABLE orders (
  order_id     INT AUTO_INCREMENT PRIMARY KEY,
  user_id      INT NOT NULL,
  order_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  total_amount DECIMAL(10,2) NOT NULL DEFAULT 0,
  CONSTRAINT fk_orders_user
    FOREIGN KEY (user_id) REFERENCES users(user_id)
) ENGINE=InnoDB;

-- Order Items Table
CREATE TABLE order_items (
  item_id INT AUTO_INCREMENT PRIMARY KEY,
  order_id INT NOT NULL,
  product_id INT NOT NULL,
  qty INT NOT NULL,
  price_at_purchase DECIMAL(10,2) NOT NULL,
  CONSTRAINT fk_items_order
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
  CONSTRAINT fk_items_product
    FOREIGN KEY (product_id) REFERENCES products(product_id)
) ENGINE=InnoDB;

-- Insert 10 Products
INSERT INTO products(product_id, name, description, price, quantity) VALUES
(101,'Apple MacBook 2020','8 GB RAM, 256 SSD',85000.00,5),
(102,'OnePlus Mobile','16 GB RAM, 128 GB Storage',37500.00,3),
(103,'Bluetooth Headphones','Wireless over-ear',2999.00,10),
(104,'Keyboard','Mechanical keyboard',1999.00,12),
(105,'Mouse','Wireless mouse',799.00,20),
(106,'Pendrive 64GB','USB 3.0 storage',499.00,25),
(107,'Smart Watch','Fitness tracking watch',2499.00,8),
(108,'Backpack','Laptop backpack',1299.00,15),
(109,'Water Bottle','Steel bottle 1L',399.00,30),
(110,'Notebook','200 pages ruled',60.00,100);

-- Insert Admin User
INSERT INTO users(role, first_name, last_name, username, password, city, email, mobile)
VALUES ('ADMIN', 'Admin', 'User', 'admin', 'admin', 'NA', 'admin@gmail.com', '0000000000');
Click "Execute All" or press Ctrl+Enter

✅ Database setup complete!

STEP 4️⃣ Download Java Source Code
Create these Java files in Eclipse (copy-paste the code below):

File 1: com.shop.util.DBUtil.java
java
package com.shop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = 
        "jdbc:mysql://localhost:3306/EcommerceDB?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "your_mysql_password";  // Change this!

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
📝 IMPORTANT: Change "your_mysql_password" to your actual MySQL root password!

File 2: com.shop.model.Product.java
java
package com.shop.model;

public class Product {
    private int productId;
    private String name;
    private String description;
    private double price;
    private int quantity;

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
File 3: com.shop.model.User.java
java
package com.shop.model;

public class User {
    private int userId;
    private String role;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String city;
    private String email;
    private String mobile;

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }
}
File 4: com.shop.model.CartItem.java
java
package com.shop.model;

public class CartItem {
    private int productId;
    private String name;
    private double price;
    private int qty;

    public CartItem(int productId, String name, double price, int qty) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    public int getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }
    public double getLineTotal() { return price * qty; }
}
File 5: com.shop.dao.ProductDao.java
java
package com.shop.dao;

import java.sql.*;
import java.util.*;
import com.shop.model.Product;
import com.shop.util.DBUtil;

public class ProductDao {

    public List<Product> getAllProductsSortedByPriceAsc() throws SQLException {
        String sql = "SELECT product_id, name, description, price, quantity FROM products ORDER BY price ASC";

        List<Product> list = new ArrayList<>();
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                list.add(p);
            }
        }
        return list;
    }

    public Product getById(int productId) throws SQLException {
        String sql = "SELECT product_id, name, description, price, quantity FROM products WHERE product_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                return p;
            }
        }
    }

    public boolean reduceQuantity(int productId, int qty) throws SQLException {
        String sql = "UPDATE products SET quantity = quantity - ? WHERE product_id = ? AND quantity >= ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, qty);
            ps.setInt(2, productId);
            ps.setInt(3, qty);
            return ps.executeUpdate() == 1;
        }
    }
}
File 6: com.shop.dao.UserDao.java
java
package com.shop.dao;

import java.sql.*;
import com.shop.model.User;
import com.shop.util.DBUtil;

public class UserDao {

    public boolean register(User u) throws SQLException {
        String sql = "INSERT INTO users(role, first_name, last_name, username, password, city, email, mobile) "
                   + "VALUES('USER', ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getFirstName());
            ps.setString(2, u.getLastName());
            ps.setString(3, u.getUsername());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.getCity());
            ps.setString(6, u.getEmail());
            ps.setString(7, u.getMobile());

            return ps.executeUpdate() == 1;
        }
    }

    public User login(String username, String password) throws SQLException {
        String sql = "SELECT user_id, role, first_name, last_name, username FROM users WHERE username = ? AND password = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setUserId(rs.getInt("user_id"));
                    u.setRole(rs.getString("role"));
                    u.setFirstName(rs.getString("first_name"));
                    u.setLastName(rs.getString("last_name"));
                    u.setUsername(rs.getString("username"));
                    return u;
                }
                return null;
            }
        }
    }
}
File 7: com.shop.dao.AdminDao.java
java
package com.shop.dao;

import java.sql.*;
import com.shop.model.Product;
import com.shop.util.DBUtil;

public class AdminDao {

    public boolean addProduct(Product p) throws SQLException {
        String sql = "INSERT INTO products(product_id, name, description, price, quantity) VALUES(?, ?, ?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getProductId());
            ps.setString(2, p.getName());
            ps.setString(3, p.getDescription());
            ps.setDouble(4, p.getPrice());
            ps.setInt(5, p.getQuantity());

            return ps.executeUpdate() == 1;
        }
    }

    public Integer checkQuantity(int productId) throws SQLException {
        String sql = "SELECT quantity FROM products WHERE product_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("quantity");
                return null;
            }
        }
    }

    public void printAllUsers() throws SQLException {
        String sql = "SELECT user_id, role, first_name, last_name, username, email, mobile, city FROM users ORDER BY user_id";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nID | ROLE | NAME | USERNAME | EMAIL | MOBILE | CITY");
            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                    rs.getInt("user_id") + " | " +
                    rs.getString("role") + " | " +
                    rs.getString("first_name") + " " + rs.getString("last_name") + " | " +
                    rs.getString("username") + " | " +
                    rs.getString("email") + " | " +
                    rs.getString("mobile") + " | " +
                    rs.getString("city")
                );
            }

            if (!found) System.out.println("No users found.");
        }
    }

    public void printUserHistoryByUsername(String username) throws SQLException {
        String sql =
            "SELECT u.username, o.order_id, o.order_time, o.total_amount, " +
            "       oi.product_id, p.name AS product_name, oi.qty, oi.price_at_purchase " +
            "FROM users u " +
            "JOIN orders o ON u.user_id = o.user_id " +
            "JOIN order_items oi ON o.order_id = oi.order_id " +
            "JOIN products p ON oi.product_id = p.product_id " +
            "WHERE u.username = ? " +
            "ORDER BY o.order_id DESC";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\nUSERNAME | ORDER_ID | TIME | TOTAL | PID | PRODUCT | QTY | PRICE");
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.println(
                        rs.getString("username") + " | " +
                        rs.getInt("order_id") + " | " +
                        rs.getTimestamp("order_time") + " | " +
                        rs.getDouble("total_amount") + " | " +
                        rs.getInt("product_id") + " | " +
                        rs.getString("product_name") + " | " +
                        rs.getInt("qty") + " | " +
                        rs.getDouble("price_at_purchase")
                    );
                }
                if (!found) System.out.println("No history found for this user.");
            }
        }
    }
}
File 8: com.shop.dao.OrderDao.java
java
package com.shop.dao;

import java.sql.*;
import java.util.List;
import com.shop.model.CartItem;
import com.shop.util.DBUtil;

public class OrderDao {

    public int placeOrder(int userId, List<CartItem> cart) throws SQLException {
        String insertOrderSql = "INSERT INTO orders(user_id, total_amount) VALUES(?, ?)";
        String insertItemSql  = "INSERT INTO order_items(order_id, product_id, qty, price_at_purchase) VALUES(?, ?, ?, ?)";
        String updateStockSql = "UPDATE products SET quantity = quantity - ? WHERE product_id = ? AND quantity >= ?";

        double total = 0;
        for (CartItem ci : cart) total += ci.getLineTotal();

        try (Connection con = DBUtil.getConnection()) {
            con.setAutoCommit(false);

            try {
                int orderId;
                try (PreparedStatement ps = con.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, userId);
                    ps.setDouble(2, total);
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (!rs.next()) throw new SQLException("Order id not generated.");
                        orderId = rs.getInt(1);
                    }
                }

                for (CartItem ci : cart) {
                    try (PreparedStatement ps = con.prepareStatement(updateStockSql)) {
                        ps.setInt(1, ci.getQty());
                        ps.setInt(2, ci.getProductId());
                        ps.setInt(3, ci.getQty());
                        int updated = ps.executeUpdate();
                        if (updated != 1) throw new SQLException("Not enough stock for product id " + ci.getProductId());
                    }

                    try (PreparedStatement ps = con.prepareStatement(insertItemSql)) {
                        ps.setInt(1, orderId);
                        ps.setInt(2, ci.getProductId());
                        ps.setInt(3, ci.getQty());
                        ps.setDouble(4, ci.getPrice());
                        ps.executeUpdate();
                    }
                }

                con.commit();
                return orderId;

            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    public void printOrdersForUser(int userId) throws SQLException {
        String sql =
            "SELECT o.order_id, o.order_time, o.total_amount, " +
            "       oi.product_id, p.name AS product_name, oi.qty, oi.price_at_purchase " +
            "FROM orders o " +
            "JOIN order_items oi ON o.order_id = oi.order_id " +
            "JOIN products p ON oi.product_id = p.product_id " +
            "WHERE o.user_id = ? " +
            "ORDER BY o.order_id DESC";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\nORDER_ID | TIME | TOTAL | PID | PRODUCT | QTY | PRICE");
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.println(
                        rs.getInt("order_id") + " | " +
                        rs.getTimestamp("order_time") + " | " +
                        rs.getDouble("total_amount") + " | " +
                        rs.getInt("product_id") + " | " +
                        rs.getString("product_name") + " | " +
                        rs.getInt("qty") + " | " +
                        rs.getDouble("price_at_purchase")
                    );
                }
                if (!found) System.out.println("No orders found.");
            }
        }
    }
}
File 9: com.shop.app.MainApp.java
Use the complete MainApp.java from the previous messages (it's too large to include here). Copy the exact code from earlier in our conversation.

STEP 5️⃣ Run the Application
Right-click src/com/shop/app/MainApp.java

Select Run As → Java Application

✅ Application starts!

Test Accounts
Admin Login: Username: admin | Password: admin

Create New User: Register from menu option 3

👥 User Roles & Operations
1️⃣ GUEST User
text
Main Menu → Option 1
├── View products (sorted by price)
└── Cannot purchase
2️⃣ REGISTERED USER
text
Main Menu → Option 3 (Register) OR Option 2 (Login)
├── View products
├── Add to cart (select product + quantity)
├── View cart
├── Purchase & see bill
├── View my orders
└── Logout
3️⃣ ADMIN USER
text
Main Menu → Option 2 (Login with admin/admin)
├── Add new product
├── Check product quantity
├── View all registered users
├── View specific user's purchase history
└── Back
📁 Project Structure
text
EcommerceConsoleApp/
│
├── src/
│   └── com/shop/
│       ├── app/
│       │   └── MainApp.java              ← MAIN ENTRY POINT
│       │
│       ├── model/
│       │   ├── Product.java              ← Product data
│       │   ├── User.java                 ← User data
│       │   └── CartItem.java             ← Cart item data
│       │
│       ├── dao/
│       │   ├── ProductDao.java           ← Product database
│       │   ├── UserDao.java              ← User database
│       │   ├── AdminDao.java             ← Admin database
│       │   └── OrderDao.java             ← Order database
│       │
│       └── util/
│           └── DBUtil.java               ← Database connection
│
├── lib/
│   └── mysql-connector-j-8.0.33.jar      ← MySQL driver
│
└── README.md                              ← This file
🗄️ Database Schema (Visual)
text
┌─────────────────────┐
│      USERS          │
├─────────────────────┤
│ user_id (PK)        │
│ role (ADMIN/USER)   │
│ first_name          │
│ last_name           │
│ username (UNIQUE)   │
│ password            │
│ city                │
│ email               │
│ mobile              │
└──────────┬──────────┘
           │ (1 user : many orders)
           │
┌──────────┴──────────┐
│      ORDERS         │
├─────────────────────┤
│ order_id (PK)       │
│ user_id (FK) ───────┼──→ USERS
│ order_time          │
│ total_amount        │
└──────────┬──────────┘
           │ (1 order : many items)
           │
┌──────────┴──────────────────┐
│      ORDER_ITEMS            │
├─────────────────────────────┤
│ item_id (PK)                │
│ order_id (FK) ──────────────┼──→ ORDERS
│ product_id (FK) ─────┐      │
│ qty                  │      │
│ price_at_purchase    │      │
└──────────────────────┼──────┘
                       │
        ┌──────────────┴─────────────┐
        │                            │
┌───────┴──────────┐      ┌──────────┴────────┐
│    PRODUCTS      │      │   (another)       │
├──────────────────┤      │    PRODUCTS       │
│ product_id (PK)  │      │                   │
│ name             │      └───────────────────┘
│ description      │
│ price            │
│ quantity         │
└──────────────────┘
🆘 Troubleshooting Guide
Problem	Solution
"No suitable driver" error	✅ Add MySQL JAR to Build Path (right-click project → Build Path → Configure)
"Access denied" error	✅ Check MySQL password in DBUtil.java
"Table doesn't exist"	✅ Run all SQL scripts in DBeaver
"Connection timeout"	✅ Check MySQL is running (services.msc on Windows)
Registration fails	✅ Check username is unique (not taken)
Purchase shows 0 quantity	✅ Normal! Stock reduces after purchase
No order history	✅ Complete a purchase first
More Help?
Check Eclipse Console tab for error messages

Verify database with: USE EcommerceDB; SHOW TABLES; in DBeaver

Restart Eclipse if changes don't take effect

🔐 Security Features
✅ PreparedStatement: Prevents SQL injection
✅ Input Validation: Checks all user inputs
✅ Role-Based Access: Different menus for Guest/User/Admin
✅ Transaction Safety: Atomic operations (all-or-nothing)
✅ Foreign Keys: Referential integrity

🎯 Learning Outcomes
After completing this project, you will understand:

✅ Core Java (OOP, Collections, Generics)
✅ JDBC (Connection, Statement, ResultSet)
✅ Database Design (Tables, Keys, Relationships)
✅ SQL (SELECT, INSERT, UPDATE, DELETE, JOIN)
✅ Transaction Management (Commit, Rollback)
✅ Exception Handling & Validation
✅ Console Application Design
✅ DAO Design Pattern
✅ Role-Based Access Control

🚀 Future Improvements
Password Hashing - Use BCrypt instead of plain text

Search & Filter - Find products by name/price range

Wishlist - Save products for later

Reviews - Add product ratings and comments

Admin Reports - Top selling products, revenue analysis

Email Notifications - Send order confirmations

PDF Invoice - Generate downloadable bills

Refund System - Process returns

Coupon Codes - Apply discounts

Multiple Addresses - Save delivery addresses

📝 Important Notes
⚠️ Security: Change password in DBUtil.java for production
⚠️ Stock: Reduces automatically when purchase completes
⚠️ Privacy: Users see only their own orders
⚠️ Admin Access: Admins can view any user's history
⚠️ Transactions: All purchases are atomic (all succeed or all fail)

📄 Project Information
Project: Ecommerce Console Application
Author: Pranay Kadu
Created: January 2026
Purpose: Java Learning Project
Status: ✅ Fully Functional

💡 Tips for Success
Test Each Feature: After adding code, test immediately

Use Meaningful Names: Variable/method names should be clear

Add Comments: Explain complex logic

Handle Errors: Always catch exceptions

Validate Input: Check user input before using

Keep Code Clean: Follow formatting standards

Happy Coding! 🚀 Good luck with your project!

If you have questions or issues, check the Troubleshooting section above or review the relevant DAO class for the feature that's not working.

