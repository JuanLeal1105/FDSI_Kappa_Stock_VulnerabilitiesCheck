package edu.dosw.Kappa_Stock_BackEnd.Infrastructure.Persistence;

import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoProductRepository extends MongoRepository<Product, String> {
    boolean existsByName(String name);
}