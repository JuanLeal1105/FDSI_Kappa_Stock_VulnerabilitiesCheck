package edu.dosw.Kappa_Stock_BackEnd.Application.Ports;

import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockAlert;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockAlertStatus;

import java.util.List;
import java.util.Optional;

public interface StockAlertRepositoryPort {

    StockAlert save(StockAlert alert);

    List<StockAlert> findByStatus(StockAlertStatus status);

    List<StockAlert> findByProductIdAndStatus(String productId, StockAlertStatus status);

    Optional<StockAlert> findActiveAlertByProductId(String productId);

    void deleteById(String id);
}