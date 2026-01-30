package com.upb.agripos.service;

import com.upb.agripos.model.Product;
import com.upb.agripos.model.Cart;
import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.exception.OutOfStockException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test untuk CartService.
 */
public class CartServiceTest {
    private Cart cart;

    @Before
    public void setUp() {
        cart = new Cart();
    }

    @Test
    public void testAddItemToCart() {
        Product product = new Product(1, "P001", "Beras", "Biji-bijian", 50000, 100);
        cart.addItem(product, 2);

        assertEquals(1, cart.getItems().size());
        assertEquals(2, cart.getItems().get(0).getQuantity());
    }

    @Test
    public void testAddDuplicateProduct() {
        Product product = new Product(1, "P001", "Beras", "Biji-bijian", 50000, 100);
        cart.addItem(product, 2);
        cart.addItem(product, 3);

        assertEquals(1, cart.getItems().size());
        assertEquals(5, cart.getItems().get(0).getQuantity());
    }

    @Test
    public void testRemoveItemFromCart() {
        Product product1 = new Product(1, "P001", "Beras", "Biji-bijian", 50000, 100);
        Product product2 = new Product(2, "P002", "Jagung", "Biji-bijian", 30000, 150);

        cart.addItem(product1, 2);
        cart.addItem(product2, 3);

        assertEquals(2, cart.getItems().size());

        cart.removeItem(1);
        assertEquals(1, cart.getItems().size());
        assertEquals(2, cart.getItems().get(0).getProduct().getId());
    }

    @Test
    public void testGetCartTotal() {
        Product product1 = new Product(1, "P001", "Beras", "Biji-bijian", 50000, 100);
        Product product2 = new Product(2, "P002", "Jagung", "Biji-bijian", 30000, 150);

        cart.addItem(product1, 2);   // 2 * 50000 = 100000
        cart.addItem(product2, 3);   // 3 * 30000 = 90000
        // Total = 190000

        assertEquals(190000, cart.getTotal(), 0.01);
    }

    @Test
    public void testUpdateItemQuantity() {
        Product product = new Product(1, "P001", "Beras", "Biji-bijian", 50000, 100);
        cart.addItem(product, 2);

        assertEquals(2, cart.getItems().get(0).getQuantity());

        cart.updateItemQuantity(1, 5);
        assertEquals(5, cart.getItems().get(0).getQuantity());
    }

    @Test
    public void testRemoveItemByUpdatingQuantityToZero() {
        Product product = new Product(1, "P001", "Beras", "Biji-bijian", 50000, 100);
        cart.addItem(product, 2);

        assertEquals(1, cart.getItems().size());

        cart.updateItemQuantity(1, 0);
        assertEquals(0, cart.getItems().size());
    }

    @Test
    public void testClearCart() {
        Product product1 = new Product(1, "P001", "Beras", "Biji-bijian", 50000, 100);
        Product product2 = new Product(2, "P002", "Jagung", "Biji-bijian", 30000, 150);

        cart.addItem(product1, 2);
        cart.addItem(product2, 3);

        assertEquals(2, cart.getItems().size());

        cart.clear();
        assertEquals(0, cart.getItems().size());
        assertEquals(0, cart.getTotal(), 0.01);
    }

    @Test
    public void testGetTotalQuantity() {
        Product product1 = new Product(1, "P001", "Beras", "Biji-bijian", 50000, 100);
        Product product2 = new Product(2, "P002", "Jagung", "Biji-bijian", 30000, 150);

        cart.addItem(product1, 5);
        cart.addItem(product2, 3);

        assertEquals(8, cart.getTotalQuantity());
    }

    @Test
    public void testPaymentValidationCash() {
        PaymentMethod cashPayment = new CashPayment();

        // Valid payment
        assertTrue(cashPayment.validatePayment(100000, 80000));

        // Invalid payment (exact amount)
        assertTrue(cashPayment.validatePayment(80000, 80000));

        // Invalid payment (insufficient)
        assertFalse(cashPayment.validatePayment(50000, 80000));
    }

    @Test
    public void testPaymentCalculateChangeCash() {
        PaymentMethod cashPayment = new CashPayment();
        double change = cashPayment.calculateChange(100000, 80000);

        assertEquals(20000, change, 0.01);
    }

    @Test
    public void testPaymentValidationEWallet() {
        PaymentMethod eWalletPayment = new EWalletPayment();

        // Valid payment
        assertTrue(eWalletPayment.validatePayment(100000, 80000));

        // Valid payment (exact amount)
        assertTrue(eWalletPayment.validatePayment(80000, 80000));

        // Invalid payment
        assertFalse(eWalletPayment.validatePayment(50000, 80000));
    }

    @Test
    public void testPaymentCalculateChangeEWallet() {
        PaymentMethod eWalletPayment = new EWalletPayment();
        double change = eWalletPayment.calculateChange(100000, 80000);

        // E-Wallet tidak ada kembalian
        assertEquals(0, change, 0.01);
    }
}
