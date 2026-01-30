package com.upb.agripos.service;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.upb.agripos.model.TransactionHistory;

/**
 * Service untuk export data ke format Excel.
 */
public class ExcelExportService {

    /**
     * Export laporan transaksi ke file Excel.
     */
    public void exportTransactionsToExcel(List<TransactionHistory> transactions, LocalDate startDate, LocalDate endDate, String filePath) throws Exception {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Laporan Transaksi");
            
            // Create styles
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle titleStyle = createTitleStyle(workbook);
            CellStyle dataStyle = createDataStyle(workbook);
            CellStyle currencyStyle = createCurrencyStyle(workbook);

            int rowNum = 0;

            // Title
            Row titleRow = sheet.createRow(rowNum++);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("LAPORAN TRANSAKSI PENJUALAN AGRI-POS");
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

            // Spacer
            rowNum++;

            // Date Range Info
            Row dateRow = sheet.createRow(rowNum++);
            dateRow.createCell(0).setCellValue("Periode: " + (startDate != null ? startDate : "---") + " sampai " + (endDate != null ? endDate : "---"));

            // Spacer
            rowNum++;

            // Header Row
            Row headerRow = sheet.createRow(rowNum++);
            String[] headers = { "ID", "Tanggal", "Kasir", "Total Harga", "Jumlah Bayar", "Kembalian", "Metode", "Status" };
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Data Rows
            double totalAmount = 0;
            for (TransactionHistory transaction : transactions) {
                Row row = sheet.createRow(rowNum++);
                
                row.createCell(0).setCellValue(transaction.getId());
                row.getCell(0).setCellStyle(dataStyle);
                
                row.createCell(1).setCellValue(transaction.getTanggalFormatted());
                row.getCell(1).setCellStyle(dataStyle);
                
                row.createCell(2).setCellValue(transaction.getNamaKasir());
                row.getCell(2).setCellStyle(dataStyle);
                
                Cell totalCell = row.createCell(3);
                totalCell.setCellValue(transaction.getTotalHarga());
                totalCell.setCellStyle(currencyStyle);
                
                Cell bayarCell = row.createCell(4);
                bayarCell.setCellValue(transaction.getJumlahBayar());
                bayarCell.setCellStyle(currencyStyle);
                
                Cell kembalianCell = row.createCell(5);
                kembalianCell.setCellValue(transaction.getKembalian());
                kembalianCell.setCellStyle(currencyStyle);
                
                row.createCell(6).setCellValue(transaction.getMetodePayment());
                row.getCell(6).setCellStyle(dataStyle);
                
                row.createCell(7).setCellValue(transaction.getStatus());
                row.getCell(7).setCellStyle(dataStyle);
                
                totalAmount += transaction.getTotalHarga();
            }

            // Total Row
            rowNum++;
            Row totalRow = sheet.createRow(rowNum);
            Cell totalLabelCell = totalRow.createCell(2);
            totalLabelCell.setCellValue("TOTAL:");
            totalLabelCell.setCellStyle(headerStyle);
            
            Cell totalValueCell = totalRow.createCell(3);
            totalValueCell.setCellValue(totalAmount);
            totalValueCell.setCellStyle(currencyStyle);

            // Adjust column widths
            sheet.setColumnWidth(0, 8 * 256);
            sheet.setColumnWidth(1, 18 * 256);
            sheet.setColumnWidth(2, 15 * 256);
            sheet.setColumnWidth(3, 15 * 256);
            sheet.setColumnWidth(4, 15 * 256);
            sheet.setColumnWidth(5, 15 * 256);
            sheet.setColumnWidth(6, 12 * 256);
            sheet.setColumnWidth(7, 12 * 256);

            // Write to file
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
        }
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        style.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        style.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        style.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        return style;
    }

    private CellStyle createTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        style.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        style.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        style.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        return style;
    }

    private CellStyle createCurrencyStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));
        style.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        style.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        style.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        style.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        return style;
    }
}
