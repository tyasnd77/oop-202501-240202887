<<<<<<< HEAD
# CSS Documentation - Agri-POS

## Daftar Isi
1. [Pendahuluan](#pendahuluan)
2. [Struktur File](#struktur-file)
3. [Variabel Warna](#variabel-warna)
4. [Komponen UI](#komponen-ui)
5. [Cara Penggunaan di JavaFX](#cara-penggunaan-di-javafx)
6. [Responsive Design](#responsive-design)
7. [Customization](#customization)

---

## Pendahuluan

Stylesheet CSS Agri-POS dirancang dengan tema hijau dan emerald yang modern. Stylesheet ini mencakup:
- **main.css**: CSS standar untuk web (reference design)
- **javafx.css**: CSS khusus untuk JavaFX styling

---

## Struktur File

```
src/main/resources/
└── styles/
    ├── main.css          # CSS Web Standard
    ├── javafx.css        # CSS JavaFX
    └── README.md         # Dokumentasi (file ini)
```

---

## Variabel Warna

### Primary Colors (Hijau)
```css
--primary-dark: #14532d    /* Hijau gelap untuk header, footer */
--primary-medium: #15803d  /* Hijau medium untuk borders */
--primary-light: #16a34a   /* Hijau terang untuk buttons, accents */
```

### Secondary Colors (Emerald)
```css
--secondary-dark: #047857     /* Untuk hover states */
--secondary-medium: #059669   /* Untuk gradients */
--secondary-light: #6ee7b7    /* Untuk backgrounds */
```

### Status Colors
```css
--success: #10b981   /* Hijau - Status berhasil */
--warning: #f59e0b   /* Orange - Peringatan */
--danger: #ef4444    /* Merah - Error/Bahaya */
--info: #3b82f6      /* Biru - Informasi */
```

### Neutral Colors
```css
--white: #ffffff
--gray-50: #f9fafb
--gray-100: #f3f4f6
--gray-200: #e5e7eb
--gray-300: #d1d5db
--gray-400: #9ca3af
--gray-500: #6b7280
--gray-600: #4b5563
--gray-700: #374151
--gray-800: #1f2937
--gray-900: #111827
```

---

## Komponen UI

### 1. LOGIN PAGE

**Class**: `.login-container`, `.login-card`, `.login-logo`

Fitur:
- Background gradient halus
- Logo circular dengan gradient hijau
- Tombol login dengan hover effect
- Form styling modern

**Contoh HTML**:
```html
<div class="login-container">
    <div class="login-card">
        <div class="login-logo">🌾</div>
        <h1 class="login-title">Agri-POS</h1>
        <p class="login-subtitle">Sistem Point of Sale Terpadu</p>
        
        <form>
            <div class="form-group">
                <label>Username</label>
                <input type="text" name="username" placeholder="Masukkan username">
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" placeholder="Masukkan password">
            </div>
            <button class="btn-login">Login</button>
        </form>
    </div>
</div>
```

### 2. HEADER

**Class**: `.header`, `.header-left`, `.header-right`, `.header-user-info`

Fitur:
- Background gradient hijau gelap
- Sticky position
- Logo dan user info
- Logout button

**Contoh HTML**:
```html
<div class="header">
    <div class="header-left">
        <div class="header-logo">
            <span class="header-logo-icon"></span>Agri-POS
        </div>
        <div class="header-user-info">
            <span class="header-user-name">Admin User</span>
            <span class="header-user-role">Administrator</span>
        </div>
    </div>
    <div class="header-right">
        <button class="btn-logout">Logout</button>
    </div>
</div>
```

### 3. NAVIGATION TABS

**Class**: `.tab-container`, `.tab`, `.tab.active`

Fitur:
- Horizontal tabs dengan border-bottom
- Active indicator dengan gradient
- Hover effect

**Contoh HTML**:
```html
<div class="tab-container">
    <button class="tab active">📦 Produk</button>
    <button class="tab">👥 User</button>
    <button class="tab">📊 Laporan</button>
</div>
```

### 4. CARDS

**Class**: `.card`

Fitur:
- Border hijau muda
- Rounded corners
- Shadow halus
- Hover effect

**Contoh HTML**:
```html
<div class="card">
    <div class="card-header">
        <h3 class="card-title">Judul Card</h3>
    </div>
    <div class="card-body">
        <p>Isi content di sini</p>
    </div>
</div>
```

### 5. PRODUCT CARDS

**Class**: `.products-grid`, `.product-card`, `.badge-stok-ready/.badge-stok-low/.badge-stok-out`

Fitur:
- Responsive grid (auto-fill, minmax)
- Product image area
- Badge stok kondisional
- Add button dengan gradient

**Contoh HTML**:
```html
<div class="products-grid">
    <div class="product-card">
        <div class="product-image">🥬</div>
        <div class="product-content">
            <div class="product-category">Sayuran</div>
            <h3 class="product-name">Bayam Segar</h3>
            <div class="product-price">Rp 15.000</div>
            <span class="product-badge badge-stok-ready">Stok: 25 kg</span>
            <button class="btn-add-product">Tambah Keranjang</button>
        </div>
    </div>
</div>
```

### 6. SHOPPING CART

**Class**: `.cart-sidebar`, `.cart-items`, `.cart-item`, `.quantity-control`

Fitur:
- Sticky sidebar di kanan
- Quantity +/- buttons
- Cart total
- Checkout button full-width

**Contoh HTML**:
```html
<div class="cart-sidebar">
    <div class="cart-header">
        <h3 class="cart-title">🛒 Keranjang (2)</h3>
    </div>
    <div class="cart-items">
        <div class="cart-item">
            <div class="cart-item-info">
                <div class="cart-item-name">Bayam Segar</div>
                <div class="cart-item-price">Rp 15.000</div>
            </div>
            <div class="quantity-control">
                <button class="qty-btn">-</button>
                <span class="qty-display">2</span>
                <button class="qty-btn">+</button>
            </div>
        </div>
    </div>
    <div class="cart-footer">
        <div class="cart-total-label">
            <span class="label">Total</span>
            <span class="amount">Rp 30.000</span>
        </div>
        <button class="btn-checkout">Bayar Sekarang</button>
    </div>
</div>
```

### 7. TABLES

**Class**: `.table-wrapper`, `.action-buttons`, `.btn-view/.btn-edit/.btn-delete`

Fitur:
- Header dengan gradient hijau
- Alternating row colors
- Hover effect pada rows
- Color-coded action buttons

**Contoh HTML**:
```html
<div class="table-wrapper">
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nama Produk</th>
                <th>Harga</th>
                <th>Stok</th>
                <th>Aksi</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>1</td>
                <td>Bayam Segar</td>
                <td>Rp 15.000</td>
                <td>25</td>
                <td>
                    <div class="action-buttons">
                        <button class="btn-small btn-view">View</button>
                        <button class="btn-small btn-edit">Edit</button>
                        <button class="btn-small btn-delete">Delete</button>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>
</div>
```

### 8. DASHBOARD CARDS

**Class**: `.stats-grid`, `.stat-card`, `.stat-value`, `.stat-label`

Fitur:
- Gradient background hijau
- Teks putih dengan shadow
- Icon besar
- Responsive grid

**Contoh HTML**:
```html
<div class="stats-grid">
    <div class="stat-card">
        <div class="stat-icon">💰</div>
        <div class="stat-content">
            <div class="stat-label">Total Penjualan</div>
            <div class="stat-value">Rp 5.000.000</div>
            <div class="stat-description">Bulan ini</div>
        </div>
    </div>
    <div class="stat-card">
        <div class="stat-icon">📊</div>
        <div class="stat-content">
            <div class="stat-label">Total Transaksi</div>
            <div class="stat-value">250</div>
            <div class="stat-description">Transaksi</div>
        </div>
    </div>
</div>
```

### 9. FORMS

**Class**: `.form-container`, `.form-group`, `.form-row`

Fitur:
- Label styling
- Input focus states
- Error messages
- Responsive columns

**Contoh HTML**:
```html
<div class="form-container">
    <div class="form-row two-columns">
        <div class="form-group">
            <label>Nama Produk</label>
            <input type="text" placeholder="Masukkan nama produk">
        </div>
        <div class="form-group">
            <label>Kategori</label>
            <input type="text" placeholder="Kategori produk">
        </div>
    </div>
    <div class="form-group">
        <label>Deskripsi</label>
        <textarea placeholder="Deskripsi produk"></textarea>
    </div>
</div>
```

### 10. BUTTONS

**Classes**: `.btn`, `.btn-primary`, `.btn-secondary`, `.btn-success`, `.btn-danger`, `.btn-warning`, `.btn-info`

Fitur:
- Gradient backgrounds
- Hover & active states
- Disabled state
- Responsive sizing

**Contoh HTML**:
```html
<button class="btn btn-primary">Simpan</button>
<button class="btn btn-secondary">Batal</button>
<button class="btn btn-success">Berhasil</button>
<button class="btn btn-danger">Hapus</button>
<button class="btn btn-warning">Peringatan</button>
<button class="btn btn-info">Info</button>
```

### 11. ALERTS

**Class**: `.alert`, `.alert-success/.alert-danger/.alert-warning/.alert-info`

Fitur:
- Color-coded backgrounds
- Left border indicator
- Padding konsisten

**Contoh HTML**:
```html
<div class="alert alert-success">✓ Data berhasil disimpan!</div>
<div class="alert alert-danger">✗ Gagal menghapus data</div>
<div class="alert alert-warning">⚠ Perhatian: Stok menipis</div>
<div class="alert alert-info">ℹ Informasi penting</div>
```

### 12. MODALS

**Class**: `.modal`, `.modal-content`, `.modal-header`, `.modal-body`, `.modal-footer`

Fitur:
- Semi-transparent backdrop
- Centered dialog
- Smooth animations
- Close button

**Contoh HTML**:
```html
<div class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h3 class="modal-title">Konfirmasi Hapus</h3>
            <button class="modal-close">&times;</button>
        </div>
        <div class="modal-body">
            <p>Apakah Anda yakin ingin menghapus data ini?</p>
        </div>
        <div class="modal-footer">
            <button class="btn btn-secondary">Batal</button>
            <button class="btn btn-danger">Hapus</button>
        </div>
    </div>
</div>
```

---

## Cara Penggunaan di JavaFX

### 1. Setup di AppJavaFX

```java
// Di method start() atau buildUI()
Scene scene = new Scene(root);

// Load external stylesheet
String resource = getClass().getResource("/styles/javafx.css").toExternalForm();
scene.getStylesheets().add(resource);

primaryStage.setScene(scene);
primaryStage.show();
```

### 2. Apply Style ke Components

#### Header
```java
HBox header = new HBox();
header.setStyle(
    "-fx-background-color: linear-gradient(to right, #14532d, #15803d);" +
    "-fx-padding: 15px 20px;" +
    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 10, 0, 0, 0);"
);
```

#### Primary Button
```java
Button btnLogin = new Button("Login");
btnLogin.setStyle(
    "-fx-background-color: linear-gradient(to right, #16a34a, #059669);" +
    "-fx-text-fill: #ffffff;" +
    "-fx-padding: 12px 24px;" +
    "-fx-font-weight: bold;" +
    "-fx-font-size: 14px;" +
    "-fx-border-radius: 5px;" +
    "-fx-background-radius: 5px;"
);
```

#### Card
```java
VBox card = new VBox();
card.setStyle(
    "-fx-background-color: #ffffff;" +
    "-fx-border-color: #d1d5db;" +
    "-fx-border-radius: 8px;" +
    "-fx-border-width: 1px;" +
    "-fx-padding: 20px;" +
    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 5, 0, 0, 0);"
);
```

#### Table Header
```java
TableView<Product> table = new TableView<>();
table.setStyle(
    "-fx-background-color: #ffffff;" +
    "-fx-border-color: #d1d5db;" +
    "-fx-border-radius: 5px;"
);
// Header styling via CSS class
table.getStyleClass().add("table-view");
```

#### Stat Card
```java
VBox statCard = new VBox();
statCard.setStyle(
    "-fx-background-color: linear-gradient(to bottom right, #16a34a, #059669);" +
    "-fx-text-fill: #ffffff;" +
    "-fx-padding: 25px;" +
    "-fx-border-radius: 8px;" +
    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 8, 0, 0, 0);"
);
```

### 3. Style Classes untuk JavaFX

```java
// Menambah CSS class
button.getStyleClass().add("btn-primary");
label.getStyleClass().add("label-title");
card.getStyleClass().add("card");

// Atau setStyle langsung untuk inline styles
```

---

## Responsive Design

CSS dirancang responsive dengan breakpoints:

### Desktop (> 1024px)
- Full layout dengan sidebar
- Multi-column grids
- Semua elements terlihat

### Tablet (768px - 1024px)
- Sidebar mungkin tersembunyi
- Grid 2 kolom
- Navigation adaptif

### Mobile (< 768px)
- Single column layout
- Sidebar menjadi drawer/modal
- Buttons full-width
- Simplified navigation

---

## Customization

### Mengubah Warna Utama

Edit variabel CSS di `:root`:

```css
:root {
    --primary-light: #16a34a;    /* Ubah warna hijau */
    --primary-dark: #14532d;
    --secondary-medium: #059669;
}
```

### Mengubah Font

```css
body {
    font-family: "Custom Font", sans-serif;
}
```

### Mengubah Ukuran Spacing

```css
.card {
    -fx-padding: 30px;  /* Ubah dari 20px */
}
```

### Custom Shadow

```css
.card {
    -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 15, 0, 2, 2);
}
```

---

## Color Palette Reference

### Hijau (Primary)
- `#14532d` - Dark
- `#15803d` - Medium  
- `#16a34a` - Light

### Emerald (Secondary)
- `#047857` - Dark
- `#059669` - Medium
- `#6ee7b7` - Light

### Status
- `#10b981` - Success (Hijau)
- `#f59e0b` - Warning (Orange)
- `#ef4444` - Danger (Merah)
- `#3b82f6` - Info (Biru)

---

## Browser & JavaFX Support

### CSS Web (main.css)
- Modern browsers (Chrome, Firefox, Safari, Edge)
- Responsive design
- Mobile-friendly

### JavaFX CSS (javafx.css)
- JavaFX 17+
- Subset dari CSS standard
- Vendor-specific properties (`-fx-*`)

---

## Tips & Best Practices

1. **Gunakan CSS Classes** - Lebih mudah di-maintain daripada inline styles
2. **Responsive First** - Design untuk mobile dulu, baru expand ke desktop
3. **Konsisten Spacing** - Gunakan multiples dari base unit (8px, 16px, etc)
4. **Meaningful Colors** - Gunakan colors untuk indicate status/importance
5. **Accessibility** - Pastikan contrast ratio sufficient untuk text
6. **Performance** - Minimize inline styles, gunakan external stylesheets

---

## Troubleshooting

### Styles tidak muncul di JavaFX
- Pastikan path stylesheet benar
- Periksa bahwa resource ada di `src/main/resources`
- JavaFX CSS syntax berbeda dari web CSS

### Border radius tidak bekerja
- Gunakan `-fx-border-radius` untuk JavaFX
- Gunakan `-fx-background-radius` untuk background

### Shadow effect tidak smooth
- Gunakan `dropshadow(gaussian, ...)` untuk smooth
- Adjust blur radius untuk kontrol kedalaman

---

## File References

- **main.css** (3800+ lines) - Complete web stylesheet
- **javafx.css** (2000+ lines) - JavaFX specific styles
- **Responsive breakpoints** - 768px, 480px

Total styling coverage untuk semua komponen utama aplikasi Agri-POS.
=======
# CSS Documentation - Agri-POS

## Daftar Isi
1. [Pendahuluan](#pendahuluan)
2. [Struktur File](#struktur-file)
3. [Variabel Warna](#variabel-warna)
4. [Komponen UI](#komponen-ui)
5. [Cara Penggunaan di JavaFX](#cara-penggunaan-di-javafx)
6. [Responsive Design](#responsive-design)
7. [Customization](#customization)

---

## Pendahuluan

Stylesheet CSS Agri-POS dirancang dengan tema hijau dan emerald yang modern. Stylesheet ini mencakup:
- **main.css**: CSS standar untuk web (reference design)
- **javafx.css**: CSS khusus untuk JavaFX styling

---

## Struktur File

```
src/main/resources/
└── styles/
    ├── main.css          # CSS Web Standard
    ├── javafx.css        # CSS JavaFX
    └── README.md         # Dokumentasi (file ini)
```

---

## Variabel Warna

### Primary Colors (Hijau)
```css
--primary-dark: #14532d    /* Hijau gelap untuk header, footer */
--primary-medium: #15803d  /* Hijau medium untuk borders */
--primary-light: #16a34a   /* Hijau terang untuk buttons, accents */
```

### Secondary Colors (Emerald)
```css
--secondary-dark: #047857     /* Untuk hover states */
--secondary-medium: #059669   /* Untuk gradients */
--secondary-light: #6ee7b7    /* Untuk backgrounds */
```

### Status Colors
```css
--success: #10b981   /* Hijau - Status berhasil */
--warning: #f59e0b   /* Orange - Peringatan */
--danger: #ef4444    /* Merah - Error/Bahaya */
--info: #3b82f6      /* Biru - Informasi */
```

### Neutral Colors
```css
--white: #ffffff
--gray-50: #f9fafb
--gray-100: #f3f4f6
--gray-200: #e5e7eb
--gray-300: #d1d5db
--gray-400: #9ca3af
--gray-500: #6b7280
--gray-600: #4b5563
--gray-700: #374151
--gray-800: #1f2937
--gray-900: #111827
```

---

## Komponen UI

### 1. LOGIN PAGE

**Class**: `.login-container`, `.login-card`, `.login-logo`

Fitur:
- Background gradient halus
- Logo circular dengan gradient hijau
- Tombol login dengan hover effect
- Form styling modern

**Contoh HTML**:
```html
<div class="login-container">
    <div class="login-card">
        <div class="login-logo">🌾</div>
        <h1 class="login-title">Agri-POS</h1>
        <p class="login-subtitle">Sistem Point of Sale Terpadu</p>
        
        <form>
            <div class="form-group">
                <label>Username</label>
                <input type="text" name="username" placeholder="Masukkan username">
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" placeholder="Masukkan password">
            </div>
            <button class="btn-login">Login</button>
        </form>
    </div>
</div>
```

### 2. HEADER

**Class**: `.header`, `.header-left`, `.header-right`, `.header-user-info`

Fitur:
- Background gradient hijau gelap
- Sticky position
- Logo dan user info
- Logout button

**Contoh HTML**:
```html
<div class="header">
    <div class="header-left">
        <div class="header-logo">
            <span class="header-logo-icon"></span>Agri-POS
        </div>
        <div class="header-user-info">
            <span class="header-user-name">Admin User</span>
            <span class="header-user-role">Administrator</span>
        </div>
    </div>
    <div class="header-right">
        <button class="btn-logout">Logout</button>
    </div>
</div>
```

### 3. NAVIGATION TABS

**Class**: `.tab-container`, `.tab`, `.tab.active`

Fitur:
- Horizontal tabs dengan border-bottom
- Active indicator dengan gradient
- Hover effect

**Contoh HTML**:
```html
<div class="tab-container">
    <button class="tab active">📦 Produk</button>
    <button class="tab">👥 User</button>
    <button class="tab">📊 Laporan</button>
</div>
```

### 4. CARDS

**Class**: `.card`

Fitur:
- Border hijau muda
- Rounded corners
- Shadow halus
- Hover effect

**Contoh HTML**:
```html
<div class="card">
    <div class="card-header">
        <h3 class="card-title">Judul Card</h3>
    </div>
    <div class="card-body">
        <p>Isi content di sini</p>
    </div>
</div>
```

### 5. PRODUCT CARDS

**Class**: `.products-grid`, `.product-card`, `.badge-stok-ready/.badge-stok-low/.badge-stok-out`

Fitur:
- Responsive grid (auto-fill, minmax)
- Product image area
- Badge stok kondisional
- Add button dengan gradient

**Contoh HTML**:
```html
<div class="products-grid">
    <div class="product-card">
        <div class="product-image">🥬</div>
        <div class="product-content">
            <div class="product-category">Sayuran</div>
            <h3 class="product-name">Bayam Segar</h3>
            <div class="product-price">Rp 15.000</div>
            <span class="product-badge badge-stok-ready">Stok: 25 kg</span>
            <button class="btn-add-product">Tambah Keranjang</button>
        </div>
    </div>
</div>
```

### 6. SHOPPING CART

**Class**: `.cart-sidebar`, `.cart-items`, `.cart-item`, `.quantity-control`

Fitur:
- Sticky sidebar di kanan
- Quantity +/- buttons
- Cart total
- Checkout button full-width

**Contoh HTML**:
```html
<div class="cart-sidebar">
    <div class="cart-header">
        <h3 class="cart-title">🛒 Keranjang (2)</h3>
    </div>
    <div class="cart-items">
        <div class="cart-item">
            <div class="cart-item-info">
                <div class="cart-item-name">Bayam Segar</div>
                <div class="cart-item-price">Rp 15.000</div>
            </div>
            <div class="quantity-control">
                <button class="qty-btn">-</button>
                <span class="qty-display">2</span>
                <button class="qty-btn">+</button>
            </div>
        </div>
    </div>
    <div class="cart-footer">
        <div class="cart-total-label">
            <span class="label">Total</span>
            <span class="amount">Rp 30.000</span>
        </div>
        <button class="btn-checkout">Bayar Sekarang</button>
    </div>
</div>
```

### 7. TABLES

**Class**: `.table-wrapper`, `.action-buttons`, `.btn-view/.btn-edit/.btn-delete`

Fitur:
- Header dengan gradient hijau
- Alternating row colors
- Hover effect pada rows
- Color-coded action buttons

**Contoh HTML**:
```html
<div class="table-wrapper">
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nama Produk</th>
                <th>Harga</th>
                <th>Stok</th>
                <th>Aksi</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>1</td>
                <td>Bayam Segar</td>
                <td>Rp 15.000</td>
                <td>25</td>
                <td>
                    <div class="action-buttons">
                        <button class="btn-small btn-view">View</button>
                        <button class="btn-small btn-edit">Edit</button>
                        <button class="btn-small btn-delete">Delete</button>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>
</div>
```

### 8. DASHBOARD CARDS

**Class**: `.stats-grid`, `.stat-card`, `.stat-value`, `.stat-label`

Fitur:
- Gradient background hijau
- Teks putih dengan shadow
- Icon besar
- Responsive grid

**Contoh HTML**:
```html
<div class="stats-grid">
    <div class="stat-card">
        <div class="stat-icon">💰</div>
        <div class="stat-content">
            <div class="stat-label">Total Penjualan</div>
            <div class="stat-value">Rp 5.000.000</div>
            <div class="stat-description">Bulan ini</div>
        </div>
    </div>
    <div class="stat-card">
        <div class="stat-icon">📊</div>
        <div class="stat-content">
            <div class="stat-label">Total Transaksi</div>
            <div class="stat-value">250</div>
            <div class="stat-description">Transaksi</div>
        </div>
    </div>
</div>
```

### 9. FORMS

**Class**: `.form-container`, `.form-group`, `.form-row`

Fitur:
- Label styling
- Input focus states
- Error messages
- Responsive columns

**Contoh HTML**:
```html
<div class="form-container">
    <div class="form-row two-columns">
        <div class="form-group">
            <label>Nama Produk</label>
            <input type="text" placeholder="Masukkan nama produk">
        </div>
        <div class="form-group">
            <label>Kategori</label>
            <input type="text" placeholder="Kategori produk">
        </div>
    </div>
    <div class="form-group">
        <label>Deskripsi</label>
        <textarea placeholder="Deskripsi produk"></textarea>
    </div>
</div>
```

### 10. BUTTONS

**Classes**: `.btn`, `.btn-primary`, `.btn-secondary`, `.btn-success`, `.btn-danger`, `.btn-warning`, `.btn-info`

Fitur:
- Gradient backgrounds
- Hover & active states
- Disabled state
- Responsive sizing

**Contoh HTML**:
```html
<button class="btn btn-primary">Simpan</button>
<button class="btn btn-secondary">Batal</button>
<button class="btn btn-success">Berhasil</button>
<button class="btn btn-danger">Hapus</button>
<button class="btn btn-warning">Peringatan</button>
<button class="btn btn-info">Info</button>
```

### 11. ALERTS

**Class**: `.alert`, `.alert-success/.alert-danger/.alert-warning/.alert-info`

Fitur:
- Color-coded backgrounds
- Left border indicator
- Padding konsisten

**Contoh HTML**:
```html
<div class="alert alert-success">✓ Data berhasil disimpan!</div>
<div class="alert alert-danger">✗ Gagal menghapus data</div>
<div class="alert alert-warning">⚠ Perhatian: Stok menipis</div>
<div class="alert alert-info">ℹ Informasi penting</div>
```

### 12. MODALS

**Class**: `.modal`, `.modal-content`, `.modal-header`, `.modal-body`, `.modal-footer`

Fitur:
- Semi-transparent backdrop
- Centered dialog
- Smooth animations
- Close button

**Contoh HTML**:
```html
<div class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h3 class="modal-title">Konfirmasi Hapus</h3>
            <button class="modal-close">&times;</button>
        </div>
        <div class="modal-body">
            <p>Apakah Anda yakin ingin menghapus data ini?</p>
        </div>
        <div class="modal-footer">
            <button class="btn btn-secondary">Batal</button>
            <button class="btn btn-danger">Hapus</button>
        </div>
    </div>
</div>
```

---

## Cara Penggunaan di JavaFX

### 1. Setup di AppJavaFX

```java
// Di method start() atau buildUI()
Scene scene = new Scene(root);

// Load external stylesheet
String resource = getClass().getResource("/styles/javafx.css").toExternalForm();
scene.getStylesheets().add(resource);

primaryStage.setScene(scene);
primaryStage.show();
```

### 2. Apply Style ke Components

#### Header
```java
HBox header = new HBox();
header.setStyle(
    "-fx-background-color: linear-gradient(to right, #14532d, #15803d);" +
    "-fx-padding: 15px 20px;" +
    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 10, 0, 0, 0);"
);
```

#### Primary Button
```java
Button btnLogin = new Button("Login");
btnLogin.setStyle(
    "-fx-background-color: linear-gradient(to right, #16a34a, #059669);" +
    "-fx-text-fill: #ffffff;" +
    "-fx-padding: 12px 24px;" +
    "-fx-font-weight: bold;" +
    "-fx-font-size: 14px;" +
    "-fx-border-radius: 5px;" +
    "-fx-background-radius: 5px;"
);
```

#### Card
```java
VBox card = new VBox();
card.setStyle(
    "-fx-background-color: #ffffff;" +
    "-fx-border-color: #d1d5db;" +
    "-fx-border-radius: 8px;" +
    "-fx-border-width: 1px;" +
    "-fx-padding: 20px;" +
    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 5, 0, 0, 0);"
);
```

#### Table Header
```java
TableView<Product> table = new TableView<>();
table.setStyle(
    "-fx-background-color: #ffffff;" +
    "-fx-border-color: #d1d5db;" +
    "-fx-border-radius: 5px;"
);
// Header styling via CSS class
table.getStyleClass().add("table-view");
```

#### Stat Card
```java
VBox statCard = new VBox();
statCard.setStyle(
    "-fx-background-color: linear-gradient(to bottom right, #16a34a, #059669);" +
    "-fx-text-fill: #ffffff;" +
    "-fx-padding: 25px;" +
    "-fx-border-radius: 8px;" +
    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 8, 0, 0, 0);"
);
```

### 3. Style Classes untuk JavaFX

```java
// Menambah CSS class
button.getStyleClass().add("btn-primary");
label.getStyleClass().add("label-title");
card.getStyleClass().add("card");

// Atau setStyle langsung untuk inline styles
```

---

## Responsive Design

CSS dirancang responsive dengan breakpoints:

### Desktop (> 1024px)
- Full layout dengan sidebar
- Multi-column grids
- Semua elements terlihat

### Tablet (768px - 1024px)
- Sidebar mungkin tersembunyi
- Grid 2 kolom
- Navigation adaptif

### Mobile (< 768px)
- Single column layout
- Sidebar menjadi drawer/modal
- Buttons full-width
- Simplified navigation

---

## Customization

### Mengubah Warna Utama

Edit variabel CSS di `:root`:

```css
:root {
    --primary-light: #16a34a;    /* Ubah warna hijau */
    --primary-dark: #14532d;
    --secondary-medium: #059669;
}
```

### Mengubah Font

```css
body {
    font-family: "Custom Font", sans-serif;
}
```

### Mengubah Ukuran Spacing

```css
.card {
    -fx-padding: 30px;  /* Ubah dari 20px */
}
```

### Custom Shadow

```css
.card {
    -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 15, 0, 2, 2);
}
```

---

## Color Palette Reference

### Hijau (Primary)
- `#14532d` - Dark
- `#15803d` - Medium  
- `#16a34a` - Light

### Emerald (Secondary)
- `#047857` - Dark
- `#059669` - Medium
- `#6ee7b7` - Light

### Status
- `#10b981` - Success (Hijau)
- `#f59e0b` - Warning (Orange)
- `#ef4444` - Danger (Merah)
- `#3b82f6` - Info (Biru)

---

## Browser & JavaFX Support

### CSS Web (main.css)
- Modern browsers (Chrome, Firefox, Safari, Edge)
- Responsive design
- Mobile-friendly

### JavaFX CSS (javafx.css)
- JavaFX 17+
- Subset dari CSS standard
- Vendor-specific properties (`-fx-*`)

---

## Tips & Best Practices

1. **Gunakan CSS Classes** - Lebih mudah di-maintain daripada inline styles
2. **Responsive First** - Design untuk mobile dulu, baru expand ke desktop
3. **Konsisten Spacing** - Gunakan multiples dari base unit (8px, 16px, etc)
4. **Meaningful Colors** - Gunakan colors untuk indicate status/importance
5. **Accessibility** - Pastikan contrast ratio sufficient untuk text
6. **Performance** - Minimize inline styles, gunakan external stylesheets

---

## Troubleshooting

### Styles tidak muncul di JavaFX
- Pastikan path stylesheet benar
- Periksa bahwa resource ada di `src/main/resources`
- JavaFX CSS syntax berbeda dari web CSS

### Border radius tidak bekerja
- Gunakan `-fx-border-radius` untuk JavaFX
- Gunakan `-fx-background-radius` untuk background

### Shadow effect tidak smooth
- Gunakan `dropshadow(gaussian, ...)` untuk smooth
- Adjust blur radius untuk kontrol kedalaman

---

## File References

- **main.css** (3800+ lines) - Complete web stylesheet
- **javafx.css** (2000+ lines) - JavaFX specific styles
- **Responsive breakpoints** - 768px, 480px

Total styling coverage untuk semua komponen utama aplikasi Agri-POS.
>>>>>>> 00b052a34e5245d8a8aa00af34917358ef8208d8
