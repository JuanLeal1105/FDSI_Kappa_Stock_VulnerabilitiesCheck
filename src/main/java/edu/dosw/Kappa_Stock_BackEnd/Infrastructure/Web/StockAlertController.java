package edu.dosw.Kappa_Stock_BackEnd.Infrastructure.Web;

import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockAlert;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockAlertStatus;
import edu.dosw.Kappa_Stock_BackEnd.Application.Services.StockUseCases.GetStockAlertsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-alerts")
@RequiredArgsConstructor
@Tag(name = "Stock Alerts", description = "Endpoints for viewing stock alerts")
public class StockAlertController {

    private final GetStockAlertsUseCase getStockAlertsUseCase;

    @Operation(summary = "Get all active stock alerts")
    @GetMapping("/active")
    public ResponseEntity<List<StockAlert>> getActiveAlerts() {
        List<StockAlert> alerts =
                getStockAlertsUseCase.getAlertsByStatus(StockAlertStatus.ACTIVE);
        return ResponseEntity.ok(alerts);
    }

    @Operation(summary = "Get alerts for specific product")
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<StockAlert>> getProductAlerts(
            @PathVariable String productId) {
        List<StockAlert> alerts =
                getStockAlertsUseCase.getAlertsByProductId(productId);
        return ResponseEntity.ok(alerts);
    }
}