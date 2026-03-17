package edu.dosw.Kappa_Stock_BackEnd.Domain.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StockMovementTest {

    private StockMovement stockMovement;

    @BeforeEach
    void setUp() {
        stockMovement = StockMovement.builder()
                .id("1")
                .productId("prod1")
                .productName("Test Product")
                .operationType(StockOperationType.RESTOCK)
                .previousStock(50)
                .amountChanged(25)
                .newStock(75)
                .reason("Monthly restock")
                .performedBy("admin")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Test
    void builder_ShouldCreateStockMovementWithDefaultTimestamp() {
        StockMovement movement = StockMovement.builder()
                .productId("prod1")
                .productName("Product")
                .build();

        assertNotNull(movement);
        assertNotNull(movement.getTimestamp());
        assertTrue(movement.getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void builder_ShouldCreateStockMovementWithAllFields() {
        LocalDateTime timestamp = LocalDateTime.now();

        StockMovement movement = StockMovement.builder()
                .id("2")
                .productId("prod2")
                .productName("Product 2")
                .operationType(StockOperationType.SALE)
                .previousStock(100)
                .amountChanged(20)
                .newStock(80)
                .reason("Customer purchase")
                .performedBy("cashier")
                .timestamp(timestamp)
                .build();

        assertEquals("2", movement.getId());
        assertEquals("prod2", movement.getProductId());
        assertEquals("Product 2", movement.getProductName());
        assertEquals(StockOperationType.SALE, movement.getOperationType());
        assertEquals(100, movement.getPreviousStock());
        assertEquals(20, movement.getAmountChanged());
        assertEquals(80, movement.getNewStock());
        assertEquals("Customer purchase", movement.getReason());
        assertEquals("cashier", movement.getPerformedBy());
        assertEquals(timestamp, movement.getTimestamp());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        LocalDateTime newTimestamp = LocalDateTime.now().plusDays(1);

        stockMovement.setId("999");
        stockMovement.setProductId("prod999");
        stockMovement.setProductName("Updated Product");
        stockMovement.setOperationType(StockOperationType.DAMAGE);
        stockMovement.setPreviousStock(200);
        stockMovement.setAmountChanged(50);
        stockMovement.setNewStock(150);
        stockMovement.setReason("Damaged goods");
        stockMovement.setPerformedBy("manager");
        stockMovement.setTimestamp(newTimestamp);

        assertEquals("999", stockMovement.getId());
        assertEquals("prod999", stockMovement.getProductId());
        assertEquals("Updated Product", stockMovement.getProductName());
        assertEquals(StockOperationType.DAMAGE, stockMovement.getOperationType());
        assertEquals(200, stockMovement.getPreviousStock());
        assertEquals(50, stockMovement.getAmountChanged());
        assertEquals(150, stockMovement.getNewStock());
        assertEquals("Damaged goods", stockMovement.getReason());
        assertEquals("manager", stockMovement.getPerformedBy());
        assertEquals(newTimestamp, stockMovement.getTimestamp());
    }

    @Test
    void noArgsConstructor_ShouldCreateEmptyStockMovement() {
        StockMovement movement = new StockMovement();

        assertNotNull(movement);
        assertNull(movement.getId());
        assertNull(movement.getProductId());
        assertNull(movement.getOperationType());
    }

    @Test
    void allArgsConstructor_ShouldCreateStockMovementWithAllFields() {
        LocalDateTime timestamp = LocalDateTime.now();

        StockMovement movement = new StockMovement(
                "3",
                "prod3",
                "Product 3",
                StockOperationType.ADJUSTMENT,
                120,
                30,
                150,
                "Inventory correction",
                "supervisor",
                timestamp
        );

        assertEquals("3", movement.getId());
        assertEquals("prod3", movement.getProductId());
        assertEquals("Product 3", movement.getProductName());
        assertEquals(StockOperationType.ADJUSTMENT, movement.getOperationType());
        assertEquals(120, movement.getPreviousStock());
        assertEquals(30, movement.getAmountChanged());
        assertEquals(150, movement.getNewStock());
        assertEquals("Inventory correction", movement.getReason());
        assertEquals("supervisor", movement.getPerformedBy());
        assertEquals(timestamp, movement.getTimestamp());
    }

    @Test
    void operationTypeEnum_ShouldHaveAllExpectedValues() {
        assertEquals(5, StockOperationType.values().length);
        assertNotNull(StockOperationType.valueOf("RESTOCK"));
        assertNotNull(StockOperationType.valueOf("SALE"));
        assertNotNull(StockOperationType.valueOf("ADJUSTMENT"));
        assertNotNull(StockOperationType.valueOf("DAMAGE"));
        assertNotNull(StockOperationType.valueOf("RETURN"));
    }

    @Test
    void timestamp_ShouldBeSetByDefault() {
        StockMovement movement = StockMovement.builder()
                .productId("test")
                .build();

        assertNotNull(movement.getTimestamp());
    }

    @Test
    void stockMovement_ForRestockOperation_ShouldCalculateCorrectly() {
        StockMovement restock = StockMovement.builder()
                .productId("prod1")
                .operationType(StockOperationType.RESTOCK)
                .previousStock(10)
                .amountChanged(50)
                .newStock(60)
                .build();

        assertEquals(StockOperationType.RESTOCK, restock.getOperationType());
        assertEquals(10, restock.getPreviousStock());
        assertEquals(50, restock.getAmountChanged());
        assertEquals(60, restock.getNewStock());
    }

    @Test
    void stockMovement_ForSaleOperation_ShouldCalculateCorrectly() {
        StockMovement sale = StockMovement.builder()
                .productId("prod1")
                .operationType(StockOperationType.SALE)
                .previousStock(100)
                .amountChanged(15)
                .newStock(85)
                .build();

        assertEquals(StockOperationType.SALE, sale.getOperationType());
        assertEquals(100, sale.getPreviousStock());
        assertEquals(15, sale.getAmountChanged());
        assertEquals(85, sale.getNewStock());
    }

    @Test
    void stockMovement_ForDamageOperation_ShouldRecordCorrectly() {
        StockMovement damage = StockMovement.builder()
                .productId("prod1")
                .operationType(StockOperationType.DAMAGE)
                .previousStock(50)
                .amountChanged(5)
                .newStock(45)
                .reason("Expired products")
                .build();

        assertEquals(StockOperationType.DAMAGE, damage.getOperationType());
        assertEquals("Expired products", damage.getReason());
        assertEquals(45, damage.getNewStock());
    }

    @Test
    void stockMovement_WithNullValues_ShouldHandleGracefully() {
        StockMovement movement = StockMovement.builder()
                .productId("prod1")
                .build();

        assertNotNull(movement);
        assertNull(movement.getReason());
        assertNull(movement.getPerformedBy());
        assertNull(movement.getOperationType());
    }
}