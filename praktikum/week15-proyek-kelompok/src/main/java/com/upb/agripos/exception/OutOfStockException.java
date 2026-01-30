package com.upb.agripos.exception;

/**
 * Custom exception untuk ketika stok produk tidak cukup.
 */
public class OutOfStockException extends Exception {
    public OutOfStockException(String message) {
        super(message);
    }

    public OutOfStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
