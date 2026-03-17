package edu.dosw.Kappa_Stock_BackEnd.Application.Services.ProductServices;

import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.command.ProductCommands.UpdateProductCommand;
import edu.dosw.Kappa_Stock_BackEnd.Application.Ports.ProductRepositoryPort;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.expression.ExpressionException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateProductServiceTest {

    @Mock
    ProductRepositoryPort productRepository;

    @InjectMocks
    UpdateProductService updateProductService;

    @Test
    void updateProduct_happyPath_updatesFields() {
        Product existing = Product.builder().id("u1").name("Old").price(new BigDecimal("1.0")).build();
        when(productRepository.findById("u1")).thenReturn(Optional.of(existing));
        when(productRepository.existsByName("NewName")).thenReturn(false);
        when(productRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        UpdateProductCommand cmd = new UpdateProductCommand("u1","NewName",new BigDecimal("2.5"),"desc","cat",null,null,true,3);
        var resp = updateProductService.updateProduct(cmd);

        assertNotNull(resp);
        assertEquals("NewName", resp.getName());
        assertEquals(new BigDecimal("2.5"), resp.getPrice());
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void updateProduct_nameAlreadyExists_throws() {
        Product existing = Product.builder().id("u2").name("Other").build();
        when(productRepository.findById("u2")).thenReturn(Optional.of(existing));
        when(productRepository.existsByName("Taken")).thenReturn(true);

        UpdateProductCommand cmd = new UpdateProductCommand("u2","Taken",null,null,null,null,null,null,null);
        assertThrows(ExpressionException.class, () -> updateProductService.updateProduct(cmd));
    }
}
