package com.upb.agripos.controller;

import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.exception.DatabaseException;
import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.service.ReportService;
import java.util.List;

/**
 * Controller utility untuk layer Admin.
 * Helper class untuk menangani administrasi dan manajemen produk.
 * Main UI logic sudah ditangani di AppJavaFX.
 */
public class AdminController {
    private ProductService productService;
    private ReportService reportService;

    public AdminController() throws DatabaseException {
        this.productService = new ProductService();
        this.reportService = new ReportService();
    }

    /**
     * Get all products dari database
     */
    public List<Product> getAllProducts() throws DatabaseException {
        return productService.getAllProducts();
    }

    /**
     * Search products by keyword
     */
    public List<Product> searchProducts(String keyword) throws DatabaseException {
        return productService.searchProducts(keyword);
    }

    /**
     * Get products by category
     */
    public List<Product> getByCategory(String category) throws DatabaseException {
        return productService.getProductsByCategory(category);
    }

    /**
     * Add product dengan validasi
     */
    public void addProduct(String kode, String nama, String kategori, double harga, int stok) 
            throws ValidationException, DatabaseException {
        if (kode == null || kode.trim().isEmpty()) {
            throw new ValidationException("Kode produk harus diisi");
        }
        if (nama == null || nama.isEmpty()) {
            throw new ValidationException("Nama produk harus diisi");
        }
        if (harga < 0) {
            throw new ValidationException("Harga tidak boleh negatif");
        }
        if (stok < 0) {
            throw new ValidationException("Stok tidak boleh negatif");
        }
        productService.addProduct(kode, nama, kategori, harga, stok);
    }

    /**
     * Update product dengan validasi
     */
    public void updateProduct(int id, String nama, String kategori, double harga, int stok) 
            throws ValidationException, DatabaseException {
        if (nama == null || nama.isEmpty()) {
            throw new ValidationException("Nama produk harus diisi");
        }
        if (harga < 0) {
            throw new ValidationException("Harga tidak boleh negatif");
        }
        if (stok < 0) {
            throw new ValidationException("Stok tidak boleh negatif");
        }
        productService.updateProduct(id, nama, kategori, harga, stok);
    }

    /**
     * Delete product
     */
    public void deleteProduct(int productId) throws DatabaseException {
        productService.deleteProduct(productId);
    }

    /**
     * Get product by ID
     */
    public Product getProductById(int productId) throws DatabaseException {
        return productService.getProductById(productId);
    }
}
