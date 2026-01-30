package com.upb.agripos.dao;

import java.util.List;

import com.upb.agripos.exception.DatabaseException;
import com.upb.agripos.model.User;

/**
 * Interface untuk DAO User.
 */
public interface IUserDAO {
    /**
     * Mencari user berdasarkan username.
     */
    User getByUsername(String username) throws DatabaseException;

    /**
     * Mencari user berdasarkan ID.
     */
    User getById(long id) throws DatabaseException;

    /**
     * Menambah user baru ke database.
     */
    void create(User user) throws DatabaseException;

    /**
     * Mengupdate user.
     */
    void update(User user) throws DatabaseException;

    /**
     * Menghapus user berdasarkan ID.
     */
    void delete(long id) throws DatabaseException;

    /**
     * Mendapatkan semua user.
     */
    List<User> getAll() throws DatabaseException;
}
