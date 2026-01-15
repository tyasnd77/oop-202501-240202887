package com.upb.agripos.view;

import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * View untuk Agri-POS Application
 * Mengikuti pattern dari Bab 12-13 (GUI dengan JavaFX)
 */
public class PosView extends VBox {
    // Product Management Components
    private TextField txtProductCode;
    private TextField txtProductName;
    private TextField txtProductPrice;
    private TextField txtProductStock;
    private Button btnAddProduct;
    private Button btnDeleteProduct;
    private TableView<Product> productTable;

    // Cart Components
    private TextField txtQuantity;
    private Button btnAddToCart;
    private Button btnRemoveFromCart;
    private Button btnClearCart;
    private Button btnCheckout;
    private TableView<CartItem> cartTable;
    private Label lblCartCount;
    private Label lblCartTotal;

    public PosView() {
        initializeView();
    }

    /**
     * Menginisialisasi layout GUI
     */
    private void initializeView() {
        this.setPadding(new Insets(0));
        this.setSpacing(0);
        this.setStyle("-fx-background-color: #f3f4f6;");

        // ===== HEADER =====
        HBox headerBox = createHeader();

        // ===== LAYOUT HORIZONTAL (PRODUK KIRI + KERANJANG KANAN) =====
        HBox mainContent = new HBox(15);
        mainContent.setPadding(new Insets(15));
        mainContent.setStyle("-fx-background-color: #f3f4f6;");
        
        // Kolom Kiri: Produk Management (60% width)
        VBox leftColumn = createProductSection();
        HBox.setHgrow(leftColumn, Priority.ALWAYS);
        
        // Kolom Kanan: Keranjang (40% width)
        VBox rightColumn = createCartSection();
        HBox.setHgrow(rightColumn, Priority.ALWAYS);
        
        mainContent.getChildren().addAll(leftColumn, rightColumn);

        // ===== SUMMARY DAN CHECKOUT =====
        VBox summarySection = createSummarySection();

        // Tambahkan semua section ke view
        this.getChildren().addAll(
            headerBox,
            mainContent,
            summarySection
        );
        VBox.setVgrow(mainContent, Priority.ALWAYS);
    }

    /**
     * Membuat header dengan judul dan informasi
     */
    private HBox createHeader() {
        HBox headerBox = new HBox(20);
        headerBox.setPadding(new Insets(15));
        headerBox.setStyle("-fx-background-color: #059669;");

        Label titleLabel = new Label("AGRI-POS SYSTEM");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: white;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label versionLabel = new Label("v1.0 - Manajemen Produk & Transaksi");
        versionLabel.setStyle("-fx-font-size: 12; -fx-text-fill: white;");

        headerBox.getChildren().addAll(titleLabel, spacer, versionLabel);
        return headerBox;
    }

    /**
     * Membuat section untuk Manajemen Produk
     */
    private VBox createProductSection() {
        VBox section = new VBox(10);
        section.setPadding(new Insets(15));
        section.setStyle("-fx-border-color: #e5e7eb; -fx-border-radius: 5; -fx-background-color: white;");

        Label title = new Label("Manajemen Produk");
        title.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: #1f2937;");

        // Form Input Produk
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setStyle("-fx-background-color: #f9fafb; -fx-padding: 10; -fx-border-radius: 3;");

        Label lblCode = new Label("Kode:");
        lblCode.setStyle("-fx-font-size: 11; -fx-text-fill: #4b5563;");
        txtProductCode = new TextField();
        txtProductCode.setPrefWidth(150);
        txtProductCode.setStyle("-fx-font-size: 11; -fx-padding: 8;");
        form.add(lblCode, 0, 0);
        form.add(txtProductCode, 1, 0);

        Label lblName = new Label("Nama:");
        lblName.setStyle("-fx-font-size: 11; -fx-text-fill: #4b5563;");
        txtProductName = new TextField();
        txtProductName.setPrefWidth(150);
        txtProductName.setStyle("-fx-font-size: 11; -fx-padding: 8;");
        form.add(lblName, 0, 1);
        form.add(txtProductName, 1, 1);

        Label lblPrice = new Label("Harga:");
        lblPrice.setStyle("-fx-font-size: 11; -fx-text-fill: #4b5563;");
        txtProductPrice = new TextField();
        txtProductPrice.setPrefWidth(150);
        txtProductPrice.setStyle("-fx-font-size: 11; -fx-padding: 8;");
        form.add(lblPrice, 0, 2);
        form.add(txtProductPrice, 1, 2);

        Label lblStock = new Label("Stok:");
        lblStock.setStyle("-fx-font-size: 11; -fx-text-fill: #4b5563;");
        txtProductStock = new TextField();
        txtProductStock.setPrefWidth(150);
        txtProductStock.setStyle("-fx-font-size: 11; -fx-padding: 8;");
        form.add(lblStock, 0, 3);
        form.add(txtProductStock, 1, 3);

        // Buttons
        HBox buttonBox = new HBox(10);
        btnAddProduct = new Button("✚ Tambah Produk");
        btnAddProduct.setPrefWidth(120);
        btnAddProduct.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-padding: 8; -fx-font-weight: bold;");
        
        btnDeleteProduct = new Button("🗑️ Hapus Produk");
        btnDeleteProduct.setPrefWidth(120);
        btnDeleteProduct.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-padding: 8; -fx-font-weight: bold;");
        
        buttonBox.getChildren().addAll(btnAddProduct, btnDeleteProduct);

        // Table Produk
        productTable = new TableView<>();
        createProductTableColumns();
        productTable.setPrefHeight(250);

        section.getChildren().addAll(title, form, buttonBox, new Label("Daftar Produk:"), productTable);
        VBox.setVgrow(productTable, Priority.ALWAYS);
        return section;
    }

