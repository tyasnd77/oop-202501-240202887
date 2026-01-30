package com.upb.agripos.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Model untuk Transaksi Penjualan.
 */
public class Transaction {
    private int id;
    private LocalDateTime tanggal;
    private Cart cart;
    private String metodePayment; // "TUNAI" atau "EWALLET"
    private double totalHarga;
    private double jumlahBayar;
    private double kembalian;
    private String status; // "PENDING", "SUCCESS", "FAILED"
    private int userId; // ID kasir yang melakukan transaksi

    // Constructor
    public Transaction() {
        this.tanggal = LocalDateTime.now();
        this.cart = new Cart();
        this.status = "PENDING";
    }

    public Transaction(int id, LocalDateTime tanggal, Cart cart, String metodePayment, 
                      double totalHarga, double jumlahBayar, double kembalian, 
                      String status, int userId) {
        this.id = id;
        this.tanggal = tanggal;
        this.cart = cart;
        this.metodePayment = metodePayment;
        this.totalHarga = totalHarga;
        this.jumlahBayar = jumlahBayar;
        this.kembalian = kembalian;
        this.status = status;
        this.userId = userId;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDateTime tanggal) {
        this.tanggal = tanggal;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getMetodePayment() {
        return metodePayment;
    }

    public void setMetodePayment(String metodePayment) {
        this.metodePayment = metodePayment;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }

    public double getJumlahBayar() {
        return jumlahBayar;
    }

    public void setJumlahBayar(double jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }

    public double getKembalian() {
        return kembalian;
    }

    public void setKembalian(double kembalian) {
        this.kembalian = kembalian;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Menghitung kembalian pembayaran.
     */
    public void hitungKembalian() {
        this.kembalian = this.jumlahBayar - this.totalHarga;
    }

    /**
     * Format tanggal untuk display.
     */
    public String getTanggalFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return tanggal.format(formatter);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", tanggal=" + tanggal +
                ", metodePayment='" + metodePayment + '\'' +
                ", totalHarga=" + totalHarga +
                ", status='" + status + '\'' +
                '}';
    }
}
