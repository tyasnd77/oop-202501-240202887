package com.upb.agripos;

import com.upb.agripos.model.*;
import com.upb.agripos.util.CreditBy;

public class MainPolymorphism {
    public static void main(String[] args) {

        System.out.println("=== DEMO OVERLOADING ===");
        Produk produkUmum = new Produk("PRD-000", "Produk Umum", 50000, 10);
        System.out.println("Stok awal: 10");
        produkUmum.tambahStok(5);       // versi int
        produkUmum.tambahStok(3.5);     // versi double
        System.out.println(produkUmum.getInfo());
        System.out.println();

        System.out.println("=== DEMO OVERRIDING & DYNAMIC BINDING ===");
        Produk[] daftarProduk = {
            new Benih("BNH-001", "Benih Padi IR64", 25000, 100, "IR64"),
            new Pupuk("PPK-101", "Pupuk Urea", 350000, 40, "Urea"),
            new AlatPertanian("ALT-501", "Cangkul Baja", 90000, 15, "Baja"),
            new Pestisida("PST-505", "Pestisida Tanaman Cabai", 95000, 30,"Thrips dan Kutu Daun")
        };

        for (Produk p : daftarProduk) {
            System.out.println(p.getInfo()); // Dynamic Binding
        }

        System.out.println();
        CreditBy.print("240202887", "Tyas Nurshika Damaia");
    }
}
