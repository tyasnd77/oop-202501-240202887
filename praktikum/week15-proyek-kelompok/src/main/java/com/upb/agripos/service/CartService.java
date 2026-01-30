package com.upb.agripos.service;

import com.upb.agripos.model.Cart;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;
import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.exception.OutOfStockException;
import com.upb.agripos.exception.DatabaseException;

/**
 * Service untuk mengelola keranjang belanja (business logic).
 * Mengimplementasikan FR-2: Transaksi Penjualan.
 */
public class CartService {
    private Cart cart;
    private ProductService productService;

    public CartService(ProductService productService) {
        this.cart = new Cart();
        this.productService = productService;
    }

    /**
     * Menambah produk ke keranjang dengan validasi stok.
     */
    public void addToCart(Product product, int quantity) 
            throws ValidationException, OutOfStockException, DatabaseException {
        if (product == null) {
            throw new ValidationException("Produk tidak boleh null");
        }
        if (quantity <= 0) {
            throw new ValidationException("Quantity harus lebih dari 0");
        }

        // Validasi stok
        if (!productService.isStockAvailable(product.getId(), quantity)) {
            throw new OutOfStockException("Stok " + product.getNama() + " tidak cukup. Dibutuhkan: " + quantity);
        }

        cart.addItem(product, quantity);
    }

    /**
     * Menghapus item dari keranjang.
     */
    public void removeFromCart(int productId) {
        cart.removeItem(productId);
    }

    /**
     * Mengupdate quantity item di keranjang.
     */
    public void updateCartItemQuantity(int productId, int newQuantity) 
            throws ValidationException, OutOfStockException, DatabaseException {
        if (newQuantity < 0) {
            throw new ValidationException("Quantity tidak boleh negatif");
        }

        if (newQuantity > 0) {
            // Validasi stok jika quantity meningkat
            CartItem item = cart.getItems().stream()
                    .filter(i -> i.getProduct().getId() == productId)
                    .findFirst()
                    .orElse(null);

            if (item != null) {
                int currentQty = item.getQuantity();
                int increase = newQuantity - currentQty;
                if (increase > 0 && !productService.isStockAvailable(productId, increase)) {
                    throw new OutOfStockException("Stok tidak cukup untuk penambahan quantity");
                }
            }
        }

        cart.updateItemQuantity(productId, newQuantity);
    }

    /**
     * Mendapatkan keranjang saat ini.
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * Mendapatkan total harga keranjang.
     */
    public double getCartTotal() {
        return cart.getTotal();
    }

    /**
     * Membersihkan keranjang.
     */
    public void clearCart() {
        cart.clear();
    }

    /**
     * Membuat keranjang baru.
     */
    public void resetCart() {
        this.cart = new Cart();
    }
}
