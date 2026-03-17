package edu.dosw.Kappa_Stock_BackEnd.Application.Services.ProductServices;

import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.ProductResponse;
import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.command.ProductCommands.UpdateProductCommand;
//import edu.dosw.Kappa_Stock_BackEnd.Application.Exceptions.ProductAlreadyExistsException;
//import edu.dosw.Kappa_Stock_BackEnd.Application.Exceptions.ProductNotFoundException;
import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.ProductRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Application.Services.ProductUseCases.UpdateProductUseCase;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateProductService implements UpdateProductUseCase {

    private final ProductRepositoryPort productRepository;

    @Override
    public ProductResponse updateProduct(UpdateProductCommand command) {
        Product existingProduct = productRepository.findById(command.id())
                .orElseThrow(() -> new ExpressionException(
                        "Product with id '" + command.id() + "' not found"
                ));

        if (command.name() != null &&
                !command.name().equals(existingProduct.getName())) {

            if (productRepository.existsByName(command.name())) {
                throw new ExpressionException(
                        "Product with name '" + command.name() + "' already exists"
                );
            }
        }

        updateProductFields(existingProduct, command);

        Product updatedProduct = productRepository.save(existingProduct);
        return ProductResponse.from(updatedProduct);
    }

    private void updateProductFields(Product product, UpdateProductCommand command) {
        if (command.name() != null) {
            product.setName(command.name());
        }
        if (command.price() != null) {
            product.setPrice(command.price());
        }
        if (command.description() != null) {
            product.setDescription(command.description());
        }
        if (command.category() != null) {
            product.setCategory(command.category());
        }
        if (command.imageUrl() != null) {
            product.setImageUrl(command.imageUrl());
        }
        if (command.preparationTime() != null) {
            product.setPreparationTime(command.preparationTime());
        }
        if (command.available() != null) {
            product.setAvailable(command.available());
        }
        if (command.minStockLevel() != null) {
            product.setMinStockLevel(command.minStockLevel());
        }
    }
}