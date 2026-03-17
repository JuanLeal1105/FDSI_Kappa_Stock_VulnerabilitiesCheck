package edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.command.ProductCommands;

import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.ProductRequest;

import java.math.BigDecimal;

public record CreateProductCommand(
        String name,
        BigDecimal price,
        String description,
        String category,
        String imageUrl,
        Integer preparationTime,
        Integer initialStock,
        Integer minStockLevel
) {
    public static CreateProductCommand fromRequest(ProductRequest request) {
        return new CreateProductCommand(
                request.getName(),
                request.getPrice(),
                request.getDescription(),
                request.getCategory(),
                request.getImageUrl(),
                request.getPreparationTime(),
                0, 5
        );
    }

    public static CreateProductCommand fromRequest(
            ProductRequest request,
            Integer initialStock,
            Integer minStockLevel) {
        return new CreateProductCommand(
                request.getName(),
                request.getPrice(),
                request.getDescription(),
                request.getCategory(),
                request.getImageUrl(),
                request.getPreparationTime(),
                initialStock,
                minStockLevel
        );
    }
}