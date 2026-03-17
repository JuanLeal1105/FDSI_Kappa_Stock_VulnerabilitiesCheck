package edu.dosw.Kappa_Stock_BackEnd.Application.Dtos;


import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "Data transfer object for product responses")
public class ProductResponse {

    @Schema(description = "ID of the product", example = "64ab1234567890abcdef1234")
    private String id;

    @Schema(description = "Name of the product", example = "Pan de bono")
    private String name;

    @Schema(description = "Price of the product", example = "12000.00")
    private BigDecimal price;

    @Schema(description = "Description of the product", example = "Tipo de pan colombiano con queso y huevos")
    private String description;

    @Schema(description = "Category of the product", example = "Pan")
    private String category;

    @Schema(description = "Image URL of the product", example = "https://example.com/pan.jpg")
    private String imageUrl;

    @Schema(description = "Preparation time in minutes", example = "15")
    private Integer preparationTime;

    @Schema(description = "Current stock of the product", example = "50")
    private Integer stock;

    @Schema(description = "Availability of the product", example = "true")
    private Boolean available;

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .category(product.getCategory())
                .imageUrl(product.getImageUrl())
                .preparationTime(product.getPreparationTime())
                .stock(product.getStock())
                .available(product.getAvailable())
                .build();
    }
}