    /**
     * Setup column untuk Product Table
     */
    private void createProductTableColumns() {
        TableColumn<Product, String> colCode = new TableColumn<>("Kode");
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colCode.setPrefWidth(80);

        TableColumn<Product, String> colName = new TableColumn<>("Nama");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setPrefWidth(120);

        TableColumn<Product, Double> colPrice = new TableColumn<>("Harga");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colPrice.setPrefWidth(80);

        TableColumn<Product, Integer> colStock = new TableColumn<>("Stok");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colStock.setPrefWidth(70);

        productTable.getColumns().addAll(colCode, colName, colPrice, colStock);
    }

    /**
     * Membuat section untuk Keranjang Belanja
     */
    private VBox createCartSection() {
        VBox section = new VBox(10);
        section.setPadding(new Insets(15));
        section.setStyle("-fx-border-color: #e5e7eb; -fx-border-radius: 5; -fx-background-color: white;");

        Label title = new Label("Keranjang Belanja");
        title.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: #1f2937;");

        // Input Quantity with +/- buttons
        HBox quantityBox = new HBox(8);
        quantityBox.setPadding(new Insets(10));
        quantityBox.setStyle("-fx-background-color: #f9fafb; -fx-border-color: #e5e7eb; -fx-border-radius: 5;");

        Button minusBtn = new Button("-");
        minusBtn.setPrefWidth(40);
        minusBtn.setStyle("-fx-font-size: 14; -fx-background-color: #ef4444; -fx-text-fill: white;");

        Label qtyLabel = new Label("Qty:");
        qtyLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #1f2937; -fx-font-size: 11;");
        
        txtQuantity = new TextField();
        txtQuantity.setPromptText("0");
        txtQuantity.setPrefWidth(80);
        txtQuantity.setStyle("-fx-font-size: 11; -fx-padding: 8; -fx-text-alignment: center;");

        Button plusBtn = new Button("+");
        plusBtn.setPrefWidth(40);
        plusBtn.setStyle("-fx-font-size: 14; -fx-background-color: #10b981; -fx-text-fill: white;");

        btnAddToCart = new Button("  Tambah Keranjang");
        btnAddToCart.setStyle("-fx-background-color: #0ea5e9; -fx-text-fill: white; -fx-padding: 10; -fx-font-size: 12; -fx-font-weight: bold;");

        quantityBox.getChildren().addAll(minusBtn, qtyLabel, txtQuantity, plusBtn, btnAddToCart);
        HBox.setHgrow(btnAddToCart, Priority.ALWAYS);

        // Handle +/- buttons
        minusBtn.setOnAction(e -> {
            try {
                int current = Integer.parseInt(txtQuantity.getText());
                if (current > 0) txtQuantity.setText(String.valueOf(current - 1));
            } catch (NumberFormatException ex) {
                txtQuantity.setText("1");
            }
        });

        plusBtn.setOnAction(e -> {
            try {
                int current = Integer.parseInt(txtQuantity.getText().isEmpty() ? "0" : txtQuantity.getText());
                txtQuantity.setText(String.valueOf(current + 1));
            } catch (NumberFormatException ex) {
                txtQuantity.setText("1");
            }
        });

        // Table Keranjang
        cartTable = new TableView<>();
        createCartTableColumns();
        cartTable.setPrefHeight(150);

        // Total Section
        HBox totalBox = new HBox(20);
        totalBox.setPadding(new Insets(10));
        totalBox.setStyle("-fx-background-color: #f0fdf4; -fx-border-color: #10b981; -fx-border-radius: 5;");
        
        lblCartCount = new Label("Total Item: 0");
        lblCartCount.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-text-fill: #1f2937;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        lblCartTotal = new Label("Total: Rp. 0");
        lblCartTotal.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-text-fill: #10b981;");
        
        totalBox.getChildren().addAll(lblCartCount, spacer, lblCartTotal);

        // Buttons
        HBox cartButtonBox = new HBox(10);
        btnRemoveFromCart = new Button("🗑️ Hapus Item");
        btnRemoveFromCart.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-padding: 8; -fx-font-weight: bold;");
        HBox.setHgrow(btnRemoveFromCart, Priority.ALWAYS);
        
        btnClearCart = new Button("🔄 Clear");
        btnClearCart.setStyle("-fx-background-color: #f59e0b; -fx-text-fill: white; -fx-padding: 8; -fx-font-weight: bold;");
        HBox.setHgrow(btnClearCart, Priority.ALWAYS);
        
        cartButtonBox.getChildren().addAll(btnRemoveFromCart, btnClearCart);

        // Checkout Button
        btnCheckout = new Button("✓ CHECKOUT");
        btnCheckout.setPrefHeight(45);
        btnCheckout.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; " +
                "-fx-font-size: 14; -fx-font-weight: bold; -fx-cursor: hand;");
        btnCheckout.setOnMouseEntered(e -> btnCheckout.setStyle(
                "-fx-background-color: #059669; -fx-text-fill: white; -fx-font-size: 14; -fx-font-weight: bold; -fx-cursor: hand;"));
        btnCheckout.setOnMouseExited(e -> btnCheckout.setStyle(
                "-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-size: 14; -fx-font-weight: bold; -fx-cursor: hand;"));

