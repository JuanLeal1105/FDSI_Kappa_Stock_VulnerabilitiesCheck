package edu.dosw.Kappa_Stock_BackEnd.Infrastructure.Persistence;

import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.StockMovementRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockMovement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StockMovementMongoRepositoryAdapter implements StockMovementRepositoryPort {

    private final MongoStockMovementRepository mongoRepository;

    @Override
    public StockMovement save(StockMovement movement) {
        return mongoRepository.save(movement);
    }

    @Override
    public List<StockMovement> findByProductId(String productId) {
        return mongoRepository.findByProductId(productId);
    }
    @Override
    public List<StockMovement> findByProductIdAndTimestampBetween(String productId, LocalDateTime start, LocalDateTime end){
        return mongoRepository.findByProductIdAndTimestampBetween(productId, start, end);
    }
    @Override
    public List<StockMovement> findByTimestampAfter(LocalDateTime timestamp){
        return mongoRepository.findByTimestampAfter(timestamp);
    }
}
