package edu.dosw.Kappa_Stock_BackEnd.Application.Services.ProductServices;

import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.ProductResponse;
import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.ProductRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Application.Services.ProductUseCases.GetProductByIdUseCase;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GetProductByIdService implements GetProductByIdUseCase {

    private final ProductRepositoryPort productRepository;

    @Override
    public ProductResponse getProductById(String id) {
        Optional<Product> product = productRepository.findById(id);

        return ProductResponse.from(product.get());
    }
}
