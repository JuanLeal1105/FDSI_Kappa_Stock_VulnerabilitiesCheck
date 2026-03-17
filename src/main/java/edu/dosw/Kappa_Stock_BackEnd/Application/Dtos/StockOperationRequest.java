package edu.dosw.Kappa_Stock_BackEnd.Application.Dtos;

import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockOperationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Schema(description = "Request to modify product stock")
public class StockOperationRequest {

    @Schema(description = "Amount to add/remove", example = "50")
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Integer amount;

    @Schema(description = "Reason for stock change",
            example = "Restock from supplier")
    private String reason;

    @Schema(description = "Operation type: RESTOCK, SALE, ADJUSTMENT, DAMAGE, RETURN",
            example = "RESTOCK")
    @NotNull(message = "Operation type is required")
    private StockOperationType operationType;
}