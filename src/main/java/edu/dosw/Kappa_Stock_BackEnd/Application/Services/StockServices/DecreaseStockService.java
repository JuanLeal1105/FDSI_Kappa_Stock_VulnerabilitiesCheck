package edu.dosw.Kappa_Stock_BackEnd.Application.Services.StockServices;

import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.ProductResponse;
import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.command.StockCommands.StockOperationCommand;
//import edu.dosw.Kappa_Stock_BackEnd.Application.Exceptions.InsufficientStockException;
//import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.ProductRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.ProductRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.StockAlertRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.StockMovementRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Application.Services.StockUseCases.DecreaseStockUseCase;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class DecreaseStockService implements DecreaseStockUseCase {

    private final ProductRepositoryPort productRepository;
    private final StockMovementRepositoryPort stockMovementRepository;
    private final StockAlertRepositoryPort stockAlertRepository;

    @Override
    @Transactional
    public ProductResponse decreaseStock(StockOperationCommand command) {
        Product product = productRepository.findById(command.productId())
                .orElseThrow(() -> new ExpressionException(
                        "Product with id '" + command.productId() + "' not found"
                ));

        if (product.getStock() < command.amount()) {
            throw new ExpressionException(
                    "Insufficient stock for product '" + product.getName() + "'. " +
                            "Available: " + product.getStock() + ", Required: " + command.amount()
            );
        }

        Integer previousStock = product.getStock();

        product.reduceStock(command.amount());

        if (product.getStock() == 0) {
            product.setAvailable(false);
        }

        createAlertIfNecessary(product);

        Product savedProduct = productRepository.save(product);

        recordStockMovement(product, command, previousStock);

        return ProductResponse.from(savedProduct);
    }

    private void recordStockMovement(
            Product product,
            StockOperationCommand command,
            Integer previousStock) {

        StockMovement movement = StockMovement.builder()
                .productId(product.getId())
                .productName(product.getName())
                .operationType(command.operationType())
                .previousStock(previousStock)
                .amountChanged(-command.amount())
                .newStock(product.getStock())
                .reason(command.reason())
                .timestamp(LocalDateTime.now())
                .build();

        stockMovementRepository.save(movement);
    }

    private void createAlertIfNecessary(Product product) {
        if (stockAlertRepository.findActiveAlertByProductId(product.getId()).isPresent()) {
            return;
        }

        StockAlertType alertType = null;

        if (product.getStock() == 0) {
            alertType = StockAlertType.OUT_OF_STOCK;
        } else if (product.getStock() <= 2) {
            alertType = StockAlertType.CRITICAL_STOCK;
        } else if (product.getMinStockLevel() != null &&
                product.isLowStock()) {
            alertType = StockAlertType.LOW_STOCK;
        }

        if (alertType != null) {
            StockAlert alert = StockAlert.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .alertType(alertType)
                    .currentStock(product.getStock())
                    .minStockLevel(product.getMinStockLevel())
                    .status(StockAlertStatus.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .build();

            stockAlertRepository.save(alert);
        }
    }
}