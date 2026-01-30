package com.upb.agripos.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.upb.agripos.dao.DatabaseConnection;
import com.upb.agripos.exception.DatabaseException;
import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.model.Transaction;
import com.upb.agripos.model.TransactionHistory;

/**
 * Service untuk mengelola transaksi penjualan (business logic).
 * Mengimplementasikan FR-3: Metode Pembayaran dan FR-4: Struk & Laporan.
 */
public class TransactionService {
    private PaymentMethod paymentMethod;

    /**
     * Set metode pembayaran yang akan digunakan.
     */
    public void setPaymentMethod(String methodType) throws ValidationException {
        switch (methodType.toUpperCase()) {
            case "TUNAI":
                this.paymentMethod = new CashPayment();
                break;
            case "EWALLET":
                this.paymentMethod = new EWalletPayment();
                break;
            default:
                throw new ValidationException("Metode pembayaran tidak dikenali: " + methodType);
        }
    }

    /**
     * Memproses pembayaran transaksi.
     */
    public boolean processPayment(Transaction transaction, double jumlahBayar) 
            throws ValidationException {
        if (paymentMethod == null) {
            throw new ValidationException("Metode pembayaran belum dipilih");
        }

        if (!paymentMethod.validatePayment(jumlahBayar, transaction.getTotalHarga())) {
            throw new ValidationException("Pembayaran gagal: jumlah tidak cukup");
        }

        boolean paymentSuccess = paymentMethod.processPayment(jumlahBayar, transaction.getTotalHarga());
        if (paymentSuccess) {
            transaction.setMetodePayment(paymentMethod.getPaymentMethodName());
            transaction.setJumlahBayar(jumlahBayar);
            transaction.setKembalian(paymentMethod.calculateChange(jumlahBayar, transaction.getTotalHarga()));
            // Sesuaikan dengan check constraint di DB (Sukses/Pending/Dibatalkan)
            transaction.setStatus("Sukses");
        } else {
            transaction.setStatus("Dibatalkan");
        }

        return paymentSuccess;
    }

