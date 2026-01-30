package com.upb.agripos.service;

import com.upb.agripos.dao.IProductDAO;
import com.upb.agripos.dao.ProductDAOImpl;
import com.upb.agripos.model.Product;
import com.upb.agripos.exception.DatabaseException;
import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.exception.OutOfStockException;

import java.util.List;

/**
 * Service untuk mengelola produk (business logic).
 * Mengimplementasikan FR-1: Manajemen Produk.
 */
public class ProductService {
    private IProductDAO productDAO;

    public ProductService() throws DatabaseException {
        this.productDAO = new ProductDAOImpl();
    }

    /**
     * Menambah produk baru dengan validasi.
     */
    public void addProduct(String kode, String nama, String kategori, double harga, int stok) 
            throws ValidationException, DatabaseException {
        // Validasi input
        if (kode == null || kode.isEmpty()) {
            throw new ValidationException("Kode produk tidak boleh kosong");
        }
        if (nama == null || nama.isEmpty()) {
            throw new ValidationException("Nama produk tidak boleh kosong");
        }
        if (harga < 0) {
            throw new ValidationException("Harga tidak boleh negatif");
        }
        if (stok < 0) {
            throw new ValidationException("Stok tidak boleh negatif");
        }

        Product product = new Product(kode, nama, kategori, harga, stok);
        productDAO.create(product);
    }

    /**
     * Mengambil semua produk.
     */
    public List<Product> getAllProducts() throws DatabaseException {
        return productDAO.getAll();
    }

    /**
     * Mengambil produk berdasarkan ID.
     */
    public Product getProductById(int id) throws DatabaseException {
        return productDAO.getById(id);
    }

    /**
     * Mengupdate produk dengan validasi.
     */
    public void updateProduct(int id, String nama, String kategori, double harga, int stok) 
            throws ValidationException, DatabaseException {
        Product product = productDAO.getById(id);
        if (product == null) {
            throw new ValidationException("Produk dengan ID " + id + " tidak ditemukan");
        }

        if (nama != null && !nama.isEmpty()) {
            product.setNama(nama);
        }
        if (kategori != null && !kategori.isEmpty()) {
            product.setKategori(kategori);
        }
        if (harga >= 0) {
            product.setHarga(harga);
        }
        if (stok >= 0) {
            product.setStok(stok);
        }

        productDAO.update(product);
    }

    /**
     * Menghapus produk.
     */
    public void deleteProduct(int id) throws DatabaseException {
        productDAO.delete(id);
    }

    /**
     * Mencari produk berdasarkan nama.
     */
    public List<Product> searchProducts(String nama) throws DatabaseException {
        if (nama == null || nama.isEmpty()) {
            return productDAO.getAll();
        }
        return productDAO.searchByName(nama);
    }

    /**
     * Mendapatkan produk berdasarkan kategori.
     */
    public List<Product> getProductsByCategory(String kategori) throws DatabaseException {
        return productDAO.getByCategory(kategori);
    }

    /**
     * Mengurangi stok produk (untuk transaksi penjualan).
     */
    public void reduceStock(int productId, int quantity) 
            throws OutOfStockException, DatabaseException {
        Product product = productDAO.getById(productId);
        if (product == null) {
            throw new OutOfStockException("Produk tidak ditemukan");
        }
        if (product.getStok() < quantity) {
            throw new OutOfStockException("Stok " + product.getNama() + " tidak cukup. Stok tersedia: " + product.getStok());
        }

        product.setStok(product.getStok() - quantity);
        productDAO.update(product);
    }

    /**
     * Memeriksa apakah stok produk mencukupi.
     */
    public boolean isStockAvailable(int productId, int quantity) throws DatabaseException {
        Product product = productDAO.getById(productId);
        return product != null && product.getStok() >= quantity;
    }
}
