package com.upb.agripos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.upb.agripos.exception.DatabaseException;
import com.upb.agripos.exception.OutOfStockException;
import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;
import com.upb.agripos.model.Transaction;
import com.upb.agripos.model.TransactionHistory;
import com.upb.agripos.model.User;
import com.upb.agripos.service.AuthService;
import com.upb.agripos.service.CartService;
import com.upb.agripos.service.CategoryService;
import com.upb.agripos.service.ExcelExportService;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.service.ReportService;
import com.upb.agripos.service.TransactionService;
import com.upb.agripos.view.AdminView;
import com.upb.agripos.view.KasirView;
import com.upb.agripos.view.LoginView;
import com.upb.agripos.view.ReceiptDialog;
import com.upb.agripos.controller.LoginController;
import com.upb.agripos.controller.KasirController;
import com.upb.agripos.controller.AdminController;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Main application class untuk Agri-POS.
 * Mengatur alur login dan navigasi ke view kasir atau admin.
 */
public class AppJavaFX extends Application {
    private Stage primaryStage;
    private AuthService authService;
    private ProductService productService;
    private CategoryService categoryService;
    private CartService cartService;
    private TransactionService transactionService;
    private ReportService reportService;
    private ExcelExportService excelExportService;
    private User currentUser;
    
    // Controllers
    private LoginController loginController;
    private KasirController kasirController;
    private AdminController adminController;

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        primaryStage.setTitle("AGRI-POS - Sistem Point of Sale Pertanian");

