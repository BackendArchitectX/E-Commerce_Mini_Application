# 🛍️ Ecommerce Console App

A multi-user Java-based eCommerce application with role-based access (Guest, Registered User, Admin), product management, shopping cart, and order history functionality.

---

## 👥 User Roles & Operations

### 1️⃣ GUEST User
**Access:** Main Menu → Option 1

**Capabilities:**
- ✅ View products (sorted by price)
- ❌ Cannot purchase

---

### 2️⃣ REGISTERED USER
**Access:** Main Menu → Option 3 (Register) OR Option 2 (Login)

**Capabilities:**
- ✅ View products
- ✅ Add to cart (select product + quantity)
- ✅ View cart contents
- ✅ Purchase & view bill
- ✅ View order history
- ✅ Logout

---

### 3️⃣ ADMIN USER
**Access:** Main Menu → Option 2 (Login with `admin`/`admin`)

**Capabilities:**
- ✅ Add new product
- ✅ Check product quantity
- ✅ View all registered users
- ✅ View specific user's purchase history
- ✅ Back to menu

---

## 📁 Project Structure

```
EcommerceConsoleApp/
│
├── src/
│   └── com/shop/
│       ├── app/
│       │   └── MainApp.java                    ← MAIN ENTRY POINT
│       │
│       ├── model/
│       │   ├── Product.java                    ← Product data model
│       │   ├── User.java                       ← User data model
│       │   └── CartItem.java                   ← Cart item data model
│       │
│       ├── dao/
│       │   ├── ProductDao.java                 ← Product database operations
│       │   ├── UserDao.java                    ← User database operations
│       │   ├── AdminDao.java                   ← Admin database operations
│       │   └── OrderDao.java                   ← Order database operations
│       │
│       └── util/
│           └── DBUtil.java                     ← Database connection utility
│
├── lib/
│   └── mysql-connector-j-8.0.33.jar            ← MySQL JDBC driver
│
└── README.md                                    ← Documentation (this file)
```

---

## 🗄️ Database Schema

### Relationships Diagram

```
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
│ user_id (FK)────────┼──→ USERS
│ order_time          │
│ total_amount        │
└──────────┬──────────┘
           │ (1 order : many items)
           │
┌──────────┴────────────────────┐
│      ORDER_ITEMS              │
├───────────────────────────────┤
│ item_id (PK)                  │
│ order_id (FK)─────────────────┼──→ ORDERS
│ product_id (FK)───┐           │
│ qty               │           │
│ price_at_purchase │           │
└───────────────────┼───────────┘
                    │
        ┌───────────┴────────────┐
        │                        │
    ┌───┴──────────┐    ┌────────┴─────┐
    │  PRODUCTS    │    │  PRODUCTS    │
    ├──────────────┤    ├──────────────┤
    │ product_id   │    │ product_id   │
    │ (PK)         │    │ (PK)         │
    │ name         │    │ name         │
    │ description  │    │ description  │
    │ price        │    │ price        │
    │ quantity     │    │ quantity     │
    └──────────────┘    └──────────────┘
```

### Table Details

**USERS**
```sql
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(10) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    city VARCHAR(50),
    email VARCHAR(100),
    mobile VARCHAR(20)
);
```

**PRODUCTS**
```sql
CREATE TABLE products (
    product_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL
);
```

**ORDERS**
```sql
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    order_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
```

**ORDER_ITEMS**
```sql
CREATE TABLE order_items (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    qty INT NOT NULL,
    price_at_purchase DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
```

---

## ⚙️ Setup Instructions

### Prerequisites
- **Java 8+** (JDK installed)
- **MySQL 5.7+** (running locally or remote)
- **Eclipse IDE** (or any Java IDE)
- **MySQL Connector/J 8.0.33** JAR file

### Step 1: Database Setup
1. Open **MySQL Workbench** or **DBeaver**
2. Create database:
   ```sql
   CREATE DATABASE ecommerce_db;
   USE ecommerce_db;
   ```
