# Laporan Praktikum Minggu 5 
Topik: Abstraction (Abstract Class & Interface)


## Identitas
- Nama  : Tyas Nurshika Damaia
- NIM   : 240202887
- Kelas : 3IKRB

---

## Tujuan
Mahasiswa mampu **menjelaskan perbedaan abstract class dan interface**.
- Mahasiswa mampu **mendesain abstract class dengan method abstrak** sesuai kebutuhan kasus.
- Mahasiswa mampu **membuat interface dan mengimplementasikannya pada class**.
- Mahasiswa mampu **menerapkan multiple inheritance melalui interface** pada rancangan kelas.
- Mahasiswa mampu **mendokumentasikan kode** (komentar kelas/method, README singkat pada folder minggu).

---

## Dasar Teori
**Abstraksi** adalah proses menyederhanakan kompleksitas dengan menampilkan elemen penting dan menyembunyikan detail implementasi.
- **Abstract class**: tidak dapat diinstansiasi, dapat memiliki method abstrak (tanpa badan) dan non-abstrak. Dapat menyimpan state (field).
- **Interface**: kumpulan kontrak (method tanpa implementasi konkret). Sejak Java 8 mendukung default method. Mendukung **multiple inheritance** (class dapat mengimplementasikan banyak interface).
- Gunakan **abstract class** bila ada _shared state_ dan perilaku dasar; gunakan **interface** untuk mendefinisikan kemampuan/kontrak lintas hierarki.

Dalam konteks Agri-POS, **Pembayaran** dapat dimodelkan sebagai abstract class dengan method abstrak `prosesPembayaran()` dan `biaya()`. Implementasi konkritnya: `Cash` dan `EWallet`. Kemudian, interface seperti `Validatable` (mis. verifikasi OTP) dan `Receiptable` (mencetak bukti) dapat diimplementasikan oleh jenis pembayaran yang relevan.

---

## Langkah Praktikum
1. **Abstract Class – Pembayaran**
   - Buat `Pembayaran` (abstract) dengan field `invoiceNo`, `total` dan method:
     - `double biaya()` (abstrak) → biaya tambahan (fee).
     - `boolean prosesPembayaran()` (abstrak) → mengembalikan status berhasil/gagal.
     - `double totalBayar()` (konkrit) → `return total + biaya();`.

2. **Subclass Konkret**
   - `Cash` → biaya = 0, proses = selalu berhasil jika `tunai >= totalBayar()`.
   - `EWallet` → biaya = 1.5% dari `total`; proses = membutuhkan validasi.

3. **Interface**
   - `Validatable` → `boolean validasi();` (contoh: OTP).
   - `Receiptable` → `String cetakStruk();`

4. **Multiple Inheritance via Interface**
   - `EWallet` mengimplementasikan **dua interface**: `Validatable`, `Receiptable`.
   - `Cash` setidaknya mengimplementasikan `Receiptable`.

5. **Main Class**
    - Buat `MainAbstraction.java` untuk mendemonstrasikan pemakaian `Pembayaran` (polimorfik).
    - Tampilkan hasil proses dan struk. Di akhir, panggil `CreditBy.print("[NIM]", "[Nama]")`.

6. **Commit dan Push**
   - Commit dengan pesan: `week5-abstraction-interface`.

---

## Kode Program
### Pembayaran.java (abstract)
```java
package com.upb.agripos.model.pembayaran;

public abstract class Pembayaran {
    protected String invoiceNo;
    protected double total;

    public Pembayaran(String invoiceNo, double total) {
        this.invoiceNo = invoiceNo;
        this.total = total;
    }

    public abstract double biaya();               // fee/biaya tambahan
    public abstract boolean prosesPembayaran();   // proses spesifik tiap metode

    public double totalBayar() {
        return total + biaya();
    }

    public String getInvoiceNo() { return invoiceNo; }
    public double getTotal() { return total; }
}
```

### Interface: Validatable & Receiptable
```java
package com.upb.agripos.model.kontrak;

public interface Validatable {
    boolean validasi(); // misal validasi OTP/ PIN
}
```
```java
package com.upb.agripos.model.kontrak;

public interface Receiptable {
    String cetakStruk();
}
```

