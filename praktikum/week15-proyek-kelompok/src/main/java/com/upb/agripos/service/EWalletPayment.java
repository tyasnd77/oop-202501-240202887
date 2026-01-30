package com.upb.agripos.service;

/**
 * Implementasi PaymentMethod untuk pembayaran E-Wallet.
 */
public class EWalletPayment implements PaymentMethod {

    @Override
    public boolean processPayment(double jumlahBayar, double totalBelanja) {
        if (!validatePayment(jumlahBayar, totalBelanja)) {
            System.out.println("E-Wallet payment failed: Balance is insufficient or transaction failed");
            return false;
        }
        // Simulasi proses E-Wallet
        System.out.println("E-Wallet payment processed successfully");
        return true;
    }

    @Override
    public String getPaymentMethodName() {
        // Konsisten dengan pilihan UI dan requirement: EWALLET
        return "EWALLET";
    }

    @Override
    public double calculateChange(double jumlahBayar, double totalBelanja) {
        // E-Wallet tidak ada kembalian, hanya debit
        return 0;
    }

    @Override
    public boolean validatePayment(double jumlahBayar, double totalBelanja) {
        // Simulasi validasi E-Wallet
        return jumlahBayar >= totalBelanja;
    }
}
