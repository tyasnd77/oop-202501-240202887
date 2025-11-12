# Laporan Praktikum Minggu 4
Topik: Polymorphism (Info Produk)

## Identitas
- Nama  : Tyas Nurshika Damaia
- NIM   : 240202887
- Kelas : 3IKRB

---

## Tujuan
- Mahasiswa mampu **menjelaskan konsep polymorphism** dalam OOP.  
- Mahasiswa mampu **membedakan method overloading dan overriding**.  
- Mahasiswa mampu **mengimplementasikan polymorphism (overriding, overloading, dynamic binding)** dalam program.  
- Mahasiswa mampu **menganalisis contoh kasus polymorphism** pada sistem nyata (Agri-POS).

---

## Dasar Teori
Polymorphism berarti “banyak bentuk” dan memungkinkan objek yang berbeda merespons panggilan method yang sama dengan cara yang berbeda.  
1. **Overloading** → mendefinisikan method dengan nama sama tetapi parameter berbeda.  
2. **Overriding** → subclass mengganti implementasi method dari superclass.  
3. **Dynamic Binding** → pemanggilan method ditentukan saat runtime, bukan compile time.  

Dalam konteks Agri-POS, misalnya:  
- Method `getInfo()` pada `Produk` dioverride oleh `Benih`, `Pupuk`, `AlatPertanian` untuk menampilkan detail spesifik.  
- Method `tambahStok()` bisa dibuat overload dengan parameter berbeda (int, double).  
---

## Langkah Praktikum
1. **Overloading**  
   - Tambahkan method `tambahStok(int jumlah)` dan `tambahStok(double jumlah)` pada class `Produk`.  

2. **Overriding**  
   - Tambahkan method `getInfo()` pada superclass `Produk`.  
   - Override method `getInfo()` pada subclass `Benih`, `Pupuk`, dan `AlatPertanian`.  

3. **Dynamic Binding**  
   - Buat array `Produk[] daftarProduk` yang berisi objek `Benih`, `Pupuk`, dan `AlatPertanian`.  
   - Loop array tersebut dan panggil `getInfo()`. Perhatikan bagaimana Java memanggil method sesuai jenis objek aktual.  

4. **Main Class**  
   - Buat `MainPolymorphism.java` untuk mendemonstrasikan overloading, overriding, dan dynamic binding.  

5. **CreditBy**  
   - Tetap panggil `CreditBy.print("<NIM>", "<Nama>")`.  

6. **Commit dan Push**  
   - Commit dengan pesan: `week4-polymorphism`.

---

## Kode Program
(Tuliskan kode utama yang dibuat, contoh:  

```java
// Contoh
Produk p1 = new Produk("BNH-001", "Benih Padi", 25000, 100);
System.out.println(p1.getNama());
```
)
---
## Contoh Implementasi Program

### Produk.java (Overloading & getInfo default)
```java
package com.upb.agripos.model;

public class Produk {
    private String kode;
    private String nama;
    private double harga;
    private int stok;

    public Produk(String kode, String nama, double harga, int stok) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    public void tambahStok(int jumlah) {
        this.stok += jumlah;
    }

    public void tambahStok(double jumlah) {
        this.stok += (int) jumlah;
    }

    public String getInfo() {
        return "Produk: " + nama + " (Kode: " + kode + ")";
    }
}
```

### Benih.java (Overriding)
```java
package com.upb.agripos.model;

public class Benih extends Produk {
    private String varietas;

    public Benih(String kode, String nama, double harga, int stok, String varietas) {
        super(kode, nama, harga, stok);
        this.varietas = varietas;
    }

    @Override
    public String getInfo() {
        return "Benih: " + super.getInfo() + ", Varietas: " + varietas;
    }
}
```

### MainPolymorphism.java
```java
package com.upb.agripos;

import com.upb.agripos.model.*;
import com.upb.agripos.util.CreditBy;

public class MainPolymorphism {
    public static void main(String[] args) {
        Produk[] daftarProduk = {
            new Benih("BNH-001", "Benih Padi IR64", 25000, 100, "IR64"),
            new Pupuk("PPK-101", "Pupuk Urea", 350000, 40, "Urea"),
            new AlatPertanian("ALT-501", "Cangkul Baja", 90000, 15, "Baja")
        };

        for (Produk p : daftarProduk) {
            System.out.println(p.getInfo()); // Dynamic Binding
        }

           CreditBy.print("240202887", "Tyas Nurshika Damaia");
    }
}
```

