# Laporan Praktikum Minggu 13
Topik: GUI Lanjutan JavaFX (TableView dan Lambda Expression)

## Identitas
- Nama  : Tyas Nurshika Damaia
- NIM   : 240202887
- Kelas : 3IKRB

---

## Tujuan

1. Menampilkan data menggunakan TableView JavaFX.
2. Mengintegrasikan koleksi objek dengan GUI.
3. Menggunakan lambda expression untuk event handling.
4. Menghubungkan GUI dengan DAO secara penuh.
5. Membangun antarmuka GUI Agri-POS yang lebih interaktif.

---

## Dasar Teori
Praktikum ini adalah LANJUTAN LANGSUNG dari:
- Pertemuan 12 (GUI Dasar JavaFX)
- Pertemuan 11 (DAO + JDBC)
- Pertemuan 7 (Collections & Lambda)

---

## Langkah Praktikum

1. Lanjutkan project week12, jangan buat project baru.
2. Ganti daftar produk menjadi TableView<Product> (kode, nama, harga, stok).
3. Ambil data dari DB melalui ProductService → ProductDAO.findAll() (loadData()).
4. Tambahkan tombol Tambah & Hapus Produk di GUI.
5. Gunakan lambda expression untuk event tombol (hapus → reload TableView).
6. Akses DB hanya lewat Service/DAO (View tidak langsung ke DAO).
7. Ambil screenshot GUI, isi laporan_week13.md, lalu commit sesuai aturan.

---

## Kode Program

1️⃣ Product.java (Model)
```java
package com.upb.agripos.model;

public class Product {
    private String code;
    private String name;
    private double price;
    private int stock;

    public Product(String code, String name, double price, int stock) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
```

2️⃣ ProductDAO.java
```java
package com.upb.agripos.dao;

import java.util.List;

import com.upb.agripos.model.Product;

public interface ProductDAO {
    void insert(Product product) throws Exception;
    void delete(String code) throws Exception;
    List<Product> findAll() throws Exception;
}
```

3️⃣ ProductService.java
```java
package com.upb.agripos.service;

import java.util.List;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.model.Product;

public class ProductService {
    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public void addProduct(Product p) throws Exception {
        // Validasi sederhana bisa ditambahkan di sini
        if (p.getPrice() < 0) throw new Exception("Harga tidak boleh negatif");
        productDAO.insert(p);
    }

    public void deleteProduct(String code) throws Exception {
        productDAO.delete(code);
    }

    public List<Product> getAllProducts() throws Exception {
        return productDAO.findAll();
    }
}
```

4️⃣ ProductController.java
```java
package com.upb.agripos.controller;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.view.ProductTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class ProductController {
    private final ProductService service;
    private final ProductTableView view;

    public ProductController(ProductService service, ProductTableView view) {
        this.service = service;
        this.view = view;
        initController();
        loadData();
    }

    private void initController() {
        // Event Handler: Tambah Produk (Lambda)
        view.getBtnAdd().setOnAction(e -> {
            try {
                Product p = view.getProductFromInput();
                service.addProduct(p);
                loadData();
                view.clearInput();
            } catch (NumberFormatException ex) {
                showAlert("Input Error", "Harga dan Stok harus angka.");
            } catch (Exception ex) {
                showAlert("Error", "Gagal menyimpan: " + ex.getMessage());
            }
        });

        // Event Handler: Hapus Produk (Lambda)
        view.getBtnDelete().setOnAction(e -> {
            Product selected = view.getTable().getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    service.deleteProduct(selected.getCode());
                    loadData();
                } catch (Exception ex) {
                    showAlert("Error", "Gagal menghapus: " + ex.getMessage());
                }
            } else {
                showAlert("Warning", "Pilih produk yang akan dihapus.");
            }
        });
    }

    private void loadData() {
        try {
            ObservableList<Product> data = FXCollections.observableArrayList(service.getAllProducts());
            view.getTable().setItems(data);
        } catch (Exception e) {
            showAlert("Error", "Gagal memuat data: " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
```

5️⃣ ProductTableView.java (View – TableView + Lambda)

