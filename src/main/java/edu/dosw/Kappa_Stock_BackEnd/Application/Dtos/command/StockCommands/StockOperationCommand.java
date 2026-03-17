package edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.command.StockCommands;

import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.StockOperationRequest;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockOperationType;

public record StockOperationCommand(
        String productId,
        Integer amount,
        String reason,
        StockOperationType operationType
) {
    public static StockOperationCommand fromRequest(
            String productId,
            StockOperationRequest request) {
        return new StockOperationCommand(
                productId,
                request.getAmount(),
                request.getReason(),
                request.getOperationType()
        );
    }
}