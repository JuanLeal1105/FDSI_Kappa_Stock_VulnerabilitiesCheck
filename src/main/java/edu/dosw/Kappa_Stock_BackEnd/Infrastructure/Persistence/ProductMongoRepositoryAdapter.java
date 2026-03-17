package edu.dosw.Kappa_Stock_BackEnd.Infrastructure.Persistence;

import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.ProductRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductMongoRepositoryAdapter implements ProductRepositoryPort {

    private final MongoProductRepository mongoProductRepository;

    @Override
    public Product save(Product product){
        return mongoProductRepository.save(product);
    }

    @Override
    public Optional<Product> findById(String id) {
        return mongoProductRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return mongoProductRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        mongoProductRepository.deleteById(id);
    }

    @Override
    public boolean existsById(String id) {
        return mongoProductRepository.existsById(id);
    }


    @Override
    public boolean existsByName(String name) {
        return mongoProductRepository.existsByName(name);
    }

}
