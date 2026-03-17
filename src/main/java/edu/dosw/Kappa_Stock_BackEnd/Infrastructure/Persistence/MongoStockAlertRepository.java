package edu.dosw.Kappa_Stock_BackEnd.Infrastructure.Persistence;

import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockAlert;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockAlertStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MongoStockAlertRepository extends MongoRepository<StockAlert, String> {

    List<StockAlert> findByStatus(StockAlertStatus status);

    List<StockAlert> findByProductIdAndStatus(
            String productId,
            StockAlertStatus status
    );

    @Query("{'productId': ?0, 'status': 'ACTIVE'}")
    Optional<StockAlert> findActiveAlertByProductId(String productId);
}