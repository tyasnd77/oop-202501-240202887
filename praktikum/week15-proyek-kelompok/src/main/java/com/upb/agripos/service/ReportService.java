package com.upb.agripos.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.upb.agripos.dao.DatabaseConnection;
import com.upb.agripos.exception.DatabaseException;
import com.upb.agripos.model.TransactionHistory;

public class ReportService {

    public List<TransactionHistory> getReportByDateRange(LocalDate startDate, LocalDate endDate) 
            throws DatabaseException {
        List<TransactionHistory> reports = new ArrayList<>();
        
        if (startDate == null) startDate = LocalDate.of(2000, 1, 1);
        if (endDate == null) endDate = LocalDate.now();

        String sql = "SELECT t.id, t.tanggal, u.nama_lengkap, t.total_harga, t.metode_payment, " +
                     "t.jumlah_bayar, t.kembalian, t.status FROM transactions t " +
                     "JOIN users u ON t.user_id = u.id " +
                     "WHERE CAST(t.tanggal AS DATE) >= ? AND CAST(t.tanggal AS DATE) <= ? " +
                     "ORDER BY t.tanggal DESC";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, java.sql.Date.valueOf(startDate));
            stmt.setDate(2, java.sql.Date.valueOf(endDate));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TransactionHistory history = new TransactionHistory(
                        rs.getInt("id"),
                        rs.getTimestamp("tanggal").toLocalDateTime(),
                        rs.getString("nama_lengkap"),
                        rs.getDouble("total_harga"),
                        rs.getString("metode_payment"),
                        rs.getDouble("jumlah_bayar"),
                        rs.getDouble("kembalian"),
                        rs.getString("status")
                    );
                    reports.add(history);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error fetching report: " + e.getMessage(), e);
        }

        return reports;
    }

    public ReportStatistics getStatisticsByDateRange(LocalDate startDate, LocalDate endDate) 
            throws DatabaseException {
        if (startDate == null) startDate = LocalDate.now();
        if (endDate == null) endDate = LocalDate.now();

        String sql = "SELECT COUNT(id) as total_transaksi, COALESCE(SUM(total_harga), 0) as total_penjualan " +
                     "FROM transactions " +
                     "WHERE CAST(tanggal AS DATE) >= ? AND CAST(tanggal AS DATE) <= ? AND status = 'Sukses'";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, java.sql.Date.valueOf(startDate));
            stmt.setDate(2, java.sql.Date.valueOf(endDate));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    long totalTransaksi = rs.getLong("total_transaksi");
                    double totalPenjualan = rs.getDouble("total_penjualan");
                    return new ReportStatistics(totalTransaksi, totalPenjualan);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error fetching statistics: " + e.getMessage(), e);
        }

        return new ReportStatistics(0, 0);
    }

    public static class ReportStatistics {
        public final long totalTransaksi;
        public final double totalPenjualan;

        public ReportStatistics(long totalTransaksi, double totalPenjualan) {
            this.totalTransaksi = totalTransaksi;
            this.totalPenjualan = totalPenjualan;
        }
    }
}
