package edu.dosw.Kappa_Stock_BackEnd.Domain.Model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testStockOperationsAndFlags() {
        Product p = Product.builder()
                .id("p1")
                .name("Test Product")
                .stock(10)
                .minStockLevel(5)
                .price(new BigDecimal("9.99"))
                .build();

        // basic getters
        assertEquals(new BigDecimal("9.99"), p.getPrice());
        assertEquals("Test Product", p.getName());

        assertTrue(p.hasStock());
        assertFalse(p.isLowStock());

        p.reduceStock(6);
        assertEquals(4, p.getStock());
        assertTrue(p.isLowStock());

        // reduce to zero and check flags
        p.reduceStock(4);
        assertEquals(0, p.getStock());
        assertFalse(p.hasStock());

        p.increaseStock(10);
        assertEquals(10, p.getStock());
    }

    @Test
    void testBuilderAndSetters() {
        Product p = new Product();
        p.setId("p2");
        p.setName("Setter Product");
        p.setPrice(new BigDecimal("1.00"));
        p.setStock(2);
        p.setMinStockLevel(1);

        assertEquals("p2", p.getId());
        assertEquals("Setter Product", p.getName());
        assertEquals(new BigDecimal("1.00"), p.getPrice());
        assertEquals(2, p.getStock());
        assertEquals(1, p.getMinStockLevel());
    }

    @Test
    void testReduceBelowZero() {
        Product p = Product.builder().stock(1).minStockLevel(0).build();
        p.reduceStock(5);
        assertEquals(-4, p.getStock());
        assertFalse(p.hasStock());
    }
}
