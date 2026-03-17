package edu.dosw.Kappa_Stock_BackEnd.Application.Services.ProductServices;


import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.ProductResponse;
import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.command.ProductCommands.CreateProductCommand;
import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.ProductRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Application.Services.ProductUseCases.CreateProductUseCase;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateProductService implements CreateProductUseCase {

    private final ProductRepositoryPort productRepository;

    @Override
    public ProductResponse createProduct(CreateProductCommand command){
        if (productRepository.existsByName(command.name())){
            throw new RuntimeException();
        }
        Product product = Product.builder()
                .name(command.name())
                .price(command.price())
                .description(command.description())
                .category(command.category())
                .imageUrl(command.imageUrl())
                .preparationTime(command.preparationTime())
                .stock(0)
                .available(command.initialStock() > 0)
                .build();
        Product saved = productRepository.save(product);
        return ProductResponse.from(saved);
    }





}