---


## Hasil Eksekusi
  
![Screenshot hasil](screenshots/OutputMainPolymorphism.png)

---

## Analisis
Pada praktikum minggu ke-4, program berjalan dengan membuat array berisi objek dari berbagai subclass (Benih, Pupuk, AlatPertanian) yang semuanya diturunkan dari superclass Produk. Ketika perulangan dilakukan dan method getInfo() dipanggil, Java secara otomatis mengeksekusi method sesuai tipe objek aktual, bukan tipe referensinya — inilah contoh dynamic binding yang menunjukkan penerapan polymorphism. Selain itu, method tambahStok() yang dibuat dengan parameter berbeda menunjukkan overloading, sedangkan getInfo() yang ditulis ulang pada subclass menunjukkan overriding.

Perbedaan pendekatan minggu ini dengan minggu sebelumnya (Inheritance) terletak pada tujuan dan fleksibilitas penggunaannya. Jika inheritance hanya menekankan pewarisan atribut dan method dari superclass ke subclass untuk menghindari duplikasi kode, maka polymorphism berfokus pada perilaku berbeda dari method yang sama. Dengan polymorphism, program menjadi lebih dinamis karena satu referensi superclass dapat mewakili banyak objek subclass sekaligus.

Kendala yang dihadapi adalah memahami perbedaan antara overloading dan overriding yang sekilas mirip, serta cara kerja dynamic binding yang baru terasa saat runtime. Kendala tersebut diatasi dengan mempelajari ulang alur eksekusi program, menambahkan System.out.println() untuk melihat hasil tiap objek, dan membandingkan output antar subclass untuk memahami konsep polymorphism secara nyata.

---

## Kesimpulan
Dari praktikum minggu ke-4 tentang Polymorphism (Info Produk), dapat disimpulkan bahwa konsep polymorphism memungkinkan suatu method yang sama menghasilkan perilaku berbeda tergantung pada objek yang memanggilnya. Melalui penerapan overloading, program dapat memiliki beberapa versi method dengan nama sama tetapi parameter berbeda; sementara overriding memungkinkan subclass menyesuaikan perilaku method dari superclass agar lebih spesifik. Selain itu, konsep dynamic binding membuat Java secara otomatis memilih method yang sesuai dengan tipe objek aktual pada saat runtime. Dengan penerapan ketiga konsep tersebut pada sistem Agri-POS, program menjadi lebih fleksibel, mudah dikembangkan, serta mencerminkan prinsip utama pemrograman berorientasi objek yang efisien dan terstruktur.

---

## Quiz
1. Apa perbedaan overloading dan overriding?  
   **Jawaban:**
Overloading adalah penggunaan beberapa method dengan nama yang sama tetapi parameter berbeda (tipe atau jumlahnya berbeda) dalam satu kelas, sedangkan overriding adalah penulisan ulang method dari superclass di subclass dengan nama, parameter, dan tipe return yang sama untuk mengubah perilakunya

2. Bagaimana Java menentukan method mana yang dipanggil dalam dynamic binding?  
   **Jawaban:**
   Dalam dynamic binding, Java menentukan method mana yang dipanggil berdasarkan tipe objek aktual saat runtime, bukan berdasarkan tipe referensi variabelnya, sehingga method yang dijalankan sesuai dengan implementasi di kelas tempat objek sebenarnya dibuat.

3. Berikan contoh kasus polymorphism dalam sistem POS selain produk pertanian.  
   **Jawaban:**
 Contoh polymorphism dalam sistem POS (Point of Sale) adalah ketika ada kelas induk Pembayaran dengan subclass PembayaranTunai, PembayaranKartu, dan PembayaranEwallet; masing-masing memiliki cara memproses transaksi yang berbeda, tetapi semua dapat dipanggil melalui referensi Pembayaran yang sama menggunakan method prosesPembayaran().
