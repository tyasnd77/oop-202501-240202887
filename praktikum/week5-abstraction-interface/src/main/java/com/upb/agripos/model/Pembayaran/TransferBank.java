package com.upb.agripos.model.Pembayaran;

import com.upb.agripos.model.kontrak.Receiptable;
import com.upb.agripos.model.kontrak.Validatable;

public class TransferBank extends Pembayaran implements Validatable, Receiptable {

    private String bank;
    private String norek;

    public TransferBank(String invoiceNo, double total, String bank, String norek) {
        super(invoiceNo, total);
        this.bank = bank;
        this.norek = norek;
    }

    @Override
    public double biaya() {
        return 3500; // biaya tetap
    }

    @Override
    public boolean prosesPembayaran() {
        return true;
    }

    @Override
    public boolean validate() {
        return norek.matches("\\d{10,15}");
    }

    @Override
    public String cetakStruk() {
        return String.format(
            "INVOICE %s | TOTAL+FEE: %.2f | TRANSFER BANK: %s (%s) | STATUS: %s",
            invoiceNo,
            totalBayar(),
            bank,
            norek,
            prosesPembayaran() ? "BERHASIL" : "GAGAL"
        );
    }
}
