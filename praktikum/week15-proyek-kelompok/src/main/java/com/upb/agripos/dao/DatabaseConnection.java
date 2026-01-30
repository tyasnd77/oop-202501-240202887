package com.upb.agripos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.upb.agripos.config.EnvConfig;

/**
 * Singleton class untuk mengelola koneksi database PostgreSQL.
 * Mengimplementasikan Singleton Pattern.
 * Membaca konfigurasi database dari file .env
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    // Database configuration dari .env
    private static final String URL = EnvConfig.getDatabaseUrl();
    private static final String USERNAME = EnvConfig.getDatabaseUsername();
    private static final String PASSWORD = EnvConfig.getDatabasePassword();
    private static final String DRIVER = EnvConfig.getDatabaseDriver();

    // Private constructor untuk Singleton
    private DatabaseConnection() throws SQLException {
        try {
            // Load PostgreSQL JDBC Driver
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database connection established successfully");
            System.out.println("Connected to: " + URL);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database JDBC Driver not found: " + DRIVER, e);
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Singleton instance getter.
     */
    public static synchronized DatabaseConnection getInstance() throws SQLException {
        if (instance == null || instance.connection == null || instance.connection.isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Get current connection.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Close connection.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed");
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }

    /**
     * Check if connection is still alive.
     */
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
