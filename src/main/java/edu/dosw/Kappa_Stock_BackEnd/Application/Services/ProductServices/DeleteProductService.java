package edu.dosw.Kappa_Stock_BackEnd.Application.Services.ProductServices;


import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.ProductRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Application.Services.ProductUseCases.DeleteProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteProductService implements DeleteProductUseCase {

    private final ProductRepositoryPort productRepository;


    @Override
    public void deleteById(String id) {
        productRepository.findById(id)
                .orElseThrow(() -> new ExpressionException(
                        "Product with id '" + id + "' not found"
                ));

        productRepository.deleteById(id);
    }
}