### Cash.java (extends Pembayaran, implements Receiptable)
```java
package com.upb.agripos.model.pembayaran;

import com.upb.agripos.model.kontrak.Receiptable;

public class Cash extends Pembayaran implements Receiptable {
    private double tunai;

    public Cash(String invoiceNo, double total, double tunai) {
        super(invoiceNo, total);
        this.tunai = tunai;
    }

    @Override
    public double biaya() {
        return 0.0;
    }

    @Override
    public boolean prosesPembayaran() {
        return tunai >= totalBayar(); // sederhana: cukup uang tunai
    }

    @Override
    public String cetakStruk() {
        return String.format("INVOICE %s | TOTAL: %.2f | BAYAR CASH: %.2f | KEMBALI: %.2f",
                invoiceNo, totalBayar(), tunai, Math.max(0, tunai - totalBayar()));
    }
}
```

### EWallet.java (extends Pembayaran, implements Validatable & Receiptable)
```java
package com.upb.agripos.model.pembayaran;

import com.upb.agripos.model.kontrak.Validatable;
import com.upb.agripos.model.kontrak.Receiptable;

public class EWallet extends Pembayaran implements Validatable, Receiptable {
    private String akun;
    private String otp; // sederhana untuk simulasi

    public EWallet(String invoiceNo, double total, String akun, String otp) {
        super(invoiceNo, total);
        this.akun = akun;
        this.otp = otp;
    }

    @Override
    public double biaya() {
        return total * 0.015; // 1.5% fee
    }

    @Override
    public boolean validasi() {
        return otp != null && otp.length() == 6; // contoh validasi sederhana
    }

    @Override
    public boolean prosesPembayaran() {
        return validasi(); // jika validasi lolos, anggap berhasil
    }

    @Override
    public String cetakStruk() {
        return String.format("INVOICE %s | TOTAL+FEE: %.2f | E-WALLET: %s | STATUS: %s",
                invoiceNo, totalBayar(), akun, prosesPembayaran() ? "BERHASIL" : "GAGAL");
    }
}
```
### TransferBank.java
```java
package com.upb.agripos.model.pembayaran;

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
```

### MainAbstraction.java
```java
package com.upb.agripos;

import com.upb.agripos.model.pembayaran.*;
import com.upb.agripos.model.kontrak.*;
import com.upb.agripos.util.CreditBy;

public class MainAbstraction {
    public static void main(String[] args) {
        Pembayaran cash = new Cash("INV-001", 100000, 120000);
        Pembayaran ew = new EWallet("INV-002", 150000, "user@ewallet", "123456");
        Pembayaran tf   = new TransferBank("INV-003", 500000, "Bank TND", "240202887123");

        System.out.println(((Receiptable) cash).cetakStruk());
        System.out.println(((Receiptable) ew).cetakStruk());
        System.out.println(((Receiptable) tf).cetakStruk());

    CreditBy.print("240202887", "Tyas Nurshika Damaia");
    }
}
```

---

## Hasil Eksekusi
 
![Screenshot Output1](screenshots/ouput_week5(1).png)

TransferBank
![Screenshot Output2](screenshots/ouput_week5(transferbank).png)

---

## Analisis

- Jelaskan bagaimana kode berjalan.  
Program dimulai dari MainAbstraction.java. Pada bagian ini, tiga objek pembayaran dibuat menggunakan konsep abstract class, yaitu Cash, EWallet, dan TransferBank, yang semuanya merupakan turunan dari kelas abstrak Pembayaran.
Masing-masing kelas turunan mengimplementasikan metode abstrak biaya() dan prosesPembayaran() sesuai logika pembayaran masing-masing. Selain itu, kelas pembayaran juga mengimplementasikan interface Receiptable (dan beberapa juga Validatable) untuk menampilkan struk dan memvalidasi proses pembayaran.

Setelah objek dibuat, program mencetak struk pembayaran dengan melakukan casting ke interface Receiptable, lalu memanggil metode cetakStruk(). Pada akhir program, informasi credit ditampilkan menggunakan CreditBy.print().

- Apa perbedaan pendekatan minggu ini dibanding minggu sebelumnya. 
 Pendekatan minggu ini fokus pada:

1. Abstract Class & Polymorphism Lebih Mendalam
Kelas Pembayaran dibuat sebagai abstract class, sehingga kelas turunan wajib mengimplementasikan metode tertentu. Ini memberikan desain yang lebih fleksibel dan memaksa setiap metode pembayaran memiliki logikanya sendiri.

2. Multiple Inheritance Menggunakan Interface
Dua interface, yaitu Validatable dan Receiptable, diimplementasikan pada kelas yang membutuhkan validasi dan pencetakan struk.
Ini menunjukkan bagaimana Java memungkinkan multiple inheritance melalui interface.

