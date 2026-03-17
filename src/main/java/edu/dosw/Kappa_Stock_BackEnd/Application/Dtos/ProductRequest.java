package edu.dosw.Kappa_Stock_BackEnd.Application.Dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Schema(description = "Data transfer object for product requests")
public class ProductRequest {

    @Schema(
            description = "Name of the product",
            example = "Pan de bono"
    )
    @NotBlank
    private String name;

    @Schema(
            description = "Price of the product",
            example = "12000.00"
    )
    @Positive
    private BigDecimal price;

    @Schema(
            description = "Description of the product",
            example = "Tipo de pan colombiano elaborado con almidón de yuca, queso y huevos"
    )
    private String description;

    @Schema(
            description = "Category of the product",
            example = "Pan"
    )
    private String category;

    @Schema(description = "Image URL of the product",
            example = "https://example.com/pan.jpg")
    private String imageUrl;

    @Schema(description = "Preparation time in minutes", example = "15")
    @Positive(message = "Preparation time must be positive")
    private Integer preparationTime;


    public ProductRequest(String name, java.math.BigDecimal price, String description, String category, String imageUrl, Integer preparationTime) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
        this.preparationTime = preparationTime;
    }

    public ProductRequest() {

    }
}
