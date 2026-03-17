package edu.dosw.Kappa_Stock_BackEnd.Application.Services.ProductServices;

import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.command.ProductCommands.CreateProductCommand;
import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.ProductRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProductServiceTest {

    @Mock
    ProductRepositoryPort productRepository;

    @InjectMocks
    CreateProductService createProductService;

    @Test
    void createProduct_whenNameExists_throws() {
        when(productRepository.existsByName("X")).thenReturn(true);
        CreateProductCommand cmd = new CreateProductCommand("X", new BigDecimal("1.0"), null, null, null, null, 0, 5);
        assertThrows(RuntimeException.class, () -> createProductService.createProduct(cmd));
        verify(productRepository, never()).save(any());
    }

    @Test
    void createProduct_happyPath_savesAndReturnsResponse() {
        when(productRepository.existsByName("New")).thenReturn(false);
        Product saved = Product.builder().id("p1").name("New").price(new BigDecimal("2.5")).stock(0).available(false).build();
        when(productRepository.save(any())).thenReturn(saved);

        CreateProductCommand cmd = new CreateProductCommand("New", new BigDecimal("2.5"), "d", "c", null, 10, 0, 5);
        var resp = createProductService.createProduct(cmd);

        assertNotNull(resp);
        assertEquals("p1", resp.getId());
        assertEquals("New", resp.getName());
        assertEquals(0, resp.getStock());
        assertFalse(resp.getAvailable());
        verify(productRepository, times(1)).save(any());
    }
}
