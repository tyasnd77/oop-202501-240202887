package com.upb.agripos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.ProductDAOImpl;
import com.upb.agripos.model.Product;

public class MainDAOTest {
    public static void main(String[] args) throws Exception {

        Connection conn = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/agripos",
            "postgres",
            "tyasnd77"
        );
        System.out.println("Database connected!");

        ProductDAO dao = new ProductDAOImpl(conn);

        System.out.println("Inserting product...");
        dao.insert(new Product("P01", "Pupuk Organik", 25000, 10));

        System.out.println("Updating product...");
        dao.update(new Product("P01", "Pupuk Organik Premium", 30000, 8));

        Product p = dao.findByCode("P01");
        System.out.println("Found: " + p.getName() + " | Price: " + p.getPrice());

        System.out.println("All Products:");
        List<Product> products = dao.findAll();
        for (Product pr : products) {
            System.out.println("- " + pr.getName() + " (" + pr.getStock() + ")");
        }

        System.out.println("Deleting product...");
        dao.delete("P01");

        // ✅ IDENTITAS DI AKHIR
        System.out.println();
        System.out.println("credit by: 240202887 - Tyas Nurshika Damaia");

        conn.close();
    }
}
