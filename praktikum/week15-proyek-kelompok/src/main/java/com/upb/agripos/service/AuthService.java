package com.upb.agripos.service;

import com.upb.agripos.dao.IUserDAO;
import com.upb.agripos.dao.UserDAOImpl;
import com.upb.agripos.exception.DatabaseException;
import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.model.User;

/**
 * Service untuk mengelola autentikasi dan user.
 * Mengimplementasikan FR-5: Login dan Hak Akses.
 */
public class AuthService {
    private IUserDAO userDAO;
    private User currentUser;

    public AuthService() throws DatabaseException {
        this.userDAO = new UserDAOImpl();
    }

    /**
     * Login user dengan username dan password.
     */
    public User login(String username, String password) 
            throws ValidationException, DatabaseException {
        if (username == null || username.isEmpty()) {
            throw new ValidationException("Username tidak boleh kosong");
        }
        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password tidak boleh kosong");
        }

        User user = userDAO.getByUsername(username);
        if (user == null) {
            throw new ValidationException("Username tidak ditemukan");
        }

        // Validasi password (simple comparison, dalam production gunakan hashing)
        if (!user.getPassword().equals(password)) {
            throw new ValidationException("Password salah");
        }

        this.currentUser = user;
        return user;
    }

    /**
     * Logout user.
     */
    public void logout() {
        this.currentUser = null;
    }

    /**
     * Mendapatkan user yang sedang login.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Mengecek apakah user sudah login.
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Mengecek apakah user adalah admin.
     */
    public boolean isAdmin() {
        return currentUser != null && currentUser.isAdmin();
    }

    /**
     * Mengecek apakah user adalah kasir.
     */
    public boolean isKasir() {
        return currentUser != null && currentUser.isKasir();
    }

    /**
     * Menambah user baru (untuk setup awal).
     */
    public void registerUser(String username, String password, String role, String namaLengkap) 
            throws ValidationException, DatabaseException {
        if (username == null || username.isEmpty()) {
            throw new ValidationException("Username tidak boleh kosong");
        }
        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password tidak boleh kosong");
        }

        User existingUser = userDAO.getByUsername(username);
        if (existingUser != null) {
            throw new ValidationException("Username sudah terdaftar");
        }

        User newUser = new User(username, password, role, namaLengkap);
        userDAO.create(newUser);
    }

    /**
     * Create user baru (alias untuk registerUser - untuk Admin create user).
     */
    public void createUser(String username, String password, String role, String namaLengkap) 
            throws ValidationException, DatabaseException {
        registerUser(username, password, role, namaLengkap);
    }

    /**
     * Update user yang sudah ada.
     */
    public void updateUser(int userId, String username, String password, String role, String namaLengkap) 
            throws ValidationException, DatabaseException {
        if (username == null || username.isEmpty()) {
            throw new ValidationException("Username tidak boleh kosong");
        }
        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password tidak boleh kosong");
        }

        User user = userDAO.getById(userId);
        if (user == null) {
            throw new ValidationException("User tidak ditemukan");
        }

        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setNamaLengkap(namaLengkap);
        userDAO.update(user);
    }

    /**
     * Menghapus user.
     */
    public void deleteUser(int userId) throws ValidationException, DatabaseException {
        userDAO.delete(userId);
    }

    /**
     * Mendapatkan semua user.
     */
    public java.util.List<User> getAllUsers() throws DatabaseException {
        return userDAO.getAll();
    }
}
