package com.upb.agripos.controller;

import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.exception.OutOfStockException;
import com.upb.agripos.exception.DatabaseException;
import com.upb.agripos.model.Product;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.service.CartService;
import java.util.List;

/**
 * Controller utility untuk layer Kasir.
 * Helper class untuk menangani business logic transaksi.
 * Main UI logic sudah ditangani di AppJavaFX.
 */
public class KasirController {
    private ProductService productService;
    private CartService cartService;

    /**
     * Constructor dengan dependency injection
     * Gunakan ini untuk memastikan menggunakan service instance yang sama
     */
    public KasirController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    /**
     * Constructor default (deprecated)
     * Hanya untuk backward compatibility
     */
    @Deprecated
    public KasirController() throws DatabaseException {
        this.productService = new ProductService();
        this.cartService = new CartService(productService);
    }

    /**
     * Get all products dari database
     */
    public List<Product> getAllProducts() throws DatabaseException {
        return productService.getAllProducts();
    }

    /**
     * Search products by keyword
     */
    public List<Product> searchProducts(String keyword) throws DatabaseException {
        return productService.searchProducts(keyword);
    }

    /**
     * Add product ke cart dengan validasi
     */
    public void addToCart(Product product, int quantity) 
            throws ValidationException, OutOfStockException, DatabaseException {
        if (product == null) {
            throw new ValidationException("Produk tidak boleh null");
        }
        if (quantity <= 0) {
            throw new ValidationException("Quantity harus lebih dari 0");
        }
        cartService.addToCart(product, quantity);
    }

    /**
     * Remove product dari cart
     */
    public void removeFromCart(int productId) {
        cartService.removeFromCart(productId);
    }

    /**
     * Update quantity item di cart
     */
    public void updateCartItemQuantity(int productId, int newQuantity) 
            throws ValidationException, OutOfStockException, DatabaseException {
        cartService.updateCartItemQuantity(productId, newQuantity);
    }

    /**
     * Get total harga dari cart
     */
    public double getCartTotal() {
        return cartService.getCartTotal();
    }

    /**
     * Get all items dari cart
     */
    public List<CartItem> getCartItems() {
        return cartService.getCart().getItems();
    }

    /**
     * Clear cart
     */
    public void clearCart() {
        cartService.clearCart();
    }

    /**
     * Check stok availability
     */
    public boolean isStockAvailable(int productId, int quantity) throws DatabaseException {
        return productService.isStockAvailable(productId, quantity);
    }

    /**
     * Get product by ID
     */
    public Product getProductById(int productId) throws DatabaseException {
        return productService.getProductById(productId);
    }
}
