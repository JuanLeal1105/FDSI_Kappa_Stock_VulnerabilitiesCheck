package edu.dosw.Kappa_Stock_BackEnd.Application.Services.StockServices;

import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.StockAlertRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Application.Services.StockUseCases.GetStockAlertsUseCase;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockAlert;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockAlertStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetStockAlertsService implements GetStockAlertsUseCase {

    private final StockAlertRepositoryPort stockAlertRepository;

    @Override
    public List<StockAlert> getAlertsByStatus(StockAlertStatus status) {
        return stockAlertRepository.findByStatus(status);
    }

    @Override
    public List<StockAlert> getAlertsByProductId(String productId) {
        return stockAlertRepository.findByProductIdAndStatus(
                productId,
                StockAlertStatus.ACTIVE
        );
    }
}