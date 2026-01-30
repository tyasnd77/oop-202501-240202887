package com.upb.agripos.service;

/**
 * Implementasi PaymentMethod untuk pembayaran tunai.
 */
public class CashPayment implements PaymentMethod {

    @Override
    public boolean processPayment(double jumlahBayar, double totalBelanja) {
        if (!validatePayment(jumlahBayar, totalBelanja)) {
            System.out.println("Cash payment failed: Amount paid is insufficient");
            return false;
        }
        System.out.println("Cash payment processed successfully");
        return true;
    }

    @Override
    public String getPaymentMethodName() {
        // Konsisten dengan pilihan UI dan requirement: TUNAI
        return "TUNAI";
    }

    @Override
    public double calculateChange(double jumlahBayar, double totalBelanja) {
        return jumlahBayar - totalBelanja;
    }

    @Override
    public boolean validatePayment(double jumlahBayar, double totalBelanja) {
        return jumlahBayar >= totalBelanja;
    }
}
