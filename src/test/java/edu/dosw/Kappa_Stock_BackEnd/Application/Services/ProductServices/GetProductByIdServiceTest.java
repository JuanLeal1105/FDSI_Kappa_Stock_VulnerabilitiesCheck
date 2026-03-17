package edu.dosw.Kappa_Stock_BackEnd.Application.Services.ProductServices;

import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.ProductResponse;
import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.ProductRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetProductByIdServiceTest {

    @Mock
    ProductRepositoryPort productRepository;

    @InjectMocks
    GetProductByIdService getProductByIdService;

    @Test
    void getProductById_returnsMappedResponse() {
        Product p = Product.builder().id("p10").name("Prod10").stock(5).build();
        when(productRepository.findById("p10")).thenReturn(Optional.of(p));

        ProductResponse resp = getProductByIdService.getProductById("p10");
        assertNotNull(resp);
        assertEquals("p10", resp.getId());
        assertEquals("Prod10", resp.getName());
    }

    @Test
    void getProductById_missing_throws() {
        when(productRepository.findById("missing")).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> getProductByIdService.getProductById("missing"));
    }
}