        section.getChildren().addAll(title, quantityBox, new Label("Isi Keranjang:"), cartTable, totalBox, cartButtonBox, btnCheckout);
        VBox.setVgrow(cartTable, Priority.ALWAYS);
        return section;
    }

    /**
     * Setup column untuk Cart Table
     */
    private void createCartTableColumns() {
        TableColumn<CartItem, String> colCode = new TableColumn<>("Kode");
        colCode.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getProduct().getCode()
        ));
        colCode.setPrefWidth(70);

        TableColumn<CartItem, String> colName = new TableColumn<>("Nama");
        colName.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getProduct().getName()
        ));
        colName.setPrefWidth(90);

        TableColumn<CartItem, Integer> colQty = new TableColumn<>("Qty");
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colQty.setPrefWidth(50);

        TableColumn<CartItem, Double> colSubtotal = new TableColumn<>("Subtotal");
        colSubtotal.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(
            cellData.getValue().getSubtotal()
        ).asObject());
        colSubtotal.setPrefWidth(100);

        cartTable.getColumns().addAll(colCode, colName, colQty, colSubtotal);
    }

    /**
     * Membuat section untuk Summary dan Checkout
     */
    private VBox createSummarySection() {
        VBox section = new VBox(10);
        section.setPadding(new Insets(15));
        section.setStyle("-fx-background-color: #f3f4f6;");
        
        Label infoLabel = new Label("Checkout dilakukan dari keranjang belanja");
        infoLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #6b7280;");
        
        section.getChildren().addAll(infoLabel);
        return section;
    }

    // ===== GETTERS =====
    public Button getBtnAddProduct() { return btnAddProduct; }
    public Button getBtnDeleteProduct() { return btnDeleteProduct; }
    public Button getBtnAddToCart() { return btnAddToCart; }
    public Button getBtnRemoveFromCart() { return btnRemoveFromCart; }
    public Button getBtnClearCart() { return btnClearCart; }
    public Button getBtnCheckout() { return btnCheckout; }
    public TableView<Product> getProductTable() { return productTable; }
    public TableView<CartItem> getCartTable() { return cartTable; }

    /**
     * Mengambil data produk dari form input
     */
    public Product getProductFromInput() throws NumberFormatException {
        return new Product(
            txtProductCode.getText(),
            txtProductName.getText(),
            Double.parseDouble(txtProductPrice.getText()),
            Integer.parseInt(txtProductStock.getText())
        );
    }

    /**
     * Mengambil quantity dari input
     */
    public int getQuantityFromInput() throws NumberFormatException {
        return Integer.parseInt(txtQuantity.getText());
    }

    /**
     * Mengosongkan product input form
     */
    public void clearProductInput() {
        txtProductCode.clear();
        txtProductName.clear();
        txtProductPrice.clear();
        txtProductStock.clear();
    }

    /**
     * Mengosongkan quantity input
     */
    public void clearQuantityInput() {
        txtQuantity.clear();
    }

    /**
     * Mengupdate summary display
     */
    public void updateCartSummary(int itemCount, double total) {
        lblCartCount.setText("Total Item: " + itemCount);
        lblCartTotal.setText(String.format("Total: Rp. %.2f", total));
    }
}