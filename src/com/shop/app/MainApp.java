package com.shop.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.shop.dao.AdminDao;
import com.shop.dao.OrderDao;
import com.shop.dao.ProductDao;
import com.shop.dao.UserDao;
import com.shop.model.CartItem;
import com.shop.model.Product;
import com.shop.model.User;

/**
 * Main application class for E-Commerce Console Application
 */
public class MainApp {
    
    private static Scanner scanner = new Scanner(System.in);
    private static ProductDao productDao = new ProductDao();
    private static UserDao userDao = new UserDao();
    private static AdminDao adminDao = new AdminDao();
    
    public static void main(String[] args) {
        try {
            mainMenu();
        } finally {
            scanner.close();
        }
    }
    
    /**
     * Main menu for user selection
     */
    private static void mainMenu() {
        while (true) {
            System.out.println("\n=== ECOMMERCE CONSOLE APP ===");
            System.out.println("1. Guest");
            System.out.println("2. Login");
            System.out.println("3. Register");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            
            int choice = readInt();
            
            switch (choice) {
                case 1:
                    guestMenu();
                    break;
                case 2:
                    loginAndRoute();
                    break;
                case 3:
                    doRegister();
                    break;
                case 0:
                    System.out.println("Thank you for using our application!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    /**
     * Guest menu - view products only
     */
    private static void guestMenu() {
        while (true) {
            System.out.println("\n--- GUEST MENU ---");
            System.out.println("1. View products (sort by price ASC)");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            
            int choice = readInt();
            
            if (choice == 0) {
                return;
            }
            
            switch (choice) {
                case 1:
                    try {
                        List<Product> products = productDao.getAllProductsSortedByPriceAsc();
                        displayProducts(products);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    /**
     * User login and route to appropriate menu
     */
    private static void loginAndRoute() {
        scanner.nextLine(); // consume newline
        
        try {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            
            User user = userDao.login(username, password);
            
            if (user == null) {
                System.out.println("Invalid username or password.");
                return;
            }
            
            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                System.out.println("Welcome Admin: " + user.getFirstName());
                adminMenu();
            } else {
                System.out.println("Welcome User: " + user.getFirstName());
                userAfterLoginMenu(user);
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }
    }
    
    /**
     * User menu after successful login
     */
    private static void userAfterLoginMenu(User user) {
        List<CartItem> cart = new ArrayList<>();
        OrderDao orderDao = new OrderDao();
        
        while (true) {
            System.out.println("\n--- USER MENU (" + user.getUsername() + ") ---");
            System.out.println("1. View products");
            System.out.println("2. Add to cart");
            System.out.println("3. View cart");
            System.out.println("4. Purchase (Checkout)");
            System.out.println("5. View my orders");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            
            int choice = readInt();
            
            if (choice == 0) {
                System.out.println("Logged out successfully.");
                return;
            }
            
            switch (choice) {
                case 1:
                    try {
                        List<Product> products = productDao.getAllProductsSortedByPriceAsc();
                        displayProducts(products);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    addToCart(cart);
                    break;
                case 3:
                    viewCart(cart);
                    break;
                case 4:
                    purchaseCart(user, cart, orderDao);
                    break;
                case 5:
                    try {
                        orderDao.printOrdersForUser(user.getUserId());
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    /**
     * Admin menu for product and user management
     */
    private static void adminMenu() {
        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. Add product");
            System.out.println("2. Check quantity by product id");
            System.out.println("3. View user history by username");
            System.out.println("4. View registered users");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            
            int choice = readInt();
            
            if (choice == 0) {
                return;
            }
            
            switch (choice) {
                case 1:
                    doAddProduct();
                    break;
                case 2:
                    doCheckQuantity();
                    break;
                case 3:
                    doViewUserHistory();
                    break;
                case 4:
                    try {
                        adminDao.printAllUsers();
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    /**
     * Register a new user
     */
    private static void doRegister() {
        scanner.nextLine(); // consume newline
        
        try {
            User user = new User();
            
            System.out.print("First name: ");
            user.setFirstName(scanner.nextLine());
            System.out.print("Last name: ");
            user.setLastName(scanner.nextLine());
            System.out.print("Username: ");
            user.setUsername(scanner.nextLine());
            System.out.print("Password: ");
            user.setPassword(scanner.nextLine());
            System.out.print("City: ");
            user.setCity(scanner.nextLine());
            System.out.print("Email: ");
            user.setEmail(scanner.nextLine());
            System.out.print("Mobile: ");
            user.setMobile(scanner.nextLine());
            
            boolean registered = userDao.register(user);
            
            if (registered) {
                System.out.println("Registered successfully!");
            } else {
                System.out.println("Registration failed.");
            }
        } catch (Exception e) {
            System.out.println("Registration error: " + e.getMessage());
        }
    }
    
    /**
     * Add a new product (admin function)
     */
    private static void doAddProduct() {
        scanner.nextLine(); // consume newline
        
        try {
            Product product = new Product();
            
            System.out.print("Product Id (number): ");
            product.setProductId(readInt());
            scanner.nextLine(); // consume newline
            
            System.out.print("Name: ");
            product.setName(scanner.nextLine());
            System.out.print("Description: ");
            product.setDescription(scanner.nextLine());
            
            System.out.print("Price: ");
            while (!scanner.hasNextDouble()) {
                System.out.print("Enter valid price: ");
                scanner.next();
            }
            product.setPrice(scanner.nextDouble());
            
            System.out.print("Quantity: ");
            product.setQuantity(readInt());
            
            boolean added = adminDao.addProduct(product);
            
            if (added) {
                System.out.println("Product added successfully!");
            } else {
                System.out.println("Failed to add product.");
            }
        } catch (Exception e) {
            System.out.println("Add product error: " + e.getMessage());
        }
    }
    
    /**
     * Check product quantity (admin function)
     */
    private static void doCheckQuantity() {
        try {
            System.out.print("Enter product id: ");
            int productId = readInt();
            
            Integer quantity = adminDao.checkQuantity(productId);
            
            if (quantity == null) {
                System.out.println("Product not found.");
            } else {
                System.out.println("Available quantity: " + quantity);
            }
        } catch (Exception e) {
            System.out.println("Check quantity error: " + e.getMessage());
        }
    }
    
    /**
     * View user purchase history (admin function)
     */
    private static void doViewUserHistory() {
        scanner.nextLine(); // consume newline
        
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            adminDao.printUserHistoryByUsername(username);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Add item to shopping cart
     */
    private static void addToCart(List<CartItem> cart) {
        try {
            System.out.print("Enter product id: ");
            int productId = readInt();
            System.out.print("Enter quantity: ");
            int quantity = readInt();
            
            if (quantity <= 0) {
                System.out.println("Quantity must be greater than 0.");
                return;
            }
            
            Product product = productDao.getById(productId);
            
            if (product == null) {
                System.out.println("Product not found.");
                return;
            }
            
            if (quantity > product.getQuantity()) {
                System.out.println("Not enough stock. Available: " + product.getQuantity());
                return;
            }
            
            // Check if product already in cart
            for (CartItem cartItem : cart) {
                if (cartItem.getProductId() == productId) {
                    cartItem.setQty(cartItem.getQty() + quantity);
                    System.out.println("Updated quantity in cart.");
                    return;
                }
            }
            
            // Add new item to cart
            cart.add(new CartItem(productId, product.getName(), product.getPrice(), quantity));
            System.out.println("Added to cart successfully.");
        } catch (Exception e) {
            System.out.println("Add to cart error: " + e.getMessage());
        }
    }
    
    /**
     * Display current shopping cart
     */
    private static void viewCart(List<CartItem> cart) {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        
        double totalAmount = 0;
        System.out.printf("\n%-6s %-22s %-10s %-6s %-10s%n", "ID", "NAME", "PRICE", "QTY", "TOTAL");
        System.out.printf("%-6s %-22s %-10s %-6s %-10s%n", "------", "----------------------", 
                          "----------", "------", "----------");
        
        for (CartItem cartItem : cart) {
            double lineTotal = cartItem.getLineTotal();
            totalAmount += lineTotal;
            
            System.out.printf("%-6d %-22s %-10.2f %-6d %-10.2f%n",
                cartItem.getProductId(),
                cartItem.getName(),
                cartItem.getPrice(),
                cartItem.getQty(),
                lineTotal);
        }
        
        System.out.printf("%-6s %-22s %-10s %-6s %-10.2f%n", "", "", "", "TOTAL:", totalAmount);
    }
    
    /**
     * Process cart checkout and place order
     */
    private static void purchaseCart(User user, List<CartItem> cart, OrderDao orderDao) {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty. Cannot place order.");
            return;
        }
        
        viewCart(cart);
        
        System.out.print("Confirm purchase? (yes/no): ");
        scanner.nextLine(); // consume newline
        String confirm = scanner.nextLine();
        
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Order cancelled.");
            return;
        }
        
        try {
            int orderId = orderDao.placeOrder(user.getUserId(), cart);
            System.out.println("Order placed successfully! Order ID: " + orderId);
            
            // Clear cart after successful order
            cart.clear();
        } catch (Exception e) {
            System.out.println("Purchase error: " + e.getMessage());
        }
    }
    
    /**
     * Display products in tabular format
     */
    private static void displayProducts(List<Product> products) {
        System.out.printf("\n%-6s %-22s %-10s %-6s%n", "ID", "NAME", "PRICE", "QTY");
        System.out.printf("%-6s %-22s %-10s %-6s%n", "------", "----------------------", 
                          "----------", "------");
        
        for (Product product : products) {
            System.out.printf("%-6d %-22s %-10.2f %-6d%n",
                product.getProductId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity());
        }
    }
    
    /**
     * Read integer input with validation
     */
    private static int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
