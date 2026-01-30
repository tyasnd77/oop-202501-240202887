package com.upb.agripos.model;

/**
 * Model untuk User (Kasir atau Admin).
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String role; // "KASIR" atau "ADMIN"
    private String namaLengkap;

    // Constructor
    public User() {}

    public User(int id, String username, String password, String role, String namaLengkap) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.namaLengkap = namaLengkap;
    }

    public User(String username, String password, String role, String namaLengkap) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.namaLengkap = namaLengkap;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(role);
    }

    public boolean isKasir() {
        return "KASIR".equalsIgnoreCase(role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", namaLengkap='" + namaLengkap + '\'' +
                '}';
    }
}
