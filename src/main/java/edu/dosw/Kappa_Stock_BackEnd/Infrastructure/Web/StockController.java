package edu.dosw.Kappa_Stock_BackEnd.Infrastructure.Web;

import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.ProductResponse;
import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.StockOperationRequest;
import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.command.StockCommands.StockOperationCommand;
import edu.dosw.Kappa_Stock_BackEnd.Application.Services.StockUseCases.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products/{productId}/stock")
@RequiredArgsConstructor
@Tag(name = "Stock Management", description = "Endpoints for managing product stock")
public class StockController {

    private final IncreaseStockUseCase increaseStockUseCase;
    private final DecreaseStockUseCase decreaseStockUseCase;

    @Operation(summary = "Increase product stock",
            description = "Add stock to a product (restock, returns, etc.)")
    @PostMapping("/increase")
    public ResponseEntity<ProductResponse> increaseStock(
            @PathVariable String productId,
            @Valid @RequestBody StockOperationRequest request) {

        StockOperationCommand command =
                StockOperationCommand.fromRequest(productId, request);

        ProductResponse response = increaseStockUseCase.increaseStock(command);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Decrease product stock",
            description = "Remove stock from a product (sales, damage, etc.)")
    @PostMapping("/decrease")
    public ResponseEntity<ProductResponse> decreaseStock(

            @PathVariable("productId") String productId,
            @Valid @RequestBody StockOperationRequest request) {
        System.out.println("HIT decreaseStock | productId=" + productId
                + " amount=" + request.getAmount());
        StockOperationCommand command =
                StockOperationCommand.fromRequest(productId, request);

        ProductResponse response = decreaseStockUseCase.decreaseStock(command);

        return ResponseEntity.ok(response);
    }
}