package edu.dosw.Kappa_Stock_BackEnd.Application.Services.StockServices;

import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.ProductResponse;
import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.command.StockCommands.StockOperationCommand;
import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.ProductRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.StockAlertRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.StockMovementRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.Product;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockAlert;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockAlertStatus;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockOperationType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.expression.ExpressionException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncreaseStockServiceTest {

    @Mock
    ProductRepositoryPort productRepository;

    @Mock
    StockMovementRepositoryPort stockMovementRepository;

    @Mock
    StockAlertRepositoryPort stockAlertRepository;

    @InjectMocks
    IncreaseStockService increaseStockService;

    @Test
    void increaseStock_resolvesAlertAndRecordsMovement() {
        Product p = Product.builder()
                .id("p1")
                .name("Prod")
                .stock(2)
                .minStockLevel(1)
                .price(new BigDecimal("10.00"))
                .build();

        when(productRepository.findById("p1")).thenReturn(Optional.of(p));
        when(productRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        StockAlert alert = StockAlert.builder()
                .id("a1")
                .productId("p1")
                .productName("Prod")
                .currentStock(0)
                .minStockLevel(1)
                .status(StockAlertStatus.ACTIVE)
                .build();

        when(stockAlertRepository.findActiveAlertByProductId("p1")).thenReturn(Optional.of(alert));

        StockOperationCommand cmd = new StockOperationCommand("p1", 5, "restock", StockOperationType.RESTOCK);

        ProductResponse resp = increaseStockService.increaseStock(cmd);

        assertNotNull(resp);
        assertEquals(7, resp.getStock());

        // alert should be resolved and saved
        verify(stockAlertRepository, times(1)).save(argThat(a -> a.getStatus() == StockAlertStatus.RESOLVED));

        // movement should be recorded
        verify(stockMovementRepository, times(1)).save(any());

        // product saved
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void increaseStock_productNotFound_throws() {
        when(productRepository.findById("missing")).thenReturn(Optional.empty());
        StockOperationCommand cmd = new StockOperationCommand("missing", 1, "x", StockOperationType.RESTOCK);
        assertThrows(ExpressionException.class, () -> increaseStockService.increaseStock(cmd));
    }
}
