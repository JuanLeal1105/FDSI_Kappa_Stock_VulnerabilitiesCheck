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
@Document(collection = "stock_movements")
public class StockMovement {

    @Id
    private String id;

    private String productId;
    private String productName;

    private StockOperationType operationType;

    private Integer previousStock;
    private Integer amountChanged;
    private Integer newStock;

    private String reason;
    private String performedBy;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}