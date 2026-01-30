package com.upb.agripos.service;

/**
 * Strategy interface untuk metode pembayaran.
 * Mengimplementasikan Strategy Pattern untuk mendukung berbagai metode pembayaran.
 */
public interface PaymentMethod {
    /**
     * Memproses pembayaran.
     * @param jumlahBayar Jumlah uang yang dibayarkan
     * @param totalBelanja Total belanja yang harus dibayar
     * @return true jika pembayaran berhasil, false jika gagal
     */
    boolean processPayment(double jumlahBayar, double totalBelanja);

    /**
     * Mendapatkan nama metode pembayaran.
     */
    String getPaymentMethodName();

    /**
     * Menghitung kembalian.
     */
    double calculateChange(double jumlahBayar, double totalBelanja);

    /**
     * Validasi pembayaran.
     */
    boolean validatePayment(double jumlahBayar, double totalBelanja);
}
