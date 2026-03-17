package edu.dosw.Kappa_Stock_BackEnd.Application.Services.StockServices;

import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.ProductResponse;
import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.command.StockCommands.StockOperationCommand;
//import edu.dosw.Kappa_Stock_BackEnd.Application.Exceptions.ProductNotFoundException;
import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.ProductRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.StockAlertRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.StockMovementRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Application.Services.StockUseCases.IncreaseStockUseCase;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class IncreaseStockService implements IncreaseStockUseCase {

    private final ProductRepositoryPort productRepository;
    private final StockMovementRepositoryPort stockMovementRepository;
    private final StockAlertRepositoryPort stockAlertRepository;

    @Override
    @Transactional
    public ProductResponse increaseStock(StockOperationCommand command) {
        Product product = productRepository.findById(command.productId())
                .orElseThrow(() -> new ExpressionException(
                        "Product with id '" + command.productId() + "' not found"
                ));

        Integer previousStock = product.getStock();

        product.increaseStock(command.amount());

        if (product.getStock() > 0) {
            product.setAvailable(true);
        }

        resolveAlertsIfNecessary(product);

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
                .amountChanged(command.amount())
                .newStock(product.getStock())
                .reason(command.reason())
                .timestamp(LocalDateTime.now())
                .build();

        stockMovementRepository.save(movement);
    }

    private void resolveAlertsIfNecessary(Product product) {
        if (product.getMinStockLevel() != null &&
                product.getStock() > product.getMinStockLevel()) {

            stockAlertRepository.findActiveAlertByProductId(product.getId())
                    .ifPresent(alert -> {
                        alert.setStatus(StockAlertStatus.RESOLVED);
                        alert.setResolvedAt(LocalDateTime.now());
                        stockAlertRepository.save(alert);
                    });
        }
    }
}