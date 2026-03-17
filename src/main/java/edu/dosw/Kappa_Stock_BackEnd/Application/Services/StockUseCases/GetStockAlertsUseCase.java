package edu.dosw.Kappa_Stock_BackEnd.Application.Services.StockUseCases;

import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockAlert;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockAlertStatus;

import java.util.List;

public interface GetStockAlertsUseCase {
    List<StockAlert> getAlertsByStatus(StockAlertStatus status);
    List<StockAlert> getAlertsByProductId(String productId);
}