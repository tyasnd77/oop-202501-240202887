package com.upb.agripos.dao;

import com.upb.agripos.model.Product;
import com.upb.agripos.exception.DatabaseException;

import java.util.List;

/**
 * Interface untuk DAO Product.
 * Mendefinisikan operasi CRUD untuk Product.
 */
public interface IProductDAO {
    /**
     * Menambah produk baru ke database.
     */
    void create(Product product) throws DatabaseException;

    /**
     * Mengambil produk berdasarkan ID.
     */
    Product getById(int id) throws DatabaseException;

    /**
     * Mengambil semua produk.
     */
    List<Product> getAll() throws DatabaseException;

    /**
     * Mengupdate produk.
     */
    void update(Product product) throws DatabaseException;

    /**
     * Menghapus produk berdasarkan ID.
     */
    void delete(int id) throws DatabaseException;

    /**
     * Mencari produk berdasarkan nama.
     */
    List<Product> searchByName(String nama) throws DatabaseException;

    /**
     * Mengambil produk berdasarkan kategori.
     */
    List<Product> getByCategory(String kategori) throws DatabaseException;
}
