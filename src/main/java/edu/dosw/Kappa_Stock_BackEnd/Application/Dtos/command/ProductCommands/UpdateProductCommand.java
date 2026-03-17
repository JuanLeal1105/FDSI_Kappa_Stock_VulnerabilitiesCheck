package edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.command.ProductCommands;

import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.UpdateProductRequest;

import java.math.BigDecimal;

public record UpdateProductCommand(
        String id,
        String name,
        BigDecimal price,
        String description,
        String category,
        String imageUrl,
        Integer preparationTime,
        Boolean available,
        Integer minStockLevel
) {
    public static UpdateProductCommand fromRequest(String id, UpdateProductRequest request) {
        return new UpdateProductCommand(
                id,
                request.getName(),
                request.getPrice(),
                request.getDescription(),
                request.getCategory(),
                request.getImageUrl(),
                request.getPreparationTime(),
                request.getAvailable(),
                request.getMinStockLevel()
        );
    }
}