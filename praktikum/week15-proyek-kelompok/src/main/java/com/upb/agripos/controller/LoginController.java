package com.upb.agripos.controller;

import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.exception.DatabaseException;
import com.upb.agripos.model.User;
import com.upb.agripos.service.AuthService;
import com.upb.agripos.view.LoginView;

/**
 * Controller untuk layer autentikasi.
 * Helper/utility class untuk menangani login logic.
 * Main logic sudah ditangani di AppJavaFX.
 */
public class LoginController {
    private AuthService authService;

    public LoginController() throws DatabaseException {
        this.authService = new AuthService();
    }

    /**
     * Validate input dan authentikasi user
     * @return User jika berhasil, null jika gagal
     */
    public User authenticate(String username, String password) throws ValidationException, DatabaseException {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("Username tidak boleh kosong");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new ValidationException("Password tidak boleh kosong");
        }
        return authService.login(username, password);
    }

    /**
     * Get current logged in user
     */
    public User getCurrentUser() {
        return authService.getCurrentUser();
    }

    /**
     * Check if user is logged in
     */
    public boolean isLoggedIn() {
        return authService.isLoggedIn();
    }

    /**
     * Logout current user
     */
    public void logout() {
        authService.logout();
    }

    /**
     * Check if current user is admin
     */
    public boolean isAdmin() {
        return authService.isAdmin();
    }

    /**
     * Check if current user is kasir
     */
    public boolean isKasir() {
        return authService.isKasir();
    }
}
