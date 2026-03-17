package edu.dosw.Kappa_Stock_BackEnd.Domain.Model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StockAlertTest {

    @Test
    void testDefaultsAndBuilder() {
        StockAlert a = StockAlert.builder()
                .id("a1")
                .productId("p1")
                .productName("Test Product")
                .alertType(StockAlertType.LOW_STOCK)
                .currentStock(3)
                .minStockLevel(5)
                .build();

        // basic fields
        assertEquals("p1", a.getProductId());
        assertEquals("Test Product", a.getProductName());
        assertEquals(StockAlertType.LOW_STOCK, a.getAlertType());
        assertEquals(3, a.getCurrentStock());
        assertEquals(5, a.getMinStockLevel());

        assertEquals(StockAlertStatus.ACTIVE, a.getStatus());
        assertNotNull(a.getCreatedAt());
        assertNull(a.getResolvedAt());

        a.setStatus(StockAlertStatus.RESOLVED);
        a.setResolvedBy("tester");
        a.setResolvedAt(LocalDateTime.now());

        assertEquals(StockAlertStatus.RESOLVED, a.getStatus());
        assertEquals("tester", a.getResolvedBy());
        assertNotNull(a.getResolvedAt());
    }

    @Test
    void testStatusTransitionsAndDates() {
        StockAlert a = StockAlert.builder()
                .id("a2")
                .productId("p2")
                .productName("Prod 2")
                .alertType(StockAlertType.OUT_OF_STOCK)
                .currentStock(0)
                .minStockLevel(1)
                .build();

        // simulate created earlier and then resolved later
        LocalDateTime created = LocalDateTime.now().minusDays(1);
        a.setCreatedAt(created);
        a.setStatus(StockAlertStatus.ACKNOWLEDGED);

        LocalDateTime resolved = LocalDateTime.now();
        a.setResolvedAt(resolved);
        a.setResolvedBy("admin");
        a.setStatus(StockAlertStatus.RESOLVED);

        assertEquals(StockAlertStatus.RESOLVED, a.getStatus());
        assertEquals("admin", a.getResolvedBy());
        assertTrue(a.getCreatedAt().isBefore(a.getResolvedAt()) || a.getCreatedAt().isEqual(a.getResolvedAt()));
    }
}
