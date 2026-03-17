package edu.dosw.Kappa_Stock_BackEnd.Infrastructure.Persistence;

import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockMovement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MongoStockMovementRepository extends MongoRepository<StockMovement, String> {

    List<StockMovement> findByProductId(String productId);

    List<StockMovement> findByProductIdAndTimestampBetween(
            String productId,
            LocalDateTime start,
            LocalDateTime end
    );

    List<StockMovement> findByTimestampAfter(LocalDateTime timestamp);
}