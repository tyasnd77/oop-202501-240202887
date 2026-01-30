package com.upb.agripos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.upb.agripos.exception.DatabaseException;
import com.upb.agripos.model.User;

/**
 * Implementasi DAO untuk User menggunakan JDBC.
 */
public class UserDAOImpl implements IUserDAO {

    public UserDAOImpl() throws DatabaseException {
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
    public User getByUsername(String username) throws DatabaseException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving user by username: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public User getById(long id) throws DatabaseException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving user by id: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void create(User user) throws DatabaseException {
        String sql = "INSERT INTO users (username, password, role, nama_lengkap) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getNamaLengkap());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error creating user: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(User user) throws DatabaseException {
        String sql = "UPDATE users SET username = ?, password = ?, role = ?, nama_lengkap = ? WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getNamaLengkap());
            stmt.setInt(5, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error updating user: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(long id) throws DatabaseException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting user: " + e.getMessage(), e);
        }
    }

    @Override
    public List<User> getAll() throws DatabaseException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving all users: " + e.getMessage(), e);
        }
        return users;
    }

    /**
     * Helper method untuk map ResultSet ke User object.
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        user.setNamaLengkap(rs.getString("nama_lengkap"));
        return user;
    }
}

