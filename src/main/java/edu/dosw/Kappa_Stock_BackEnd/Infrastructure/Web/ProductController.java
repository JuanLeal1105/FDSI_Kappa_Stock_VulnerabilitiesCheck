package edu.dosw.Kappa_Stock_BackEnd.Infrastructure.Web;


import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.ProductRequest;
import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.ProductResponse;
import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.UpdateProductRequest;
import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.command.ProductCommands.CreateProductCommand;
import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.command.ProductCommands.UpdateProductCommand;
import edu.dosw.Kappa_Stock_BackEnd.Application.Services.ProductUseCases.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final GetAllProductsUseCase getAllProductsUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest request) {

        CreateProductCommand command = CreateProductCommand.fromRequest(request);
        ProductResponse response = createProductUseCase.createProduct(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductBy(@PathVariable String id){
        ProductResponse response = getProductByIdUseCase.getProductById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> productsResponse = getAllProductsUseCase.getAllProducts();
        return ResponseEntity.ok(productsResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        deleteProductUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable String id,
            @Valid @RequestBody UpdateProductRequest request) {

        UpdateProductCommand command = UpdateProductCommand.fromRequest(id, request);
        ProductResponse response = updateProductUseCase.updateProduct(command);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> partiallyUpdateProduct(
            @PathVariable String id,
            @Valid @RequestBody UpdateProductRequest request) {

        UpdateProductCommand command = UpdateProductCommand.fromRequest(id, request);
        ProductResponse response = updateProductUseCase.updateProduct(command);

        return ResponseEntity.ok(response);
    }

}
