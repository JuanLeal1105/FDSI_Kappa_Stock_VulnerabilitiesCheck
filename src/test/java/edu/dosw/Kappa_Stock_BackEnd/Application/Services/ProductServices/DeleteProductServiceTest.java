package edu.dosw.Kappa_Stock_BackEnd.Application.Services.ProductServices;

import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.ProductRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.expression.ExpressionException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteProductServiceTest {

    @Mock
    ProductRepositoryPort productRepository;

    @InjectMocks
    DeleteProductService deleteProductService;

    @Test
    void deleteById_existing_deletes() {
        Product p = Product.builder().id("d1").build();
        when(productRepository.findById("d1")).thenReturn(Optional.of(p));

        deleteProductService.deleteById("d1");

        verify(productRepository, times(1)).deleteById("d1");
    }

    @Test
    void deleteById_missing_throws() {
        when(productRepository.findById("missing")).thenReturn(Optional.empty());
        assertThrows(ExpressionException.class, () -> deleteProductService.deleteById("missing"));
        verify(productRepository, never()).deleteById(any());
    }
}
