package edu.dosw.Kappa_Stock_BackEnd.Application.Services.ProductServices;

import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.ProductResponse;
import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.ProductRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllProductsServiceTest {

    @Mock
    ProductRepositoryPort productRepository;

    @InjectMocks
    GetAllProductsService getAllProductsService;

    @Test
    void getAllProducts_mapsList() {
        Product p1 = Product.builder().id("a").name("A").build();
        Product p2 = Product.builder().id("b").name("B").build();
        when(productRepository.findAll()).thenReturn(List.of(p1, p2));

        List<ProductResponse> resp = getAllProductsService.getAllProducts();
        assertEquals(2, resp.size());
        assertEquals("A", resp.get(0).getName());
    }
}
