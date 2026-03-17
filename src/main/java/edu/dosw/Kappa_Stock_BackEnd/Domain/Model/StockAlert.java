package edu.dosw.Kappa_Stock_BackEnd.Domain.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "stock_alerts")
public class StockAlert {

    @Id
    private String id;

    private String productId;
    private String productName;

    private StockAlertType alertType;

    private Integer currentStock;
    private Integer minStockLevel;

    @Builder.Default
    private StockAlertStatus status = StockAlertStatus.ACTIVE;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime resolvedAt;
    private String resolvedBy;
}