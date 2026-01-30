package com.upb.agripos.view;

import com.upb.agripos.model.Product;
import com.upb.agripos.model.TransactionHistory;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * View untuk admin (Manajemen Produk dan Laporan).
 */
public class AdminView {
    private VBox root;
    private TabPane tabPane;
    private Label userInfoLabel;

    // Product Management Tab
    private TableView<Product> productTable;
    private TextField kodeField;
    private TextField namaField;
    private ComboBox<String> kategoriField;
    private TextField hargaField;
    private TextField stokField;
    private Button addProductButton;
    private Button updateProductButton;
    private Button deleteProductButton;
    private TextField searchField;
    private ComboBox<String> kategoriFilterCombo;
    
    // Category Management Tab fields
    private TextField newCategoryField;
    private Button addCategoryButton;
    private Button deleteCategoryButton;
    private ComboBox<String> categoryListCombo;

    // User Management Tab fields
    private TextField usernameField;
    private TextField passwordField;
    private ComboBox<String> roleField;
    private TextField namaLengkapField;
    private Button addUserButton;
    private TableView<com.upb.agripos.model.User> userTable;
    private Button editUserButton;
    private Button deleteUserButton;

    // Report Tab fields
    private Label totalPenjualanLabel;
    private Label totalTransaksiLabel;
    private TableView<TransactionHistory> reportTable;
    private javafx.scene.control.DatePicker reportFromDate;
    private javafx.scene.control.DatePicker reportToDate;
    private Button refreshReportButton;
    private Button downloadReportButton;
    private Button applyReportFilterButton;

    // Transaction History Tab fields
    private TableView<TransactionHistory> historyTable;
    private Button refreshHistoryButton;
    private ComboBox<String> historyMethodFilter;
    private ComboBox<String> historyStatusFilter;
    private Button historyApplyFilterBtn;

    private Button logoutButton;

    public Scene buildUI(String userName) {
        root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #f3f4f6;");

        // Header
        HBox headerBox = createHeader(userName);

        // Tab Pane untuk Product Management, User Management, Report, dan Transaction History
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab productTab = new Tab("Manajemen Produk", createProductManagementPane());
        Tab categoryTab = new Tab("Manajemen Kategori", createCategoryManagementPane());
        Tab userTab = new Tab("Manajemen User", createUserManagementPane());
        Tab reportTab = new Tab("Laporan Penjualan", createReportPane());
        Tab historyTab = new Tab("Riwayat Transaksi", createHistoryPane());

        tabPane.getTabs().addAll(productTab, categoryTab, userTab, reportTab, historyTab);

        root.getChildren().addAll(headerBox, new Separator(), tabPane);
        VBox.setVgrow(tabPane, Priority.ALWAYS);

        return new Scene(root, 1400, 800);
    }

