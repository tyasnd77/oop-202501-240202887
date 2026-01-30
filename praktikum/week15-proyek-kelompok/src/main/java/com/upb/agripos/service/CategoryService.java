package com.upb.agripos.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.upb.agripos.dao.DatabaseConnection;
import com.upb.agripos.exception.DatabaseException;

/**
 * Service untuk mengelola kategori produk.
 */
public class CategoryService {

    /**
     * Mendapatkan semua kategori yang tersedia.
     */
    public List<String> getAllCategories() throws DatabaseException {
        List<String> categories = new ArrayList<>();
        
        String sql = "SELECT DISTINCT kategori FROM products ORDER BY kategori";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                categories.add(rs.getString("kategori"));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error fetching categories: " + e.getMessage(), e);
        }

        return categories;
    }

    /**
     * Menambah kategori baru.
     */
    public void addCategory(String kategori) throws DatabaseException {
        if (kategori == null || kategori.trim().isEmpty()) {
            throw new DatabaseException("Kategori tidak boleh kosong");
        }

        // Check if category already exists
        List<String> categories = getAllCategories();
        if (categories.contains(kategori)) {
            throw new DatabaseException("Kategori '" + kategori + "' sudah ada");
        }

        // Insert a placeholder product to establish the category
        String sql = "INSERT INTO products (kode, nama, kategori, harga, stok) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "CAT_" + System.currentTimeMillis());
            stmt.setString(2, "[Template] " + kategori);
            stmt.setString(3, kategori);
            stmt.setDouble(4, 0);
            stmt.setInt(5, 0);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error adding category: " + e.getMessage(), e);
        }
    }

    /**
     * Menghapus kategori.
     */
    public void deleteCategory(String kategori) throws DatabaseException {
        if (kategori == null || kategori.trim().isEmpty()) {
            throw new DatabaseException("Kategori tidak boleh kosong");
        }

        // Delete template products with this category
        String sql = "DELETE FROM products WHERE kategori = ? AND nama LIKE '[Template]%'";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, kategori);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error deleting category: " + e.getMessage(), e);
        }
    }
}
