package com.upb.agripos.model;

public class Pestisida extends Produk {
    private String jenisHama; 

    public Pestisida(String kode, String nama, double harga, int stok, String jenisHama) {
        super(kode, nama, harga, stok);
        this.jenisHama = jenisHama;
    }

    @Override
    public String getInfo() {
        return "Pestisida: " + super.getInfo() + ", Hama Sasaran: " + jenisHama;
    }
}