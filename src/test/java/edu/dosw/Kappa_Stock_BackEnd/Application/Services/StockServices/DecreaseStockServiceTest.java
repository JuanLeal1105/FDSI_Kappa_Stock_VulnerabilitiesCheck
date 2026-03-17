package edu.dosw.Kappa_Stock_BackEnd.Application.Services.StockServices;

import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.ProductResponse;
import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.command.StockCommands.StockOperationCommand;
import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.ProductRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.StockAlertRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.StockMovementRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.Product;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockAlert;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockAlertStatus;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockAlertType;
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
class DecreaseStockServiceTest {

    @Mock
    ProductRepositoryPort productRepository;

    @Mock
    StockMovementRepositoryPort stockMovementRepository;

    @Mock
    StockAlertRepositoryPort stockAlertRepository;

    @InjectMocks
    DecreaseStockService decreaseStockService;

    @Test
    void decreaseStock_createsLowStockAlertAndRecordsMovement() {
        Product p = Product.builder()
                .id("p2")
                .name("Prod2")
                .stock(10)
                .minStockLevel(5)
                .price(new BigDecimal("5.00"))
                .build();

        when(productRepository.findById("p2")).thenReturn(Optional.of(p));
        when(productRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(stockAlertRepository.findActiveAlertByProductId("p2")).thenReturn(Optional.empty());

        StockOperationCommand cmd = new StockOperationCommand("p2", 6, "sale", StockOperationType.SALE);

        ProductResponse resp = decreaseStockService.decreaseStock(cmd);

        assertNotNull(resp);
        assertEquals(4, resp.getStock());

        // alert should be created with LOW_STOCK
        verify(stockAlertRepository, times(1)).save(argThat(alert ->
                alert.getAlertType() == StockAlertType.LOW_STOCK && alert.getCurrentStock() == 4
        ));

        verify(stockMovementRepository, times(1)).save(any());
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void decreaseStock_insufficientStock_throws() {
        Product p = Product.builder().id("p3").stock(3).build();
        when(productRepository.findById("p3")).thenReturn(Optional.of(p));

        StockOperationCommand cmd = new StockOperationCommand("p3", 5, "sale", StockOperationType.SALE);

        assertThrows(ExpressionException.class, () -> decreaseStockService.decreaseStock(cmd));

        verify(stockMovementRepository, never()).save(any());
    }
}
