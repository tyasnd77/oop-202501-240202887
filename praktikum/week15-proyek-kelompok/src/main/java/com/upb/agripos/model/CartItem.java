package com.upb.agripos.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Model untuk item dalam keranjang belanja.
 * Menyimpan referensi produk dan quantity yang dipilih.
 */
public class CartItem {
    private Product product;
    private IntegerProperty quantity;

    // Constructor
    public CartItem(Product product, int qty) {
        this.product = product;
        this.quantity = new SimpleIntegerProperty(qty);
    }

    // Getter dan Setter
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int qty) {
        this.quantity.set(qty);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    /**
     * Menghitung subtotal item (harga * quantity).
     */
    public double getSubtotal() {
        return product.getHarga() * getQuantity();
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product.getNama() +
                ", quantity=" + getQuantity() +
                ", subtotal=" + getSubtotal() +
                '}';
    }
}
