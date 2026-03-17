package edu.dosw.Kappa_Stock_BackEnd.Application.Ports;

import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {

    Product save(Product product);

    Optional<Product> findById(String id);

    List<Product> findAll();

    void deleteById(String id);

    boolean existsById(String id);

    boolean existsByName(String name);


}
