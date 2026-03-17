package edu.dosw.Kappa_Stock_BackEnd.Application.Dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Data transfer object for updating products")
public class UpdateProductRequest {

    @Schema(description = "Name of the product", example = "Pan de bono")
    private String name;

    @Schema(description = "Price of the product", example = "12000.00")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @Schema(description = "Description of the product",
            example = "Tipo de pan colombiano elaborado con almidón de yuca")
    private String description;

    @Schema(description = "Category of the product", example = "Pan")
    private String category;

    @Schema(description = "Image URL of the product",
            example = "https://example.com/pan.jpg")
    private String imageUrl;

    @Schema(description = "Preparation time in minutes", example = "15")
    @Positive(message = "Preparation time must be positive")
    private Integer preparationTime;

    @Schema(description = "Availability of the product", example = "true")
    private Boolean available;

    @Schema(description = "Minimum stock level", example = "5")
    @Positive(message = "Minimum stock level must be positive")
    private Integer minStockLevel;
}