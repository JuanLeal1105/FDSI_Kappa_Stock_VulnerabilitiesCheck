package edu.dosw.Kappa_Stock_BackEnd.Application.Services.ProductServices;


import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.ProductResponse;
import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.ProductRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Application.Services.ProductUseCases.GetAllProductsUseCase;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetAllProductsService implements GetAllProductsUseCase {

    private final ProductRepositoryPort productRepository;

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductResponse::from).toList();
    }
}
