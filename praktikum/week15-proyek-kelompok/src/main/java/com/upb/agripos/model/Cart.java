package com.upb.agripos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model untuk keranjang belanja.
 * Menyimpan daftar CartItem dan menyediakan operasi untuk menambah, menghapus, dan menghitung total.
 */
public class Cart {
    private List<CartItem> items;

    // Constructor
    public Cart() {
        this.items = new ArrayList<>();
    }

    /**
     * Menambah produk ke keranjang atau update quantity jika sudah ada.
     */
    public void addItem(Product product, int quantity) {
        // Cek apakah produk sudah ada di keranjang
        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        // Jika belum ada, tambah item baru
        items.add(new CartItem(product, quantity));
    }

    /**
     * Menghapus item dari keranjang berdasarkan product id.
     */
    public void removeItem(int productId) {
        items.removeIf(item -> item.getProduct().getId() == productId);
    }

    /**
     * Mengupdate quantity item di keranjang.
     */
    public void updateItemQuantity(int productId, int newQuantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == productId) {
                if (newQuantity > 0) {
                    item.setQuantity(newQuantity);
                } else {
                    removeItem(productId);
                }
                return;
            }
        }
    }

    /**
     * Mendapatkan total harga keranjang.
     */
    public double getTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    /**
     * Mengosongkan keranjang.
     */
    public void clear() {
        items.clear();
    }

    /**
     * Mendapatkan daftar item dalam keranjang.
     */
    public List<CartItem> getItems() {
        return items;
    }

    /**
     * Mendapatkan jumlah item unik dalam keranjang.
     */
    public int getItemCount() {
        return items.size();
    }

    /**
     * Mendapatkan total quantity dari semua item.
     */
    public int getTotalQuantity() {
        int total = 0;
        for (CartItem item : items) {
            total += item.getQuantity();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items.size() +
                ", total=" + getTotal() +
                '}';
    }
}
