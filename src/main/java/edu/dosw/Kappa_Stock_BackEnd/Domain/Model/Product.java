package edu.dosw.Kappa_Stock_BackEnd.Domain.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.math.BigDecimal;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String imageUrl;
    private Integer preparationTime;
    private Boolean available;
    private Integer stock;
    private Integer minStockLevel;

    public boolean hasStock() { return stock > 0;}

    public boolean isLowStock() { return stock <= minStockLevel; }

    public void reduceStock(Integer amount) { stock-=amount; }

    public void increaseStock(Integer amount) { stock+=amount; }

}