    /**
     * Menyimpan transaksi ke database.
     */
    public void saveTransaction(Transaction transaction) throws DatabaseException {
        String sql = "INSERT INTO transactions (user_id, tanggal, total_harga, metode_payment, jumlah_bayar, kembalian, status) " +
                     "VALUES (?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, transaction.getUserId());
            stmt.setDouble(2, transaction.getTotalHarga());
            stmt.setString(3, transaction.getMetodePayment());
            stmt.setDouble(4, transaction.getJumlahBayar());
            stmt.setDouble(5, transaction.getKembalian());
            stmt.setString(6, transaction.getStatus());
            
            stmt.executeUpdate();
            System.out.println("Transaction saved successfully");
        } catch (SQLException e) {
            throw new DatabaseException("Error saving transaction: " + e.getMessage(), e);
        }
    }

    /**
     * Generate struk transaksi dalam format string.
     */
    public String generateReceipt(Transaction transaction) {
        StringBuilder receipt = new StringBuilder();
        receipt.append("========================================\n");
        receipt.append("              AGRI-POS RECEIPT\n");
        receipt.append("========================================\n");
        receipt.append("Tanggal: ").append(transaction.getTanggalFormatted()).append("\n");
        receipt.append("----------------------------------------\n");

        // Detail items
        receipt.append("Item:\n");
        transaction.getCart().getItems().forEach(item -> {
            receipt.append(String.format("%-20s %3d x %8.0f = %10.0f\n",
                    item.getProduct().getNama(),
                    item.getQuantity(),
                    item.getProduct().getHarga(),
                    item.getSubtotal()));
        });

        receipt.append("----------------------------------------\n");
        receipt.append(String.format("Total:                      Rp %10.0f\n", transaction.getTotalHarga()));
        receipt.append(String.format("Metode Pembayaran:          %s\n", transaction.getMetodePayment()));
        receipt.append(String.format("Jumlah Dibayar:             Rp %10.0f\n", transaction.getJumlahBayar()));
        receipt.append(String.format("Kembalian:                  Rp %10.0f\n", transaction.getKembalian()));
        receipt.append("----------------------------------------\n");
        receipt.append("        Terima Kasih Atas Belanja Anda\n");
        receipt.append("========================================\n");

        return receipt.toString();
    }

    /**
     * Mengambil riwayat transaksi dari database.
     */
    public List<TransactionHistory> getTransactionHistory() throws DatabaseException {
        List<TransactionHistory> history = new ArrayList<>();
        String sql = "SELECT t.id, t.tanggal, u.nama_lengkap, t.total_harga, t.metode_payment, " +
                     "t.jumlah_bayar, t.kembalian, t.status " +
                     "FROM transactions t " +
                     "JOIN users u ON t.user_id = u.id " +
                     "ORDER BY t.tanggal DESC";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                TransactionHistory txn = new TransactionHistory(
                    rs.getInt("id"),
                    rs.getTimestamp("tanggal").toLocalDateTime(),
                    rs.getString("nama_lengkap"),
                    rs.getDouble("total_harga"),
                    rs.getString("metode_payment"),
                    rs.getDouble("jumlah_bayar"),
                    rs.getDouble("kembalian"),
                    rs.getString("status")
                );
                history.add(txn);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving transaction history: " + e.getMessage(), e);
        }
        
        return history;
    }

    /**
     * Mengambil riwayat transaksi untuk kasir tertentu.
     */
    public List<TransactionHistory> getTransactionHistoryByUser(int userId) throws DatabaseException {
        List<TransactionHistory> history = new ArrayList<>();
        String sql = "SELECT t.id, t.tanggal, u.nama_lengkap, t.total_harga, t.metode_payment, " +
                     "t.jumlah_bayar, t.kembalian, t.status " +
                     "FROM transactions t " +
                     "JOIN users u ON t.user_id = u.id " +
                     "WHERE t.user_id = ? " +
                     "ORDER BY t.tanggal DESC";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                TransactionHistory txn = new TransactionHistory(
                    rs.getInt("id"),
                    rs.getTimestamp("tanggal").toLocalDateTime(),
                    rs.getString("nama_lengkap"),
                    rs.getDouble("total_harga"),
                    rs.getString("metode_payment"),
                    rs.getDouble("jumlah_bayar"),
                    rs.getDouble("kembalian"),
                    rs.getString("status")
                );
                history.add(txn);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving transaction history for user: " + e.getMessage(), e);
        }
        
        return history;
    }

    /**
     * Mendapatkan metode pembayaran yang sedang digunakan.
     */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Mengambil riwayat transaksi dengan filter metode pembayaran dan status.
     */
    public List<TransactionHistory> getTransactionHistoryByUserWithFilter(int userId, String metodeFilter, String statusFilter) throws DatabaseException {
        List<TransactionHistory> history = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder(
            "SELECT t.id, t.tanggal, u.nama_lengkap, t.total_harga, t.metode_payment, " +
            "t.jumlah_bayar, t.kembalian, t.status " +
            "FROM transactions t " +
            "JOIN users u ON t.user_id = u.id " +
            "WHERE t.user_id = ?"
        );
        
        // Add filter kondisi
        if (metodeFilter != null && !metodeFilter.equals("Semua")) {
            sql.append(" AND t.metode_payment = ?");
        }
        if (statusFilter != null && !statusFilter.equals("Semua")) {
            sql.append(" AND t.status = ?");
        }
        
        sql.append(" ORDER BY t.tanggal DESC");
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            stmt.setInt(paramIndex++, userId);
            
            if (metodeFilter != null && !metodeFilter.equals("Semua")) {
                stmt.setString(paramIndex++, metodeFilter);
            }
            if (statusFilter != null && !statusFilter.equals("Semua")) {
                stmt.setString(paramIndex++, statusFilter);
            }
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                TransactionHistory txn = new TransactionHistory(
                    rs.getInt("id"),
                    rs.getTimestamp("tanggal").toLocalDateTime(),
                    rs.getString("nama_lengkap"),
                    rs.getDouble("total_harga"),
                    rs.getString("metode_payment"),
                    rs.getDouble("jumlah_bayar"),
                    rs.getDouble("kembalian"),
                    rs.getString("status")
                );
                history.add(txn);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving filtered transaction history: " + e.getMessage(), e);
        }
        
        return history;
    }

    /**
     * Mengambil riwayat transaksi semua kasir dengan filter metode dan status.
     */
    public List<TransactionHistory> getTransactionHistoryWithFilter(String metodeFilter, String statusFilter) throws DatabaseException {
        List<TransactionHistory> history = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder(
            "SELECT t.id, t.tanggal, u.nama_lengkap, t.total_harga, t.metode_payment, " +
            "t.jumlah_bayar, t.kembalian, t.status " +
            "FROM transactions t " +
            "JOIN users u ON t.user_id = u.id " +
            "WHERE 1=1"
        );
        
        // Add filter kondisi
        if (metodeFilter != null && !metodeFilter.equals("Semua")) {
            sql.append(" AND t.metode_payment = ?");
        }
        if (statusFilter != null && !statusFilter.equals("Semua")) {
            sql.append(" AND t.status = ?");
        }
        
        sql.append(" ORDER BY t.tanggal DESC");
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            
            if (metodeFilter != null && !metodeFilter.equals("Semua")) {
                stmt.setString(paramIndex++, metodeFilter);
            }
            if (statusFilter != null && !statusFilter.equals("Semua")) {
                stmt.setString(paramIndex++, statusFilter);
            }
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                TransactionHistory txn = new TransactionHistory(
                    rs.getInt("id"),
                    rs.getTimestamp("tanggal").toLocalDateTime(),
                    rs.getString("nama_lengkap"),
                    rs.getDouble("total_harga"),
                    rs.getString("metode_payment"),
                    rs.getDouble("jumlah_bayar"),
                    rs.getDouble("kembalian"),
                    rs.getString("status")
                );
                history.add(txn);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving filtered transaction history: " + e.getMessage(), e);
        }
        
        return history;
    }
}
