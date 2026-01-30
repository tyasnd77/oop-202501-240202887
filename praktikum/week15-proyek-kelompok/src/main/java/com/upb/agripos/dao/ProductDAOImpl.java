package com.upb.agripos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.upb.agripos.exception.DatabaseException;
import com.upb.agripos.model.Product;

/**
 * Implementasi DAO untuk Product menggunakan JDBC.
 */
public class ProductDAOImpl implements IProductDAO {

    public ProductDAOImpl() throws DatabaseException {
        // Ensure connection is available
        try {
            DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get database connection", e);
        }
    }

    /**
     * Get connection from DatabaseConnection singleton
     */
    private Connection getConnection() throws DatabaseException {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            if (conn == null || conn.isClosed()) {
                throw new DatabaseException("Database connection is closed or unavailable");
            }
            return conn;
        } catch (SQLException e) {
            throw new DatabaseException("Error getting database connection: " + e.getMessage(), e);
        }
    }

    @Override
    public void create(Product product) throws DatabaseException {
        String sql = "INSERT INTO products (kode, nama, kategori, harga, stok) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, product.getKode());
            stmt.setString(2, product.getNama());
            stmt.setString(3, product.getKategori());
            stmt.setDouble(4, product.getHarga());
            stmt.setInt(5, product.getStok());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error inserting product: " + e.getMessage(), e);
        }
    }

    @Override
    public Product getById(int id) throws DatabaseException {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToProduct(rs);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving product by id: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Product> getAll() throws DatabaseException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY id ASC";
        try (Statement stmt = getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving all products: " + e.getMessage(), e);
        }
        return products;
    }

    @Override
    public void update(Product product) throws DatabaseException {
        String sql = "UPDATE products SET kode = ?, nama = ?, kategori = ?, harga = ?, stok = ? WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, product.getKode());
            stmt.setString(2, product.getNama());
            stmt.setString(3, product.getKategori());
            stmt.setDouble(4, product.getHarga());
            stmt.setInt(5, product.getStok());
            stmt.setInt(6, product.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error updating product: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) throws DatabaseException {
        String sql = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting product: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Product> searchByName(String nama) throws DatabaseException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE nama ILIKE ? ORDER BY nama ASC";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, "%" + nama + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error searching products by name: " + e.getMessage(), e);
        }
        return products;
    }

    @Override
    public List<Product> getByCategory(String kategori) throws DatabaseException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE kategori = ? ORDER BY nama ASC";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, kategori);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving products by category: " + e.getMessage(), e);
        }
        return products;
    }

    /**
     * Helper method untuk map ResultSet ke Product object.
     */
    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setKode(rs.getString("kode"));
        product.setNama(rs.getString("nama"));
        product.setKategori(rs.getString("kategori"));
        product.setHarga(rs.getDouble("harga"));
        product.setStok(rs.getInt("stok"));
        return product;
    }
}
