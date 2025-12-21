package com.upb.agripos;

public class Product {

    private String kode;
    private String nama;
    private double harga;

    public Product(String kode, String nama, double harga) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
    }

    public String getKode() {
        return kode;
    }

    public String getName() {
        return nama;
    }

    public double getPrice() {
        return harga;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product p = (Product) o;
        return kode.equals(p.kode);
    }

    @Override
    public int hashCode() {
        return kode.hashCode();
    }
}
