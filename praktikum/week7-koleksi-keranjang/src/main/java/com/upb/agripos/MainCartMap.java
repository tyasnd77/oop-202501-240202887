package com.upb.agripos;

public class MainCartMap {

    public static void main(String[] args) {

        ShoppingCartMap cart = new ShoppingCartMap();

        Product p1 = new Product("P01","Beras", 50000);
        Product p2 = new Product("P02","Pupuk", 30000);
        Product p3 = new Product("P03","Obat Hama", 18000);

        // Tambah produk ke keranjang
        cart.addProduct(p1);
        cart.addProduct(p1);
        cart.addProduct(p2);
        cart.addProduct(p3);
        cart.addProduct(p3);
        cart.addProduct(p3);

       cart.printCart();
        System.out.println("\nTotal Belanja: Rp " + cart.getTotal());
    }
}
