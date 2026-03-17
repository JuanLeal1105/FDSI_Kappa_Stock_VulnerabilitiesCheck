package edu.dosw.Kappa_Stock_BackEnd.Application.Ports;

import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockMovement;

import java.time.LocalDateTime;
import java.util.List;

public interface StockMovementRepositoryPort {

    StockMovement save(StockMovement movement);

    List<StockMovement> findByProductId(String productId);

    List<StockMovement> findByProductIdAndTimestampBetween(String productId, LocalDateTime start, LocalDateTime end);

    List<StockMovement> findByTimestampAfter(LocalDateTime timestamp);
}