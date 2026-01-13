package com.shop.model;

/**
 * CartItem model class representing an item in the shopping cart
 */
public class CartItem {
    
    private int productId;
    private String name;
    private double price;
    private int qty;
    
    /**
     * Constructor for CartItem
     * @param productId unique product identifier
     * @param name product name
     * @param price unit price of the product
     * @param qty quantity in cart
     */
    public CartItem(int productId, String name, double price, int qty) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.qty = qty;
    }
    
    // Getters and Setters
    
    public int getProductId() {
        return productId;
    }
    
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public int getQty() {
        return qty;
    }
    
    public void setQty(int qty) {
        this.qty = qty;
    }
    
    /**
     * Calculate line total for this item
     * @return price * qty
     */
    public double getLineTotal() {
        return price * qty;
    }
}
