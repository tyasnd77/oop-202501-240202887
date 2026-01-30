package com.upb.agripos.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.upb.agripos.model.Product;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.TransactionHistory;
import javafx.util.Callback;

/**
 * View untuk transaksi penjualan (Kasir).
 */
public class KasirView {
    private VBox root;
    private TabPane tabPane;
    
    // Transaction Tab components
    private TableView<Product> productTable;
    private TableView<CartItem> cartTable;
    private TextField searchField;
    private TextField quantityField;
    private Button addToCartButton;
    private Button removeFromCartButton;
    private Button checkoutButton;
    private Label totalLabel;
    private ComboBox<String> paymentMethodCombo;
    private TextField paymentAmountField;
    private Label kembalianLabel;
    
    // History Tab components
    private TableView<TransactionHistory> historyTable;
    private Button refreshHistoryButton;
    private ComboBox<String> historyMethodFilter;
    private ComboBox<String> historyStatusFilter;
    private Button historyApplyFilterBtn;
    
    // Common components
    private Label userInfoLabel;
    private Button logoutButton;

    public Scene buildUI(String userName) {
        root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #f3f4f6;");

        // Header
        HBox headerBox = createHeader(userName);

        // Tab Pane untuk Transaksi dan Riwayat
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab transactionTab = new Tab("Transaksi", createTransactionPane());
        Tab historyTab = new Tab("Riwayat Transaksi", createHistoryPane());

        tabPane.getTabs().addAll(transactionTab, historyTab);

        root.getChildren().addAll(headerBox, new Separator(), tabPane);
        VBox.setVgrow(tabPane, Priority.ALWAYS);

        return new Scene(root, 1400, 800);
    }

    private Pane createTransactionPane() {
        HBox mainContent = new HBox(15);
        mainContent.setPadding(new Insets(10));
        VBox leftPanel = createLeftPanel();
        VBox rightPanel = createRightPanel();

        mainContent.getChildren().addAll(leftPanel, rightPanel);
        HBox.setHgrow(leftPanel, Priority.ALWAYS);
        HBox.setHgrow(rightPanel, Priority.ALWAYS);

        return mainContent;
    }