3. Penerapan Polimorfisme Saat Cast ke Interface
Objek bertipe Pembayaran bisa diperlakukan sebagai Receiptable, menunjukkan polimorfisme yang lebih luas dibanding minggu sebelumnya.

Minggu lalu lebih fokus pada inheritance biasa, tanpa abstract class dan interface. Minggu ini lebih menekankan desain arsitektur OOP yang lebih profesional: pemisahan tanggung jawab, kontrak fungsi melalui interface, dan generalisasi lewat abstract class.

- Kendala yang dihadapi dan cara mengatasinya. 
 Kendala utama adalah kesalahan penulisan nama package, yaitu menulis pembayaran (huruf kecil) padahal direktori dan nama package yang benar adalah Pembayaran (huruf besar).
Karena Java bersifat case-sensitive, perbedaan ini membuat compiler tidak dapat menemukan package yang dituju dan menyebabkan error seperti "package does not exist".

Cara mengatasinya:

- Mengecek ulang struktur folder pada src.
- Menyamakan nama package di seluruh file Java agar sesuai dengan folder, yaitu package com.upb.agripos.model.Pembayaran;.
- Setelah disesuaikan, program dapat dikompilasi dan dijalankan tanpa error.

---

## Kesimpulan
Secara keseluruhan, program ini menunjukkan bagaimana konsep abstraksi dan implementasi interface bekerja dalam sistem pembayaran sederhana. Kelas abstrak Pembayaran menjadi dasar bagi Cash, EWallet, dan TransferBank, sementara interface Validatable dan Receiptable memberikan kemampuan tambahan seperti validasi dan pencetakan struk. Pendekatan ini berbeda dengan minggu sebelumnya yang lebih fokus pada inheritance dasar, sedangkan minggu ini menekankan multiple inheritance via interface serta pemisahan perilaku melalui kontrak yang lebih jelas. Kendala utama yang muncul adalah kesalahan penulisan nama package—seharusnya “Pembayaran” tetapi tertulis “pembayaran”—yang menyebabkan error package not found, dan dapat diatasi dengan menyamakan nama folder serta deklarasi package dengan tepat.

---

## Quiz
1. Jelaskan perbedaan konsep dan penggunaan **abstract class** dan **interface**  
   **Jawaban:** 
   Abstract class digunakan ketika sebuah kelompok objek memiliki kesamaan identitas dan perilaku dasar, sehingga bisa berbagi properti dan metode bawaan (fields, constructor, dan metode konkret). Abstract class juga dapat memiliki metode abstrak yang wajib dioverride oleh subclass. Sementara itu, interface digunakan sebagai kontrak perilaku yang harus dipatuhi oleh kelas mana pun yang mengimplementasikannya. Interface tidak menyimpan keadaan (state) dan fokus pada apa yang harus dilakukan, bukan bagaimana melakukannya. Singkatnya, abstract class = dasar kerangka objek, interface = kontrak kemampuan tambahan. 

2. Mengapa **multiple inheritance** lebih aman dilakukan dengan interface pada Java?  
   **Jawaban:** 
   Multiple inheritance melalui interface lebih aman karena interface tidak membawa state (variabel instance) sehingga tidak menimbulkan konflik pewarisan seperti “diamond problem”. Kelas yang mengimplementasikan banyak interface hanya mewarisi perilaku abstrak, bukan implementasi penuh, sehingga Java terhindar dari ambiguitas pewarisan dan tetap menjaga struktur desain kode tetap jelas, modular, dan mudah dikelola.  

3. Pada contoh Agri-POS, bagian mana yang **paling tepat** menjadi abstract class dan mana yang menjadi interface? Jelaskan alasannya.   
   **Jawaban:**
   Dalam Agri-POS, kelas Pembayaran paling tepat menjadi abstract class karena semua jenis pembayaran (Cash, EWallet, TransferBank) memiliki struktur data dan perilaku dasar yang sama, yaitu memiliki nomor invoice, jumlah pembayaran, dan metode untuk memproses pembayaran. Di sisi lain, Validatable dan Receiptable paling tepat dijadikan interface karena tidak semua metode pembayaran wajib memiliki kemampuan yang sama, sehingga validasi dan pencetakan struk dapat ditambahkan sebagai kemampuan opsional. Dengan kata lain, abstract class untuk fondasi sistem pembayaran, interface untuk perilaku tambahan yang fleksibel dan modular.  