    private HBox createHeader(String userName) {
        HBox headerBox = new HBox(20);
        headerBox.setPadding(new Insets(15));
        headerBox.setStyle("-fx-background-color: #059669;");

        Label titleLabel = new Label("AGRI-POS - Admin");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: white;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        userInfoLabel = new Label("User: " + userName);
        userInfoLabel.setStyle("-fx-font-size: 12; -fx-text-fill: white;");

        logoutButton = new Button("🚪 Logout");
        logoutButton.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-padding: 8; -fx-font-weight: bold;");
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white; -fx-padding: 8; -fx-font-weight: bold;"));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-padding: 8; -fx-font-weight: bold;"));

        headerBox.getChildren().addAll(titleLabel, spacer, userInfoLabel, logoutButton);
        return headerBox;
    }

    private VBox createCategoryManagementPane() {
        VBox mainPane = new VBox(15);
        mainPane.setPadding(new Insets(15));
        mainPane.setStyle("-fx-background-color: #f3f4f6;");

        // Add Category Section
        VBox addCategoryPane = new VBox(10);
        addCategoryPane.setPadding(new Insets(15));
        addCategoryPane.setStyle("-fx-border-color: #e5e7eb; -fx-border-radius: 5; -fx-background-color: white;");

        Label addTitle = new Label("Tambah Kategori Baru:");
        addTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-text-fill: #1f2937;");

        HBox addBox = new HBox(10);
        Label categoryLabel = new Label("Nama Kategori:");
        categoryLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #1f2937; -fx-min-width: 120;");
        
        newCategoryField = new TextField();
        newCategoryField.setPromptText("Contoh: Pupuk, Alat-alat, Bibit");
        newCategoryField.setStyle("-fx-padding: 8;");
        
        addCategoryButton = new Button("➕ Tambah Kategori");
        addCategoryButton.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-padding: 10; -fx-font-weight: bold;");

        addBox.getChildren().addAll(categoryLabel, newCategoryField, addCategoryButton);
        HBox.setHgrow(newCategoryField, Priority.ALWAYS);

        addCategoryPane.getChildren().addAll(addTitle, addBox);

        // Delete Category Section
        VBox deleteCategoryPane = new VBox(10);
        deleteCategoryPane.setPadding(new Insets(15));
        deleteCategoryPane.setStyle("-fx-border-color: #e5e7eb; -fx-border-radius: 5; -fx-background-color: white;");

        Label deleteTitle = new Label("Hapus Kategori:");
        deleteTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-text-fill: #1f2937;");

        HBox deleteBox = new HBox(10);
        Label selectLabel = new Label("Pilih Kategori:");
        selectLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #1f2937; -fx-min-width: 120;");
        
        categoryListCombo = new ComboBox<>();
        categoryListCombo.setPrefWidth(150);
        
        deleteCategoryButton = new Button("🗑️  Hapus Kategori");
        deleteCategoryButton.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-padding: 10; -fx-font-weight: bold;");

        deleteBox.getChildren().addAll(selectLabel, categoryListCombo, deleteCategoryButton);

        deleteCategoryPane.getChildren().addAll(deleteTitle, deleteBox);

        mainPane.getChildren().addAll(addCategoryPane, deleteCategoryPane);
        return mainPane;
    }

    private VBox createUserManagementPane() {
        VBox mainPane = new VBox(15);
        mainPane.setPadding(new Insets(15));
        mainPane.setStyle("-fx-background-color: #f3f4f6;");

        // Form untuk menambah/edit user
        VBox formPane = new VBox(10);
        formPane.setPadding(new Insets(15));
        formPane.setStyle("-fx-border-color: #e5e7eb; -fx-border-radius: 5; -fx-background-color: white;");

        Label formTitle = new Label("Tambah/Edit User:");
        formTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-text-fill: #1f2937;");

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-padding: 8;");

        passwordField = new TextField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-padding: 8;");

        roleField = new ComboBox<>();
        roleField.getItems().addAll("KASIR", "ADMIN");
        roleField.setValue("KASIR");
        roleField.setStyle("-fx-padding: 8;");

        namaLengkapField = new TextField();
        namaLengkapField.setPromptText("Nama Lengkap");
        namaLengkapField.setStyle("-fx-padding: 8;");

        addUserButton = new Button("✅  Tambah User");
        addUserButton.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-padding: 10; -fx-font-weight: bold;");

        editUserButton = new Button("✏️  Edit User");
        editUserButton.setStyle("-fx-background-color: #f59e0b; -fx-text-fill: white; -fx-padding: 10; -fx-font-weight: bold;");

        deleteUserButton = new Button("🗑️  Hapus User");
        deleteUserButton.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-padding: 10; -fx-font-weight: bold;");

        HBox formGrid = new HBox(15);
        VBox col1 = new VBox(5);
        VBox col2 = new VBox(5);

        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #1f2937;");
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #1f2937;");

        col1.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField);

        Label namaLabel = new Label("Nama Lengkap:");
        namaLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #1f2937;");
        Label roleLabel = new Label("Role:");
        roleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #1f2937;");

        col2.getChildren().addAll(namaLabel, namaLengkapField, roleLabel, roleField);

        formGrid.getChildren().addAll(col1, col2);
        HBox.setHgrow(col1, Priority.ALWAYS);
        HBox.setHgrow(col2, Priority.ALWAYS);

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(addUserButton, editUserButton, deleteUserButton);

        formPane.getChildren().addAll(formTitle, formGrid, buttonBox);

        // User Table
        userTable = new TableView<>();
        setupUserTable();

        Label tableTitle = new Label("Daftar User:");
        tableTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-text-fill: #1f2937;");

        mainPane.getChildren().addAll(formPane, tableTitle, userTable);
        VBox.setVgrow(userTable, Priority.ALWAYS);
        return mainPane;
    }

    private void setupUserTable() {
        TableColumn<com.upb.agripos.model.User, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(
                cellData.getValue().getId()).asObject());
        idCol.setPrefWidth(50);

        TableColumn<com.upb.agripos.model.User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getUsername()));
        usernameCol.setPrefWidth(120);

        TableColumn<com.upb.agripos.model.User, String> namaCol = new TableColumn<>("Nama Lengkap");
        namaCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getNamaLengkap()));
        namaCol.setPrefWidth(150);

        TableColumn<com.upb.agripos.model.User, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getRole()));
        roleCol.setPrefWidth(100);

        userTable.getColumns().addAll(idCol, usernameCol, namaCol, roleCol);
        userTable.setStyle("-fx-font-size: 11;");
    }

    private VBox createProductManagementPane() {
        VBox mainPane = new VBox(15);
        mainPane.setPadding(new Insets(15));
        mainPane.setStyle("-fx-background-color: #f3f4f6;");

        // Search Bar
        HBox searchBox = createSearchBox();

        // Product Form
        VBox formPane = createProductFormPane();

        // Product Table
        productTable = new TableView<>();
        setupProductTable();

        mainPane.getChildren().addAll(
                searchBox,
                new Separator(),
                formPane,
                new Separator(),
                new Label("Daftar Produk:"),
                productTable
        );
        VBox.setVgrow(productTable, Priority.ALWAYS);

        return mainPane;
    }

    private HBox createSearchBox() {
        HBox searchBox = new HBox(10);
        searchBox.setPadding(new Insets(10));
        searchBox.setStyle("-fx-border-color: #e5e7eb; -fx-border-radius: 5; -fx-background-color: white;");

        Label searchLabel = new Label("Cari Produk:");
        searchField = new TextField();
        searchField.setPromptText("Cari berdasarkan nama...");
        Button searchButton = new Button("Cari");
        searchButton.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-padding: 8;");

        Label kategoriLabel = new Label("Filter Kategori:");
        kategoriLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #4b5563;");
        kategoriFilterCombo = new ComboBox<>();
        kategoriFilterCombo.getItems().add("Semua");
        kategoriFilterCombo.setValue("Semua");
        kategoriFilterCombo.setPrefWidth(120);

        Button resetButton = new Button("Reset");
        resetButton.setStyle("-fx-background-color: #6b7280; -fx-text-fill: white; -fx-padding: 8;");

        searchBox.getChildren().addAll(searchLabel, searchField, searchButton, kategoriLabel, kategoriFilterCombo, resetButton);
        HBox.setHgrow(searchField, Priority.ALWAYS);

        return searchBox;
    }

    private VBox createProductFormPane() {
        VBox formPane = new VBox(10);
        formPane.setPadding(new Insets(15));
        formPane.setStyle("-fx-border-color: #e5e7eb; -fx-border-radius: 5; -fx-background-color: white;");

        // Form fields
        Label kodeLabel = new Label("Kode Produk:");
        kodeField = new TextField();
        kodeField.setPromptText("Masukkan kode produk");

        Label namaLabel = new Label("Nama Produk:");
        namaField = new TextField();
        namaField.setPromptText("Masukkan nama produk");

        Label kategoriLabel = new Label("Kategori:");
        kategoriField = new ComboBox<>();
        kategoriField.setPrefWidth(150);
        kategoriField.setEditable(false);

        Label hargaLabel = new Label("Harga:");
        hargaField = new TextField();
        hargaField.setPromptText("Masukkan harga");

        Label stokLabel = new Label("Stok:");
        stokField = new TextField();
        stokField.setPromptText("Masukkan jumlah stok");

        // Buttons
        addProductButton = new Button("Tambah Produk");
        addProductButton.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-padding: 10;");

        updateProductButton = new Button("Update Produk");
        updateProductButton.setStyle("-fx-background-color: #f59e0b; -fx-text-fill: white; -fx-padding: 10;");

        deleteProductButton = new Button("Hapus Produk");
        deleteProductButton.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-padding: 10;");

        // Layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(10);

        gridPane.add(kodeLabel, 0, 0);
        gridPane.add(kodeField, 1, 0);
        gridPane.add(namaLabel, 0, 1);
        gridPane.add(namaField, 1, 1);
        gridPane.add(kategoriLabel, 2, 0);
        gridPane.add(kategoriField, 3, 0);
        gridPane.add(hargaLabel, 2, 1);
        gridPane.add(hargaField, 3, 1);
        gridPane.add(stokLabel, 4, 0);
        gridPane.add(stokField, 5, 0);

        GridPane.setHgrow(kodeField, Priority.ALWAYS);
        GridPane.setHgrow(namaField, Priority.ALWAYS);
        GridPane.setHgrow(kategoriField, Priority.ALWAYS);
        GridPane.setHgrow(hargaField, Priority.ALWAYS);
        GridPane.setHgrow(stokField, Priority.ALWAYS);

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(addProductButton, updateProductButton, deleteProductButton);

        formPane.getChildren().addAll(gridPane, buttonBox);

        return formPane;
    }

    private void setupProductTable() {
        TableColumn<Product, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));
        idCol.setPrefWidth(50);

        TableColumn<Product, String> kodeCol = new TableColumn<>("Kode");
        kodeCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getKode()));
        kodeCol.setPrefWidth(100);

        TableColumn<Product, String> namaCol = new TableColumn<>("Nama Produk");
        namaCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNama()));
        namaCol.setPrefWidth(200);

        TableColumn<Product, String> kategoriCol = new TableColumn<>("Kategori");
        kategoriCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getKategori()));
        kategoriCol.setPrefWidth(120);

        TableColumn<Product, Double> hargaCol = new TableColumn<>("Harga");
        hargaCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getHarga()));
        hargaCol.setPrefWidth(100);

        TableColumn<Product, Integer> stokCol = new TableColumn<>("Stok");
        stokCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getStok()));
        stokCol.setPrefWidth(80);

        productTable.getColumns().addAll(idCol, kodeCol, namaCol, kategoriCol, hargaCol, stokCol);
    }

    private VBox createReportPane() {
        VBox reportPane = new VBox(15);
        reportPane.setPadding(new Insets(15));
        reportPane.setStyle("-fx-background-color: #f3f4f6;");

        // Statistics Cards
        HBox statsBox = createStatsBox();

        // Filter Section
        HBox filterBox = new HBox(15);
        filterBox.setPadding(new Insets(10));
        filterBox.setStyle("-fx-background-color: white; -fx-border-color: #e5e7eb; -fx-border-radius: 5;");
        
        Label fromLabel = new Label("Dari Tanggal:");
        fromLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #4b5563;");
        reportFromDate = new javafx.scene.control.DatePicker();
        reportFromDate.setPrefWidth(120);
        
        Label toLabel = new Label("Sampai Tanggal:");
        toLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #4b5563;");
        reportToDate = new javafx.scene.control.DatePicker();
        reportToDate.setPrefWidth(120);
        
        applyReportFilterButton = new Button("Apply Filter");
        applyReportFilterButton.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-padding: 6; -fx-font-size: 10;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        filterBox.getChildren().addAll(fromLabel, reportFromDate, toLabel, reportToDate, applyReportFilterButton, spacer);

        // Report Table Header
        Label reportLabel = new Label("Detail Laporan Transaksi:");
        reportLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        // Report Table
        reportTable = new TableView<>();
        setupReportTable();

        // Buttons Box
        HBox buttonsBox = new HBox(10);
        buttonsBox.setPadding(new Insets(10));
        
        refreshReportButton = new Button("🔄 Refresh Laporan");
        refreshReportButton.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-padding: 10; -fx-font-weight: bold;");
        
        downloadReportButton = new Button("📥 Download ke Excel");
        downloadReportButton.setStyle("-fx-background-color: #3b82f6; -fx-text-fill: white; -fx-padding: 10; -fx-font-weight: bold;");
        
        buttonsBox.getChildren().addAll(refreshReportButton, downloadReportButton);

        reportPane.getChildren().addAll(statsBox, filterBox, reportLabel, reportTable, buttonsBox);
        VBox.setVgrow(reportTable, Priority.ALWAYS);

        return reportPane;
    }

    private void setupReportTable() {
        TableColumn<TransactionHistory, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));
        idCol.setPrefWidth(50);

        TableColumn<TransactionHistory, String> tanggalCol = new TableColumn<>("Tanggal");
        tanggalCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTanggalFormatted()));
        tanggalCol.setPrefWidth(150);

        TableColumn<TransactionHistory, String> kasirCol = new TableColumn<>("Kasir");
        kasirCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNamaKasir()));
        kasirCol.setPrefWidth(130);

        TableColumn<TransactionHistory, Double> totalCol = new TableColumn<>("Total Harga");
        totalCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getTotalHarga()));
        totalCol.setPrefWidth(120);

        TableColumn<TransactionHistory, Double> bayarCol = new TableColumn<>("Jumlah Bayar");
        bayarCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getJumlahBayar()));
        bayarCol.setPrefWidth(120);

        TableColumn<TransactionHistory, Double> kembalianCol = new TableColumn<>("Kembalian");
        kembalianCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getKembalian()));
        kembalianCol.setPrefWidth(100);

        TableColumn<TransactionHistory, String> methodCol = new TableColumn<>("Metode");
        methodCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMetodePayment()));
        methodCol.setPrefWidth(100);

        TableColumn<TransactionHistory, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatus()));
        statusCol.setPrefWidth(80);

        reportTable.getColumns().addAll(idCol, tanggalCol, kasirCol, totalCol, bayarCol, kembalianCol, methodCol, statusCol);
    }

    private HBox createStatsBox() {
        HBox statsBox = new HBox(15);
        statsBox.setPadding(new Insets(15));

        // Card 1: Total Penjualan
        VBox card1 = createStatCard("Total Penjualan", "Rp 0");
        totalPenjualanLabel = (Label) card1.getChildren().get(1);

        // Card 2: Total Transaksi
        VBox card2 = createStatCard("Total Transaksi", "0");
        totalTransaksiLabel = (Label) card2.getChildren().get(1);

        statsBox.getChildren().addAll(card1, card2);
        return statsBox;
    }

    private VBox createStatCard(String title, String value) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-border-color: #e5e7eb; -fx-border-radius: 5; -fx-background-color: white;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-text-fill: #6b7280;");

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #10b981;");

        card.getChildren().addAll(titleLabel, valueLabel);
        return card;
    }

    private VBox createHistoryPane() {
        VBox historyPane = new VBox(10);
        historyPane.setPadding(new Insets(15));
        historyPane.setStyle("-fx-background-color: white; -fx-border-color: #e5e7eb; -fx-border-radius: 5;");

        // Header untuk history
        HBox historyHeader = new HBox(10);
        Label historyLabel = new Label("Riwayat Transaksi Semua Kasir");
        historyLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-text-fill: #1f2937;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        refreshHistoryButton = new Button("🔄 Refresh");
        refreshHistoryButton.setStyle("-fx-background-color: #0ea5e9; -fx-text-fill: white; -fx-padding: 8; -fx-font-weight: bold;");

        historyHeader.getChildren().addAll(historyLabel, spacer, refreshHistoryButton);

        // Filter section
        HBox filterBox = new HBox(15);
        filterBox.setPadding(new Insets(10));
        filterBox.setStyle("-fx-background-color: #f9fafb; -fx-border-color: #e5e7eb; -fx-border-radius: 3;");
        
        Label methodLabel = new Label("Filter Metode:");
        methodLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #4b5563;");
        historyMethodFilter = new ComboBox<>();
        historyMethodFilter.getItems().addAll("Semua", "TUNAI", "EWALLET");
        historyMethodFilter.setValue("Semua");
        historyMethodFilter.setPrefWidth(100);

        Label statusLabel = new Label("Filter Status:");
        statusLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #4b5563;");
        historyStatusFilter = new ComboBox<>();
        historyStatusFilter.getItems().addAll("Semua", "Sukses", "Pending", "Dibatalkan");
        historyStatusFilter.setValue("Semua");
        historyStatusFilter.setPrefWidth(100);

        historyApplyFilterBtn = new Button("Apply Filter");
        historyApplyFilterBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-padding: 6; -fx-font-size: 10;");
        
        filterBox.getChildren().addAll(methodLabel, historyMethodFilter, statusLabel, historyStatusFilter, historyApplyFilterBtn);

        // History Table
        historyTable = new TableView<>();
        setupHistoryTable();

        // Pagination
        javafx.scene.control.Pagination pagination = new javafx.scene.control.Pagination(1, 0);
        pagination.setStyle("-fx-font-size: 10;");
        pagination.setPrefHeight(40);

        historyPane.getChildren().addAll(historyHeader, filterBox, historyTable, pagination);
        VBox.setVgrow(historyTable, Priority.ALWAYS);

        return historyPane;
    }

    private void setupHistoryTable() {
        TableColumn<TransactionHistory, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));
        idCol.setPrefWidth(50);

        TableColumn<TransactionHistory, String> tanggalCol = new TableColumn<>("Tanggal");
        tanggalCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTanggalFormatted()));
        tanggalCol.setPrefWidth(150);

        TableColumn<TransactionHistory, String> kasirCol = new TableColumn<>("Kasir");
        kasirCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNamaKasir()));
        kasirCol.setPrefWidth(120);

        TableColumn<TransactionHistory, Double> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getTotalHarga()));
        totalCol.setPrefWidth(100);

        TableColumn<TransactionHistory, String> methodCol = new TableColumn<>("Metode");
        methodCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMetodePayment()));
        methodCol.setPrefWidth(80);

        TableColumn<TransactionHistory, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatus()));
        statusCol.setPrefWidth(80);

        historyTable.getColumns().addAll(idCol, tanggalCol, kasirCol, totalCol, methodCol, statusCol);
    }

    // Getter methods
    public TextField getKodeField() {
        return kodeField;
    }

    public TextField getNamaField() {
        return namaField;
    }

    public ComboBox<String> getKategoriField() {
        return kategoriField;
    }

    public ComboBox<String> getKategoriFilterCombo() {
        return kategoriFilterCombo;
    }

    public TextField getNewCategoryField() {
        return newCategoryField;
    }

    public Button getAddCategoryButton() {
        return addCategoryButton;
    }

    public Button getDeleteCategoryButton() {
        return deleteCategoryButton;
    }

    public ComboBox<String> getCategoryListCombo() {
        return categoryListCombo;
    }

    public TextField getHargaField() {
        return hargaField;
    }

    public TextField getStokField() {
        return stokField;
    }

    public Button getAddProductButton() {
        return addProductButton;
    }

    public Button getUpdateProductButton() {
        return updateProductButton;
    }

    public Button getDeleteProductButton() {
        return deleteProductButton;
    }

    public TableView<Product> getProductTable() {
        return productTable;
    }

    public Product getSelectedProduct() {
        return productTable.getSelectionModel().getSelectedItem();
    }

    public void setProducts(ObservableList<Product> products) {
        productTable.setItems(products);
    }

    public void clearForm() {
        kodeField.clear();
        namaField.clear();
        kategoriField.setValue(null);
        hargaField.clear();
        stokField.clear();
    }

    public void setFormData(Product product) {
        if (product != null) {
            kodeField.setText(product.getKode());
            namaField.setText(product.getNama());
            kategoriField.setValue(product.getKategori());
            hargaField.setText(String.valueOf(product.getHarga()));
            stokField.setText(String.valueOf(product.getStok()));
        }
    }

    public void updateStatistics(double totalPenjualan, int totalTransaksi) {
        totalPenjualanLabel.setText(String.format("Rp %.0f", totalPenjualan));
        totalTransaksiLabel.setText(String.valueOf(totalTransaksi));
    }

    public TextField getSearchField() {
        return searchField;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public ComboBox<String> getRoleField() {
        return roleField;
    }

    public TextField getNamaLengkapField() {
        return namaLengkapField;
    }

    public Button getAddUserButton() {
        return addUserButton;
    }

    public Button getEditUserButton() {
        return editUserButton;
    }

    public Button getDeleteUserButton() {
        return deleteUserButton;
    }

    public TableView<com.upb.agripos.model.User> getUserTable() {
        return userTable;
    }

    public void setUsers(ObservableList<com.upb.agripos.model.User> users) {
        userTable.setItems(users);
    }

    public com.upb.agripos.model.User getSelectedUser() {
        return userTable.getSelectionModel().getSelectedItem();
    }

    public void clearUserForm() {
        usernameField.clear();
        passwordField.clear();
        namaLengkapField.clear();
        roleField.setValue("KASIR");
    }

    public Button getLogoutButton() {
        return logoutButton;
    }

    public Label getTotalPenjualanLabel() {
        return totalPenjualanLabel;
    }

    public Label getTotalTransaksiLabel() {
        return totalTransaksiLabel;
    }



    public Button getRefreshReportButton() {
        return refreshReportButton;
    }

    public Button getDownloadReportButton() {
        return downloadReportButton;
    }

    public Button getApplyReportFilterButton() {
        return applyReportFilterButton;
    }

    public javafx.scene.control.DatePicker getReportFromDate() {
        return reportFromDate;
    }

    public javafx.scene.control.DatePicker getReportToDate() {
        return reportToDate;
    }

    public TableView<TransactionHistory> getReportTable() {
        return reportTable;
    }

    public void setReportItems(ObservableList<TransactionHistory> items) {
        if (reportTable != null) {
            reportTable.setItems(items);
            reportTable.refresh();
        }
    }

    public void updateReportStatistics(double totalPenjualan, int totalTransaksi) {
        totalPenjualanLabel.setText(String.format("Rp %.0f", totalPenjualan));
        totalTransaksiLabel.setText(String.valueOf(totalTransaksi));
    }

    public TableView<TransactionHistory> getHistoryTable() {
        return historyTable;
    }

    public Button getRefreshHistoryButton() {
        return refreshHistoryButton;
    }

    public void setHistoryItems(ObservableList<TransactionHistory> items) {
        if (historyTable != null) {
            historyTable.setItems(items);
            historyTable.refresh();
        }
    }

    public ComboBox<String> getHistoryMethodFilter() {
        return historyMethodFilter;
    }

    public ComboBox<String> getHistoryStatusFilter() {
        return historyStatusFilter;
    }

    public Button getHistoryApplyFilterBtn() {
        return historyApplyFilterBtn;
    }
}