```java
package com.upb.agripos.view;

import com.upb.agripos.model.Product;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProductTableView extends VBox {
    private TextField txtCode = new TextField();
    private TextField txtName = new TextField();
    private TextField txtPrice = new TextField();
    private TextField txtStock = new TextField();
    private Button btnAdd = new Button("Tambah Produk");
    private Button btnDelete = new Button("Hapus Produk");
    private TableView<Product> table = new TableView<>();

    public ProductTableView() {
        setSpacing(10);
        setPadding(new Insets(10));

        // Setup Input Form
        txtCode.setPromptText("Kode");
        txtName.setPromptText("Nama Produk");
        txtPrice.setPromptText("Harga");
        txtStock.setPromptText("Stok");

        HBox formBox = new HBox(10, txtCode, txtName, txtPrice, txtStock, btnAdd);
        
        // Setup Table Columns
        TableColumn<Product, String> colCode = new TableColumn<>("Kode");
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));

        TableColumn<Product, String> colName = new TableColumn<>("Nama");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setMinWidth(200);

        TableColumn<Product, Double> colPrice = new TableColumn<>("Harga");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, Integer> colStock = new TableColumn<>("Stok");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        table.getColumns().addAll(colCode, colName, colPrice, colStock);

        // Add components
        getChildren().addAll(new Label("Manajemen Produk Agri-POS"), formBox, table, btnDelete);
    }

    // Getters for Controller
    public Button getBtnAdd() { return btnAdd; }
    public Button getBtnDelete() { return btnDelete; }
    public TableView<Product> getTable() { return table; }

    public Product getProductFromInput() throws NumberFormatException {
        return new Product(
            txtCode.getText(),
            txtName.getText(),
            Double.parseDouble(txtPrice.getText()),
            Integer.parseInt(txtStock.getText())
        );
    }

    public void clearInput() {
        txtCode.clear();
        txtName.clear();
        txtPrice.clear();
        txtStock.clear();
    }
}
```

6️⃣ AppJavaFX.java

```java
package com.upb.agripos;

import java.sql.Connection;
import java.sql.DriverManager;

import com.upb.agripos.controller.ProductController;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.ProductDAOImpl;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.view.ProductTableView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppJavaFX extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // 1. Setup Database Connection
            Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/agripos", "postgres", "tyasnd77"
            );

            // 2. Setup MVC Components
            ProductDAO dao = new ProductDAOImpl(conn);
            ProductService service = new ProductService(dao);
            ProductTableView view = new ProductTableView();
            new ProductController(service, view);

            // 3. Show Scene
            Scene scene = new Scene(view, 800, 600);
            stage.setTitle("Agri-POS Week 13 - Tyas Nurshika Damaia (240202887)");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

---

## Hasil Utama

| Pertemuan | Komponen | Deskripsi |
|-----------|----------|-----------|
| 11 | DAO + JDBC | Koneksi PostgreSQL |
| 11 | DAO + JDBC | Interface ProductDAO |
| 11 | DAO + JDBC | Implementasi CRUD (insert, delete, findAll) |
| 11 | DAO + JDBC | ProductService sebagai business logic layer |
| 12 | GUI JavaFX Dasar | Scene dan Layout (VBox, HBox) |
| 12 | GUI JavaFX Dasar | Form input (TextField, Button) |
| 12 | GUI JavaFX Dasar | Alert untuk notifikasi |
| 12 | GUI JavaFX Dasar | Struktur MVC awal |
| 13 | GUI Lanjutan + TableView + Lambda | TableView dengan kolom dinamis |
| 13 | GUI Lanjutan + TableView + Lambda | ObservableList untuk data binding |
| 13 | GUI Lanjutan + TableView + Lambda | Lambda expression untuk event handler |
| 13 | GUI Lanjutan + TableView + Lambda | Integrasi penuh View-Controller-Service-DAO |
| 13 | GUI Lanjutan + TableView + Lambda | Load dan reload data dari database |

---

## Hasil Eksekusi
  
![Screenshot hasil](screenshots/Hasil_Week13.png)

---

## Analisis

1. Arsitektur MVC + DAO diterapkan dengan konsisten
Pemisahan antara Model, View, Controller, Service, dan DAO sudah jelas. View tidak berinteraksi langsung dengan database sehingga sesuai prinsip SOLID (Dependency Inversion Principle).

2. Penggunaan TableView terintegrasi database
Data pada TableView diambil langsung dari PostgreSQL melalui ProductService dan ProductDAO, bukan data statis atau dummy.

3. Lambda Expression digunakan secara nyata
Event handler tombol Tambah dan Hapus menggunakan lambda expression dan terhubung langsung ke proses CRUD.

4. Alur aplikasi sesuai UML Bab 6
Proses load data dan hapus produk mengikuti urutan View → Controller → Service → DAO → Database.

5. Terdapat validasi dan error handling
Validasi data dilakukan di Service dan kesalahan input maupun database ditangani dengan Alert.

---

## Kesimpulan
Praktikum minggu 13 ini menunjukkan pentingnya penerapan design pattern dalam pengembangan aplikasi. Penggunaan TableView untuk menampilkan data koleksi dari database, penerapan lambda expression untuk event handling yang lebih clean, serta konsistensi arsitektur MVC + DAO membuat kode lebih maintainable dan scalable. Praktikum ini membuktikan bahwa kombinasi GUI modern dengan database dan arsitektur yang baik menghasilkan aplikasi yang solid dan siap production.

---
