package edu.dosw.Kappa_Stock_BackEnd.Config;

import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.*;
import edu.dosw.Kappa_Stock_BackEnd.Application.Services.ProductServices.*;
import edu.dosw.Kappa_Stock_BackEnd.Application.Services.StockServices.*;
import edu.dosw.Kappa_Stock_BackEnd.Application.Services.ProductUseCases.*;
import edu.dosw.Kappa_Stock_BackEnd.Application.Services.StockUseCases.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ProductRepositoryPort productRepository;
    private final StockMovementRepositoryPort stockMovementRepository;
    private final StockAlertRepositoryPort stockAlertRepository;

    // Product Use Cases
    @Bean
    public CreateProductUseCase createProductUseCase() {
        return new CreateProductService(productRepository);
    }

    @Bean
    public GetProductByIdUseCase getProductByIdUseCase() {
        return new GetProductByIdService(productRepository);
    }

    @Bean
    public GetAllProductsUseCase getAllProductsUseCase() {
        return new GetAllProductsService(productRepository);
    }

    @Bean
    public UpdateProductUseCase updateProductUseCase() {
        return new UpdateProductService(productRepository);
    }

    @Bean
    public DeleteProductUseCase deleteProductUseCase() {
        return new DeleteProductService(productRepository);
    }

    // Stock Use Cases
    @Bean
    public IncreaseStockUseCase increaseStockUseCase() {
        return new IncreaseStockService(
                productRepository,
                stockMovementRepository,
                stockAlertRepository
        );
    }

    @Bean
    public DecreaseStockUseCase decreaseStockUseCase() {
        return new DecreaseStockService(
                productRepository,
                stockMovementRepository,
                stockAlertRepository
        );
    }
}