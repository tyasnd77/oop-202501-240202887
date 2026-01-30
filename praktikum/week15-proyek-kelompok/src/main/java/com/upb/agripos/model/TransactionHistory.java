package com.upb.agripos.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Model untuk menampilkan riwayat transaksi dalam table view.
 * Simplified version dari Transaction untuk display purposes.
 */
public class TransactionHistory {
    private int id;
    private LocalDateTime tanggal;
    private String namaKasir;
    private double totalHarga;
    private String metodePayment;
    private double jumlahBayar;
    private double kembalian;
    private String status;

    // Constructor
    public TransactionHistory(int id, LocalDateTime tanggal, String namaKasir, 
                              double totalHarga, String metodePayment, 
                              double jumlahBayar, double kembalian, String status) {
        this.id = id;
        this.tanggal = tanggal;
        this.namaKasir = namaKasir;
        this.totalHarga = totalHarga;
        this.metodePayment = metodePayment;
        this.jumlahBayar = jumlahBayar;
        this.kembalian = kembalian;
        this.status = status;
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

    public String getNamaKasir() {
        return namaKasir;
    }

    public void setNamaKasir(String namaKasir) {
        this.namaKasir = namaKasir;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getMetodePayment() {
        return metodePayment;
    }

    public void setMetodePayment(String metodePayment) {
        this.metodePayment = metodePayment;
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

    /**
     * Format tanggal untuk display.
     */
    public String getTanggalFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return tanggal.format(formatter);
    }

    /**
     * Format harga untuk display.
     */
    public String getTotalHargaFormatted() {
        return String.format("Rp %.0f", totalHarga);
    }

    public String getJumlahBayarFormatted() {
        return String.format("Rp %.0f", jumlahBayar);
    }

    public String getKembalianFormatted() {
        return String.format("Rp %.0f", kembalian);
    }

    @Override
    public String toString() {
        return "TransactionHistory{" +
                "id=" + id +
                ", tanggal=" + tanggal +
                ", namaKasir='" + namaKasir + '\'' +
                ", totalHarga=" + totalHarga +
                ", metodePayment='" + metodePayment + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