3. Run the SQL scripts above to create all tables

### Step 2: Configure DBUtil.java
Update connection details in `src/com/shop/util/DBUtil.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/ecommerce_db?useSSL=false&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASS = "your_password";
```

### Step 3: Add MySQL JAR to Build Path
1. Right-click project → **Build Path** → **Configure Build Path**
2. **Libraries** tab → **Add External JAR**
3. Select `mysql-connector-j-8.0.33.jar` from `lib/` folder
4. Click **Apply and Close**

### Step 4: Run Application
Right-click `MainApp.java` → **Run As** → **Java Application**

---

## 🆘 Troubleshooting Guide

| Problem | Solution |
|---------|----------|
| **"No suitable driver" error** | ✅ Add MySQL JAR to Build Path (right-click project → Build Path → Configure Build Path) |
| **"Access denied" error** | ✅ Check MySQL username/password in `DBUtil.java` |
| **"Table doesn't exist" error** | ✅ Run all SQL create scripts in DBeaver or MySQL Workbench |
| **"Connection timeout" error** | ✅ Verify MySQL service is running (`services.msc` on Windows, `brew services` on Mac) |
| **Registration fails** | ✅ Check username is unique (not already registered) |
| **Purchase shows 0 quantity** | ✅ Normal! Stock reduces automatically after successful purchase |
| **No order history visible** | ✅ Complete a purchase first before viewing orders |
| **Null Pointer Exception** | ✅ Check Eclipse Console tab for full stack trace and error location |

---

## 🚀 Key Features

- **Role-Based Access Control** – Different features for Guest, User, and Admin
- **Product Catalog** – Browse and filter products by price
- **Shopping Cart** – Add/remove items, manage quantities
- **Order Management** – Place orders, generate bills, view history
- **Admin Dashboard** – Manage inventory, users, and sales reports
- **Secure Login** – Username/password authentication
- **Database Persistence** – All data stored in MySQL

---

## 📝 Sample Data (Optional)

Insert test products:
```sql
INSERT INTO products(product_id, name, description, price, quantity) 
VALUES
(101, 'Apple MacBook 2020', '8 GB RAM, 256 GB SSD', 85000.00, 5),
(102, 'OnePlus Mobile', '16 GB RAM, 128 GB Storage', 37500.00, 3),
(103, 'Samsung Galaxy S23', '8 GB RAM, 256 GB Storage', 74999.00, 4),
(104, 'Dell Inspiron 15', '16 GB RAM, 512 GB SSD', 62000.00, 6),
(105, 'Sony WH-1000XM5', 'Noise Cancelling Headphones', 29990.00, 10),
(106, 'Apple iPad 10th Gen', '64 GB WiFi, 10.9-inch', 39900.00, 7);
```

Insert test admin user:
```sql
INSERT INTO users(user_id, role, first_name, last_name, username, password, city, email, mobile)
VALUES
(1, 'ADMIN', 'Admin', 'User', 'admin', 'admin', 'New Delhi', 'admin@shop.com', '9999999999');
```

---

## 🔗 Technologies Used

| Technology | Version | Purpose |
|-----------|---------|---------|
| **Java** | 8+ | Core application language |
| **MySQL** | 5.7+ | Database management |
| **JDBC** | 4.2+ | Database connectivity |
| **Eclipse IDE** | Latest | Development environment |
| **MySQL Connector/J** | 8.0.33 | MySQL driver |

---

## 📄 License

This project is licensed under the **MIT License** – feel free to use, modify, and distribute.

---

## ✉️ Support & Contributions

- 📧 For issues, check the **Troubleshooting Guide** above
- 🐛 Found a bug? Open an **Issue** on GitHub
- 🤝 Want to contribute? Submit a **Pull Request**

---

**Happy Shopping! 🛒**
