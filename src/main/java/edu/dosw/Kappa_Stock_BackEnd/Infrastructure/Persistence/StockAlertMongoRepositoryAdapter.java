package edu.dosw.Kappa_Stock_BackEnd.Infrastructure.Persistence;

import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.StockAlertRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockAlert;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockAlertStatus;
import edu.dosw.Kappa_Stock_BackEnd.Infrastructure.Persistence.MongoStockAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StockAlertMongoRepositoryAdapter implements StockAlertRepositoryPort {

    private final MongoStockAlertRepository mongoStockAlertRepository;

    @Override
    public StockAlert save(StockAlert alert) {
        return mongoStockAlertRepository.save(alert);
    }

    @Override
    public List<StockAlert> findByStatus(StockAlertStatus status) {
        return mongoStockAlertRepository.findByStatus(status);
    }

    @Override
    public List<StockAlert> findByProductIdAndStatus(String productId, StockAlertStatus status) {
        return mongoStockAlertRepository.findByProductIdAndStatus(productId, status);
    }

    @Override
    public Optional<StockAlert> findActiveAlertByProductId(String productId) {
        return mongoStockAlertRepository.findActiveAlertByProductId(productId);
    }

    @Override
    public void deleteById(String id) {
        mongoStockAlertRepository.deleteById(id);
    }
}