    private Pane createHistoryPane() {
        VBox historyPane = new VBox(10);
        historyPane.setPadding(new Insets(15));
        historyPane.setStyle("-fx-background-color: white; -fx-border-color: #e5e7eb; -fx-border-radius: 5;");

        // Header untuk history
        HBox historyHeader = new HBox(10);
        Label historyLabel = new Label("Riwayat Transaksi");
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

    private HBox createHeader(String userName) {
        HBox headerBox = new HBox(20);
        headerBox.setPadding(new Insets(15));
        headerBox.setStyle("-fx-background-color: #059669;");

        Label titleLabel = new Label("AGRI-POS - Kasir");
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

    private VBox createLeftPanel() {
        VBox leftPanel = new VBox(10);
        leftPanel.setPadding(new Insets(15));
        leftPanel.setStyle("-fx-border-color: #e5e7eb; -fx-border-radius: 5; -fx-background-color: white;");

        // Search Bar
        Label searchLabel = new Label("Cari Produk:");
        searchLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #1f2937;");
        searchField = new TextField();
        searchField.setPromptText("Ketik nama produk...");
        searchField.setStyle("-fx-font-size: 11; -fx-padding: 8;");
        Button searchButton = new Button("Cari");
        searchButton.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-padding: 8; -fx-font-weight: bold;");

        HBox searchBox = new HBox(10);
        searchBox.getChildren().addAll(searchField, searchButton);
        HBox.setHgrow(searchField, Priority.ALWAYS);

        // Product Table with Action Column
        productTable = new TableView<>();
        setupProductTable();

        // Quantity Input with +/- buttons
        HBox qtyBox = new HBox(8);
        qtyBox.setPadding(new Insets(10));
        qtyBox.setStyle("-fx-background-color: #f9fafb; -fx-border-color: #e5e7eb; -fx-border-radius: 5;");

        Button minusBtn = new Button("-");
        minusBtn.setPrefWidth(40);
        minusBtn.setStyle("-fx-font-size: 14; -fx-background-color: #ef4444; -fx-text-fill: white;");

        Label qtyLabel = new Label("Qty:");
        qtyLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #1f2937;");
        quantityField = new TextField();
        quantityField.setPromptText("0");
        quantityField.setPrefWidth(80);
        quantityField.setStyle("-fx-font-size: 11; -fx-padding: 8; -fx-text-alignment: center;");

        Button plusBtn = new Button("+");
        plusBtn.setPrefWidth(40);
        plusBtn.setStyle("-fx-font-size: 14; -fx-background-color: #10b981; -fx-text-fill: white;");

        addToCartButton = new Button("  Tambah ke Keranjang");
        addToCartButton.setStyle("-fx-background-color: #0ea5e9; -fx-text-fill: white; -fx-padding: 10; -fx-font-size: 12; -fx-font-weight: bold;");

        qtyBox.getChildren().addAll(minusBtn, qtyLabel, quantityField, plusBtn, addToCartButton);
        HBox.setHgrow(addToCartButton, Priority.ALWAYS);

        leftPanel.getChildren().addAll(
                searchLabel, searchBox,
                new Label("Daftar Produk:"),
                productTable,
                qtyBox
        );
        VBox.setVgrow(productTable, Priority.ALWAYS);

        // Handle +/- buttons
        minusBtn.setOnAction(e -> {
            try {
                int current = Integer.parseInt(quantityField.getText());
                if (current > 0) quantityField.setText(String.valueOf(current - 1));
            } catch (NumberFormatException ex) {
                quantityField.setText("1");
            }
        });

        plusBtn.setOnAction(e -> {
            try {
                int current = Integer.parseInt(quantityField.getText().isEmpty() ? "0" : quantityField.getText());
                quantityField.setText(String.valueOf(current + 1));
            } catch (NumberFormatException ex) {
                quantityField.setText("1");
            }
        });

        return leftPanel;
    }

    private void setupProductTable() {
        TableColumn<Product, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));
        idCol.setPrefWidth(50);

        TableColumn<Product, String> namaCol = new TableColumn<>("Nama Produk");
        namaCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNama()));
        namaCol.setPrefWidth(150);

        TableColumn<Product, String> kategoriCol = new TableColumn<>("Kategori");
        kategoriCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getKategori()));
        kategoriCol.setPrefWidth(100);

        TableColumn<Product, Double> hargaCol = new TableColumn<>("Harga");
        hargaCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getHarga()));
        hargaCol.setPrefWidth(100);

        TableColumn<Product, Integer> stokCol = new TableColumn<>("Stok");
        stokCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getStok()));
        stokCol.setPrefWidth(80);

        productTable.getColumns().addAll(idCol, namaCol, kategoriCol, hargaCol, stokCol);
    }

    private VBox createRightPanel() {
        VBox rightPanel = new VBox(10);
        rightPanel.setPadding(new Insets(15));
        rightPanel.setStyle("-fx-border-color: #e5e7eb; -fx-border-radius: 5; -fx-background-color: white;");

        // Cart Table with Action Column
        cartTable = new TableView<>();
        setupCartTable();

        // Total Section
        HBox totalBox = new HBox(20);
        totalBox.setPadding(new Insets(15));
        totalBox.setStyle("-fx-background-color: #f0fdf4; -fx-border-color: #10b981; -fx-border-radius: 5;");

        Label totalTextLabel = new Label("Total Belanja:");
        totalTextLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-text-fill: #1f2937;");

        totalLabel = new Label("Rp 0");
        totalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18; -fx-text-fill: #10b981;");

        removeFromCartButton = new Button("Hapus Item");
        removeFromCartButton.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-padding: 8; -fx-font-weight: bold;");

        totalBox.getChildren().addAll(totalTextLabel, totalLabel, new Region(), removeFromCartButton);
        HBox.setHgrow(totalLabel, Priority.ALWAYS);

        // Payment Section
        VBox paymentBox = createPaymentBox();

        // Checkout Button
        checkoutButton = new Button("CHECKOUT");
        checkoutButton.setPrefHeight(50);
        checkoutButton.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; " +
                "-fx-font-size: 16; -fx-font-weight: bold; -fx-cursor: hand;");
        checkoutButton.setOnMouseEntered(e -> checkoutButton.setStyle(
                "-fx-background-color: #059669; -fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-cursor: hand;"));
        checkoutButton.setOnMouseExited(e -> checkoutButton.setStyle(
                "-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-cursor: hand;"));

        rightPanel.getChildren().addAll(
                new Label("Keranjang Belanja:"),
                cartTable,
                totalBox,
                paymentBox,
                checkoutButton
        );
        VBox.setVgrow(cartTable, Priority.ALWAYS);

        return rightPanel;
    }

    private VBox createPaymentBox() {
        VBox paymentBox = new VBox(10);
        paymentBox.setPadding(new Insets(12));
        paymentBox.setStyle("-fx-border-color: #e5e7eb; -fx-border-radius: 5; -fx-background-color: #fffbeb;");

        Label paymentLabel = new Label("Metode Pembayaran:");
        paymentLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #1f2937;");

        paymentMethodCombo = new ComboBox<>();
        paymentMethodCombo.getItems().addAll("TUNAI", "EWALLET");
        paymentMethodCombo.setValue("TUNAI");
        paymentMethodCombo.setStyle("-fx-font-size: 11; -fx-padding: 8;");

        Label amountLabel = new Label("Jumlah Bayar:");
        amountLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #1f2937;");
        paymentAmountField = new TextField();
        paymentAmountField.setPromptText("Masukkan jumlah pembayaran");
        paymentAmountField.setStyle("-fx-font-size: 11; -fx-padding: 8;");

        kembalianLabel = new Label("Kembalian: Rp 0");
        kembalianLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13; -fx-text-fill: #059669; -fx-padding: 8;");

        paymentBox.getChildren().addAll(
                paymentLabel, paymentMethodCombo,
                amountLabel, paymentAmountField,
                kembalianLabel
        );

        return paymentBox;
    }

    private void setupCartTable() {
        TableColumn<CartItem, String> produkCol = new TableColumn<>("Produk");
        produkCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getProduct().getNama()));
        produkCol.setPrefWidth(120);

        TableColumn<CartItem, Void> qtyCol = new TableColumn<>("Qty");
        qtyCol.setPrefWidth(90);
        qtyCol.setCellFactory(new Callback<TableColumn<CartItem, Void>, TableCell<CartItem, Void>>() {
            @Override
            public TableCell<CartItem, Void> call(TableColumn<CartItem, Void> param) {
                return new TableCell<CartItem, Void>() {
                    private final Button minus = new Button("-");
                    private final TextField qty = new TextField();
                    private final Button plus = new Button("+");
                    private final HBox box = new HBox(4);
                    private CartItem currentItem;

                    {
                        minus.setPrefWidth(30);
                        minus.setStyle("-fx-font-size: 10; -fx-background-color: #ef4444; -fx-text-fill: white;");
                        qty.setPrefWidth(50);
                        qty.setStyle("-fx-font-size: 10; -fx-text-alignment: center;");
                        plus.setPrefWidth(30);
                        plus.setStyle("-fx-font-size: 10; -fx-background-color: #10b981; -fx-text-fill: white;");
                        box.getChildren().addAll(minus, qty, plus);

                        // Event handlers untuk minus button
                        minus.setOnAction(e -> {
                            if (currentItem != null) {
                                int current = currentItem.getQuantity();
                                if (current > 1) {
                                    currentItem.setQuantity(current - 1);
                                    qty.setText(String.valueOf(current - 1));
                                    cartTable.refresh();
                                    onCartUpdated.run();
                                }
                            }
                        });

                        // Event handler untuk quantity field
                        qty.setOnAction(e -> {
                            if (currentItem != null) {
                                try {
                                    int newQty = Integer.parseInt(qty.getText());
                                    if (newQty > 0) {
                                        currentItem.setQuantity(newQty);
                                        cartTable.refresh();
                                        onCartUpdated.run();
                                    } else {
                                        qty.setText(String.valueOf(currentItem.getQuantity()));
                                    }
                                } catch (NumberFormatException ex) {
                                    qty.setText(String.valueOf(currentItem.getQuantity()));
                                }
                            }
                        });

                        // Event handlers untuk plus button
                        plus.setOnAction(e -> {
                            if (currentItem != null) {
                                int current = currentItem.getQuantity();
                                currentItem.setQuantity(current + 1);
                                qty.setText(String.valueOf(current + 1));
                                cartTable.refresh();
                                onCartUpdated.run();
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || getTableRow().getItem() == null) {
                            setGraphic(null);
                            currentItem = null;
                        } else {
                            currentItem = getTableRow().getItem();
                            qty.setText(String.valueOf(currentItem.getQuantity()));
                            setGraphic(box);
                        }
                    }
                };
            }
        });

        TableColumn<CartItem, Double> hargaCol = new TableColumn<>("Harga");
        hargaCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(
                cellData.getValue().getProduct().getHarga()));
        hargaCol.setPrefWidth(90);

        TableColumn<CartItem, Double> subtotalCol = new TableColumn<>("Subtotal");
        subtotalCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(
                cellData.getValue().getSubtotal()));
        subtotalCol.setPrefWidth(100);

        cartTable.getColumns().addAll(produkCol, qtyCol, hargaCol, subtotalCol);
    }

    // Callback untuk update cart
    private Runnable onCartUpdated = () -> {};

    public void setOnCartUpdated(Runnable callback) {
        this.onCartUpdated = callback;
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
    public TextField getSearchField() {
        return searchField;
    }

    public TextField getQuantityField() {
        return quantityField;
    }

    public TextField getPaymentAmountField() {
        return paymentAmountField;
    }

    public Button getAddToCartButton() {
        return addToCartButton;
    }

    public Button getRemoveFromCartButton() {
        return removeFromCartButton;
    }

    public Button getCheckoutButton() {
        return checkoutButton;
    }

    public TableView<Product> getProductTable() {
        return productTable;
    }

    public TableView<CartItem> getCartTable() {
        return cartTable;
    }

    public ComboBox<String> getPaymentMethodCombo() {
        return paymentMethodCombo;
    }

    public Label getTotalLabel() {
        return totalLabel;
    }

    public void setProducts(ObservableList<Product> products) {
        productTable.setItems(products);
    }

    public void setCartItems(ObservableList<CartItem> items) {
        cartTable.setItems(items);
    }

    public void updateTotal(double total) {
        totalLabel.setText(String.format("Rp %.0f", total));
    }

    public Product getSelectedProduct() {
        return productTable.getSelectionModel().getSelectedItem();
    }

    public CartItem getSelectedCartItem() {
        return cartTable.getSelectionModel().getSelectedItem();
    }

    public Button getLogoutButton() {
        return logoutButton;
    }

    public void updateKembalian(double total, double jumlahBayar) {
        double kembalian = jumlahBayar - total;
        if (kembalian >= 0) {
            kembalianLabel.setText(String.format("Kembalian: Rp %.0f", kembalian));
            kembalianLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13; -fx-text-fill: #059669; -fx-padding: 8;");
        } else {
            kembalianLabel.setText(String.format("Kurang: Rp %.0f", Math.abs(kembalian)));
            kembalianLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13; -fx-text-fill: #ef4444; -fx-padding: 8;");
        }
    }

    public Label getKembalianLabel() {
        return kembalianLabel;
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