        try {
            // Inisialisasi services
            authService = new AuthService();
            productService = new ProductService();
            categoryService = new CategoryService();
            cartService = new CartService(productService);
            transactionService = new TransactionService();
            reportService = new ReportService();
            excelExportService = new ExcelExportService();
            
            // Inisialisasi controllers
            loginController = new LoginController();
            kasirController = new KasirController(productService, cartService);
            adminController = new AdminController();

            // Tampilkan login screen
            showLoginScreen();
        } catch (DatabaseException e) {
            showErrorAlert("Database Error", "Gagal menghubungkan ke database: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Menampilkan login screen.
     */
    private void showLoginScreen() {
        LoginView loginView = new LoginView(primaryStage);
        Scene loginScene = loginView.buildUI();

        loginView.getLoginButton().setOnAction(e -> handleLogin(loginView));

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    /**
     * Handle proses login.
     */
    private void handleLogin(LoginView loginView) {
        String username = loginView.getUsername();
        String password = loginView.getPassword();

        try {
            // Gunakan LoginController untuk autentikasi
            User user = loginController.authenticate(username, password);
            this.currentUser = user;

            loginView.clearFields();

            // Navigate ke halaman sesuai role
            if (user.isKasir()) {
                showKasirScreen();
            } else if (user.isAdmin()) {
                showAdminScreen();
            }
        } catch (ValidationException e) {
            loginView.setErrorMessage(e.getMessage());
        } catch (DatabaseException e) {
            showErrorAlert("Database Error", e.getMessage());
        }
    }

    /**
     * Menampilkan screen untuk Kasir.
     */
    private void showKasirScreen() {
        KasirView kasirView = new KasirView();
        Scene kasirScene = kasirView.buildUI(currentUser.getNamaLengkap());

        try {
            // Load products
            ObservableList<Product> products = FXCollections.observableArrayList(
                    productService.getAllProducts()
            );
            kasirView.setProducts(products);

            // Load transaction history
            loadKasirTransactionHistory(kasirView);

            // Setup callback untuk cart update
            kasirView.setOnCartUpdated(() -> updateCartDisplay(kasirView));

            // Setup event handlers
            setupKasirEventHandlers(kasirView, products);

            primaryStage.setScene(kasirScene);
        } catch (DatabaseException e) {
            showErrorAlert("Error", e.getMessage());
        }
    }

    /**
     * Load transaction history untuk kasir.
     */
    private void loadKasirTransactionHistory(KasirView kasirView) {
        try {
            java.util.List<com.upb.agripos.model.TransactionHistory> history = 
                transactionService.getTransactionHistoryByUser(currentUser.getId());
            ObservableList<com.upb.agripos.model.TransactionHistory> historyList = 
                FXCollections.observableArrayList(history);
            kasirView.setHistoryItems(historyList);
        } catch (DatabaseException ex) {
            showErrorAlert("Error", "Gagal memuat riwayat transaksi: " + ex.getMessage());
        }
    }

    /**
     * Setup event handlers untuk Kasir view.
     */
    private void setupKasirEventHandlers(KasirView kasirView, ObservableList<Product> allProducts) {
        // Logout Button
        kasirView.getLogoutButton().setOnAction(e -> handleLogout());

        // Add to Cart Button
        kasirView.getAddToCartButton().setOnAction(e -> {
            Product selectedProduct = kasirView.getSelectedProduct();
            String qtyText = kasirView.getQuantityField().getText();

            if (selectedProduct == null) {
                showErrorAlert("Perhatian", "Pilih produk terlebih dahulu!");
                return;
            }

            if (qtyText.isEmpty()) {
                showErrorAlert("Perhatian", "Masukkan jumlah quantity!");
                return;
            }

            try {
                int quantity = Integer.parseInt(qtyText);
                if (quantity <= 0) {
                    showErrorAlert("Validasi", "Quantity harus lebih dari 0!");
                    return;
                }
                // Gunakan KasirController untuk menambah item ke cart
                kasirController.addToCart(selectedProduct, quantity);
                updateCartDisplay(kasirView);
                kasirView.getQuantityField().clear();
                showInfoAlert("Sukses", "Item berhasil ditambahkan ke keranjang!");
            } catch (NumberFormatException ex) {
                showErrorAlert("Error", "Quantity harus berupa angka positif!");
            } catch (ValidationException ex) {
                showErrorAlert("Validasi Error", ex.getMessage());
            } catch (OutOfStockException ex) {
                showErrorAlert("Stok Tidak Cukup", ex.getMessage());
            } catch (DatabaseException ex) {
                showErrorAlert("Database Error", ex.getMessage());
            }
        });

        // Remove from Cart Button
        kasirView.getRemoveFromCartButton().setOnAction(e -> {
            CartItem selectedItem = kasirView.getSelectedCartItem();
            if (selectedItem != null) {
                // Gunakan KasirController untuk menghapus item dari cart
                kasirController.removeFromCart(selectedItem.getProduct().getId());
                updateCartDisplay(kasirView);
            } else {
                showErrorAlert("Perhatian", "Pilih item keranjang untuk dihapus!");
            }
        });

        // Checkout Button
        kasirView.getCheckoutButton().setOnAction(e -> handleCheckout(kasirView));

        // Real-time kembalian calculation
        kasirView.getPaymentAmountField().textProperty().addListener((obs, oldVal, newVal) -> {
            try {
                double total = cartService.getCartTotal();
                if (newVal != null && !newVal.isEmpty()) {
                    double jumlahBayar = Double.parseDouble(newVal);
                    kasirView.updateKembalian(total, jumlahBayar);
                } else {
                    kasirView.updateKembalian(total, 0);
                }
            } catch (NumberFormatException ex) {
                kasirView.updateKembalian(cartService.getCartTotal(), 0);
            }
        });

        // Search products
        kasirView.getSearchField().textProperty().addListener((obs, oldVal, newVal) -> {
            try {
                if (newVal == null || newVal.isEmpty()) {
                    kasirView.setProducts(allProducts);
                } else {
                    // Gunakan KasirController untuk search produk
                    ObservableList<Product> searchResults = FXCollections.observableArrayList(
                            kasirController.searchProducts(newVal)
                    );
                    kasirView.setProducts(searchResults);
                }
            } catch (DatabaseException ex) {
                showErrorAlert("Error", ex.getMessage());
            }
        });

        // Refresh History Button
        kasirView.getRefreshHistoryButton().setOnAction(e -> loadKasirTransactionHistory(kasirView));

        // Apply History Filter Button
        kasirView.getHistoryApplyFilterBtn().setOnAction(e -> {
            String metodeFilter = kasirView.getHistoryMethodFilter().getValue();
            String statusFilter = kasirView.getHistoryStatusFilter().getValue();
            
            try {
                java.util.List<com.upb.agripos.model.TransactionHistory> filteredHistory = 
                    transactionService.getTransactionHistoryByUserWithFilter(
                        currentUser.getId(), 
                        metodeFilter, 
                        statusFilter
                    );
                javafx.collections.ObservableList<com.upb.agripos.model.TransactionHistory> filteredList = 
                    javafx.collections.FXCollections.observableArrayList(filteredHistory);
                kasirView.setHistoryItems(filteredList);
                showInfoAlert("Sukses", "Filter berhasil diterapkan!");
            } catch (DatabaseException ex) {
                showErrorAlert("Error", "Gagal menerapkan filter: " + ex.getMessage());
            }
        });
    }

    /**
     * Handle proses checkout.
     */
    private void handleCheckout(KasirView kasirView) {
        if (cartService.getCart().getItems().isEmpty()) {
            showErrorAlert("Perhatian", "Keranjang belanja kosong!");
            return;
        }

        String paymentMethodStr = kasirView.getPaymentMethodCombo().getValue();
        String paymentAmountStr = kasirView.getPaymentAmountField().getText();

        if (paymentAmountStr.isEmpty()) {
            showErrorAlert("Perhatian", "Masukkan jumlah pembayaran!");
            return;
        }

        try {
            double paymentAmount = Double.parseDouble(paymentAmountStr);

            // Set payment method
            transactionService.setPaymentMethod(paymentMethodStr);

            // Create transaction
            Transaction transaction = new Transaction();
            transaction.setCart(cartService.getCart());
            transaction.setTotalHarga(cartService.getCartTotal());
            transaction.setUserId(currentUser.getId());

            // Process payment
            if (transactionService.processPayment(transaction, paymentAmount)) {
                // Save transaction to database
                transactionService.saveTransaction(transaction);
                
                // Reduce stock for each product
                for (CartItem item : transaction.getCart().getItems()) {
                    productService.reduceStock(item.getProduct().getId(), item.getQuantity());
                }

                // Generate receipt
                String receipt = transactionService.generateReceipt(transaction);

                // Show receipt dialog
                ReceiptDialog receiptDialog = new ReceiptDialog(primaryStage);
                receiptDialog.buildUI(receipt);
                receiptDialog.show();

                receiptDialog.getCloseButton().setOnAction(e -> {
                    // Reset for next transaction
                    cartService.resetCart();
                    updateCartDisplay(kasirView);
                    kasirView.getPaymentAmountField().clear();
                    kasirView.getQuantityField().clear();
                    
                    // Reload products
                    try {
                        ObservableList<Product> products = FXCollections.observableArrayList(
                                productService.getAllProducts()
                        );
                        kasirView.setProducts(products);
                    } catch (DatabaseException ex) {
                        showErrorAlert("Error", ex.getMessage());
                    }
                });
            } else {
                showErrorAlert("Pembayaran Gagal", "Pembayaran tidak berhasil diproses!");
            }
        } catch (NumberFormatException ex) {
            showErrorAlert("Error", "Jumlah pembayaran harus berupa angka!");
        } catch (ValidationException | OutOfStockException | DatabaseException ex) {
            showErrorAlert("Error", ex.getMessage());
        }
    }

    /**
     * Update display keranjang di kasir view.
     */
    private void updateCartDisplay(KasirView kasirView) {
        // Gunakan KasirController untuk mendapat cart items
        ObservableList<CartItem> cartItems = FXCollections.observableArrayList(
                kasirController.getCartItems()
        );
        kasirView.setCartItems(cartItems);
        kasirView.updateTotal(kasirController.getCartTotal());
        
        // Update kembalian when cart changes
        try {
            String paymentText = kasirView.getPaymentAmountField().getText();
            double jumlahBayar = (paymentText != null && !paymentText.isEmpty()) 
                    ? Double.parseDouble(paymentText) 
                    : 0;
            kasirView.updateKembalian(kasirController.getCartTotal(), jumlahBayar);
        } catch (NumberFormatException ex) {
            kasirView.updateKembalian(kasirController.getCartTotal(), 0);
        }
    }

    /**
     * Menampilkan screen untuk Admin.
     */
    private void showAdminScreen() {
        AdminView adminView = new AdminView();
        Scene adminScene = adminView.buildUI(currentUser.getNamaLengkap());

        try {
            // Load products
            ObservableList<Product> products = FXCollections.observableArrayList(
                    productService.getAllProducts()
            );
            adminView.setProducts(products);

            // Load users
            ObservableList<com.upb.agripos.model.User> users = FXCollections.observableArrayList(
                    authService.getAllUsers()
            );
            adminView.setUsers(users);

            // Load report data
            loadReportData(adminView);

            // Load transaction history
            loadAdminTransactionHistory(adminView);

            // Setup event handlers
            setupAdminEventHandlers(adminView, products);

            primaryStage.setScene(adminScene);
        } catch (DatabaseException e) {
            showErrorAlert("Error", e.getMessage());
        }
    }

    /**
     * Load kategori dari database untuk dropdown.
     */
    private void loadCategories(AdminView adminView) {
        try {
            java.util.List<String> categories = categoryService.getAllCategories();
            ObservableList<String> categoryList = FXCollections.observableArrayList(categories);
            
            // Populate kategori field combobox
            adminView.getKategoriField().setItems(categoryList);
            
            // Populate kategori filter combobox
            ObservableList<String> filterList = FXCollections.observableArrayList("Semua");
            filterList.addAll(categoryList);
            adminView.getKategoriFilterCombo().setItems(filterList);
            
            // Populate category list combobox for deletion
            adminView.getCategoryListCombo().setItems(categoryList);
        } catch (DatabaseException e) {
            showErrorAlert("Error", "Gagal memuat kategori: " + e.getMessage());
        }
    }

    /**
     * Load transaction history untuk admin (all transactions).
     */
    private void loadAdminTransactionHistory(AdminView adminView) {
        try {
            java.util.List<com.upb.agripos.model.TransactionHistory> history = 
                transactionService.getTransactionHistory();
            ObservableList<com.upb.agripos.model.TransactionHistory> historyList = 
                FXCollections.observableArrayList(history);
            adminView.setHistoryItems(historyList);
        } catch (DatabaseException ex) {
            showErrorAlert("Error", "Gagal memuat riwayat transaksi: " + ex.getMessage());
        }
    }

    /**
     * Setup event handlers untuk Admin view.
     */
    private void setupAdminEventHandlers(AdminView adminView, ObservableList<Product> allProducts) {
        // Load categories
        loadCategories(adminView);

        // Logout Button
        adminView.getLogoutButton().setOnAction(e -> handleLogout());

        // Add Category Button
        adminView.getAddCategoryButton().setOnAction(e -> {
            try {
                String categoryName = adminView.getNewCategoryField().getText().trim();
                if (categoryName.isEmpty()) {
                    showErrorAlert("Error", "Nama kategori tidak boleh kosong!");
                    return;
                }
                categoryService.addCategory(categoryName);
                showInfoAlert("Sukses", "Kategori '" + categoryName + "' berhasil ditambahkan!");
                adminView.getNewCategoryField().clear();
                loadCategories(adminView);
            } catch (DatabaseException ex) {
                showErrorAlert("Error", ex.getMessage());
            }
        });

        // Delete Category Button
        adminView.getDeleteCategoryButton().setOnAction(e -> {
            try {
                String selectedCategory = adminView.getCategoryListCombo().getValue();
                if (selectedCategory == null || selectedCategory.isEmpty()) {
                    showErrorAlert("Error", "Pilih kategori untuk dihapus!");
                    return;
                }
                categoryService.deleteCategory(selectedCategory);
                showInfoAlert("Sukses", "Kategori '" + selectedCategory + "' berhasil dihapus!");
                loadCategories(adminView);
            } catch (DatabaseException ex) {
                showErrorAlert("Error", ex.getMessage());
            }
        });

        // Add Product Button
        adminView.getAddProductButton().setOnAction(e -> {
            try {
                String kode = adminView.getKodeField().getText();
                String nama = adminView.getNamaField().getText();
                String kategori = adminView.getKategoriField().getValue();
                if (kategori == null || kategori.isEmpty()) {
                    showErrorAlert("Error", "Pilih kategori produk!");
                    return;
                }
                double harga = Double.parseDouble(adminView.getHargaField().getText());
                int stok = Integer.parseInt(adminView.getStokField().getText());

                // Gunakan AdminController untuk menambah produk
                adminController.addProduct(kode, nama, kategori, harga, stok);
                showInfoAlert("Sukses", "Produk berhasil ditambahkan!");

                adminView.clearForm();
                refreshProductList(adminView, allProducts);
            } catch (NumberFormatException ex) {
                showErrorAlert("Error", "Harga dan stok harus berupa angka!");
            } catch (ValidationException | DatabaseException ex) {
                showErrorAlert("Error", ex.getMessage());
            }
        });

        // Update Product Button
        adminView.getUpdateProductButton().setOnAction(e -> {
            Product selected = adminView.getSelectedProduct();
            if (selected == null) {
                showErrorAlert("Perhatian", "Pilih produk untuk diupdate!");
                return;
            }

            try {
                String nama = adminView.getNamaField().getText();
                String kategori = adminView.getKategoriField().getValue();
                if (kategori == null || kategori.isEmpty()) {
                    showErrorAlert("Error", "Pilih kategori produk!");
                    return;
                }
                double harga = Double.parseDouble(adminView.getHargaField().getText());
                int stok = Integer.parseInt(adminView.getStokField().getText());

                // Gunakan AdminController untuk update produk
                adminController.updateProduct(selected.getId(), nama, kategori, harga, stok);
                showInfoAlert("Sukses", "Produk berhasil diupdate!");

                adminView.clearForm();
                refreshProductList(adminView, allProducts);
            } catch (NumberFormatException ex) {
                showErrorAlert("Error", "Harga dan stok harus berupa angka!");
            } catch (ValidationException | DatabaseException ex) {
                showErrorAlert("Error", ex.getMessage());
            }
        });

        // Delete Product Button
        adminView.getDeleteProductButton().setOnAction(e -> {
            Product selected = adminView.getSelectedProduct();
            if (selected == null) {
                showErrorAlert("Perhatian", "Pilih produk untuk dihapus!");
                return;
            }

            try {
                // Gunakan AdminController untuk delete produk
                adminController.deleteProduct(selected.getId());
                showInfoAlert("Sukses", "Produk berhasil dihapus!");

                adminView.clearForm();
                refreshProductList(adminView, allProducts);
            } catch (DatabaseException ex) {
                showErrorAlert("Error", ex.getMessage());
            }
        });

        // Add User Button
        adminView.getAddUserButton().setOnAction(e -> {
            try {
                String username = adminView.getUsernameField().getText();
                String password = adminView.getPasswordField().getText();
                String namaLengkap = adminView.getNamaLengkapField().getText();
                String role = adminView.getRoleField().getValue();

                if (username.isEmpty() || password.isEmpty() || namaLengkap.isEmpty()) {
                    showErrorAlert("Perhatian", "Semua field harus diisi!");
                    return;
                }

                authService.createUser(username, password, role, namaLengkap);
                showInfoAlert("Sukses", "User berhasil ditambahkan!");

                adminView.clearUserForm();
                refreshUserList(adminView);
            } catch (ValidationException | DatabaseException ex) {
                showErrorAlert("Error", ex.getMessage());
            }
        });

        // Edit User Button
        adminView.getEditUserButton().setOnAction(e -> {
            com.upb.agripos.model.User selected = adminView.getSelectedUser();
            if (selected == null) {
                showErrorAlert("Perhatian", "Pilih user untuk diedit!");
                return;
            }

            try {
                String username = adminView.getUsernameField().getText();
                String password = adminView.getPasswordField().getText();
                String namaLengkap = adminView.getNamaLengkapField().getText();
                String role = adminView.getRoleField().getValue();

                if (username.isEmpty() || password.isEmpty() || namaLengkap.isEmpty()) {
                    showErrorAlert("Perhatian", "Semua field harus diisi!");
                    return;
                }

                authService.updateUser(selected.getId(), username, password, role, namaLengkap);
                showInfoAlert("Sukses", "User berhasil diupdate!");

                adminView.clearUserForm();
                refreshUserList(adminView);
            } catch (ValidationException | DatabaseException ex) {
                showErrorAlert("Error", ex.getMessage());
            }
        });

        // Delete User Button
        adminView.getDeleteUserButton().setOnAction(e -> {
            com.upb.agripos.model.User selected = adminView.getSelectedUser();
            if (selected == null) {
                showErrorAlert("Perhatian", "Pilih user untuk dihapus!");
                return;
            }

            try {
                authService.deleteUser(selected.getId());
                showInfoAlert("Sukses", "User berhasil dihapus!");

                adminView.clearUserForm();
                refreshUserList(adminView);
            } catch (DatabaseException | com.upb.agripos.exception.ValidationException ex) {
                showErrorAlert("Error", ex.getMessage());
            }
        });

        // User Table selection listener
        adminView.getUserTable().getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                adminView.getUsernameField().setText(newVal.getUsername());
                adminView.getPasswordField().setText(newVal.getPassword());
                adminView.getNamaLengkapField().setText(newVal.getNamaLengkap());
                adminView.getRoleField().setValue(newVal.getRole());
            }
        });

        // Table selection listener
        adminView.getProductTable().getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                adminView.setFormData(newVal);
            }
        });

        // Search functionality
        adminView.getSearchField().textProperty().addListener((obs, oldVal, newVal) -> {
            try {
                if (newVal == null || newVal.isEmpty()) {
                    adminView.setProducts(allProducts);
                } else {
                    // Gunakan AdminController untuk search produk
                    ObservableList<Product> searchResults = FXCollections.observableArrayList(
                            adminController.searchProducts(newVal)
                    );
                    adminView.setProducts(searchResults);
                }
            } catch (DatabaseException ex) {
                showErrorAlert("Error", ex.getMessage());
            }
        });

        // Refresh Report Button
        adminView.getRefreshReportButton().setOnAction(e -> {
            loadReportData(adminView);
            showInfoAlert("Sukses", "Laporan telah diperbarui!");
        });

        // Apply Report Filter Button
        adminView.getApplyReportFilterButton().setOnAction(e -> {
            loadReportDataWithFilter(adminView);
            showInfoAlert("Sukses", "Filter laporan telah diterapkan!");
        });

        // Download Report Button
        adminView.getDownloadReportButton().setOnAction(e -> {
            try {
                exportReportToExcel(adminView);
                showInfoAlert("Sukses", "Laporan berhasil diunduh ke Excel!");
            } catch (Exception ex) {
                showErrorAlert("Error", "Gagal mengunduh laporan: " + ex.getMessage());
            }
        });

        // Refresh History Button
        adminView.getRefreshHistoryButton().setOnAction(e -> loadAdminTransactionHistory(adminView));

        // Apply History Filter Button
        adminView.getHistoryApplyFilterBtn().setOnAction(e -> {
            String metodeFilter = adminView.getHistoryMethodFilter().getValue();
            String statusFilter = adminView.getHistoryStatusFilter().getValue();
            
            try {
                java.util.List<com.upb.agripos.model.TransactionHistory> filteredHistory = 
                    transactionService.getTransactionHistoryWithFilter(metodeFilter, statusFilter);
                javafx.collections.ObservableList<com.upb.agripos.model.TransactionHistory> filteredList = 
                    javafx.collections.FXCollections.observableArrayList(filteredHistory);
                adminView.setHistoryItems(filteredList);
                showInfoAlert("Sukses", "Filter berhasil diterapkan!");
            } catch (DatabaseException ex) {
                showErrorAlert("Error", "Gagal menerapkan filter: " + ex.getMessage());
            }
        });
    }

    /**
     * Refresh product list.
     */
    private void refreshProductList(AdminView adminView, ObservableList<Product> allProducts) {
        try {
            // Gunakan AdminController untuk load semua produk
            allProducts.clear();
            allProducts.addAll(adminController.getAllProducts());
        } catch (DatabaseException e) {
            showErrorAlert("Error", e.getMessage());
        }
    }

    /**
     * Refresh user list.
     */
    private void refreshUserList(AdminView adminView) {
        try {
            ObservableList<com.upb.agripos.model.User> users = FXCollections.observableArrayList(
                    authService.getAllUsers()
            );
            adminView.setUsers(users);
        } catch (DatabaseException e) {
            showErrorAlert("Error", e.getMessage());
        }
    }

    /**
     * Menampilkan error alert.
     */
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Menampilkan info alert.
     */
    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handle logout - kembali ke login screen dengan konfirmasi.
     */
    private void handleLogout() {
        javafx.scene.control.Alert confirmAlert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Konfirmasi Logout");
        confirmAlert.setHeaderText("Apakah Anda yakin ingin logout?");
        confirmAlert.setContentText("Anda akan kembali ke layar login.");
        
        javafx.scene.control.ButtonType yesButton = new javafx.scene.control.ButtonType("Ya, Logout");
        javafx.scene.control.ButtonType noButton = new javafx.scene.control.ButtonType("Tidak", javafx.scene.control.ButtonBar.ButtonData.CANCEL_CLOSE);
        
        confirmAlert.getButtonTypes().setAll(yesButton, noButton);
        
        java.util.Optional<javafx.scene.control.ButtonType> result = confirmAlert.showAndWait();
        
        if (result.isPresent() && result.get() == yesButton) {
            this.currentUser = null;
            cartService.resetCart();
            showLoginScreen();
        }
    }

    /**
     * Load report data dari database untuk admin dashboard.
     */
    private void loadReportData(AdminView adminView) {
        try {
            List<TransactionHistory> transactions = transactionService.getTransactionHistory();
            
            javafx.collections.ObservableList<TransactionHistory> observableList = 
                javafx.collections.FXCollections.observableArrayList(transactions);
            
            adminView.setReportItems(observableList);

            // Update statistics
            double totalPenjualan = 0;
            for (TransactionHistory t : transactions) {
                totalPenjualan += t.getTotalHarga();
            }
            
            adminView.updateReportStatistics(totalPenjualan, transactions.size());

        } catch (DatabaseException e) {
            showErrorAlert("Error", "Error loading report: " + e.getMessage());
        } catch (Exception e) {
            showErrorAlert("Error", "Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Load report data dengan filter tanggal.
     */
    private void loadReportDataWithFilter(AdminView adminView) {
        try {
            java.time.LocalDate fromDate = adminView.getReportFromDate().getValue();
            java.time.LocalDate toDate = adminView.getReportToDate().getValue();
            
            if (fromDate == null && toDate == null) {
                showErrorAlert("Error", "Pilih setidaknya satu tanggal filter!");
                return;
            }

            List<TransactionHistory> transactions = reportService.getReportByDateRange(fromDate, toDate);
            
            javafx.collections.ObservableList<TransactionHistory> observableList = 
                javafx.collections.FXCollections.observableArrayList(transactions);
            
            adminView.setReportItems(observableList);

            // Update statistics
            ReportService.ReportStatistics stats = reportService.getStatisticsByDateRange(fromDate, toDate);
            adminView.updateReportStatistics(stats.totalPenjualan, (int) stats.totalTransaksi);

        } catch (DatabaseException e) {
            showErrorAlert("Error", "Error loading report: " + e.getMessage());
        } catch (Exception e) {
            showErrorAlert("Error", "Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Export laporan transaksi ke format Excel.
     */
    private void exportReportToExcel(AdminView adminView) throws Exception {
        String filename = "Laporan_Transaksi_" + 
                         LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + 
                         ".xlsx";
        
        java.time.LocalDate fromDate = adminView.getReportFromDate().getValue();
        java.time.LocalDate toDate = adminView.getReportToDate().getValue();
        
        List<TransactionHistory> transactions = new ArrayList<>();
        
        if (fromDate != null || toDate != null) {
            transactions = reportService.getReportByDateRange(fromDate, toDate);
        } else {
            transactions = transactionService.getTransactionHistory();
        }

        excelExportService.exportTransactionsToExcel(transactions, fromDate, toDate, filename);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
