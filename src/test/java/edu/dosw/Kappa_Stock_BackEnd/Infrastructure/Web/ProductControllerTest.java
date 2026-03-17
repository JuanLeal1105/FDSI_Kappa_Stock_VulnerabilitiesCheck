package edu.dosw.Kappa_Stock_BackEnd.Infrastructure.Web;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.ProductResponse;
import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.StockOperationRequest;
import edu.dosw.Kappa_Stock_BackEnd.Application.Services.StockUseCases.DecreaseStockUseCase;
import edu.dosw.Kappa_Stock_BackEnd.Application.Services.StockUseCases.IncreaseStockUseCase;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockOperationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StockController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IncreaseStockUseCase increaseStockUseCase;

    @MockBean
    private DecreaseStockUseCase decreaseStockUseCase;

    private ProductResponse productResponse;
    private StockOperationRequest stockOperationRequest;

    @BeforeEach
    void setUp() {
        productResponse = ProductResponse.builder()
                .id("1")
                .name("Test Product")
                .description("Test Description")
                .price(BigDecimal.valueOf(100))
                .stock(75)
                .available(true)
                .build();

        stockOperationRequest = new StockOperationRequest();
        stockOperationRequest.setAmount(25);
        stockOperationRequest.setReason("Restock");
        stockOperationRequest.setOperationType(StockOperationType.RESTOCK);
    }

    @Test
    void increaseStock_ShouldReturnUpdatedProduct() throws Exception {
        when(increaseStockUseCase.increaseStock(any())).thenReturn(productResponse);

        mockMvc.perform(post("/api/products/1/stock/increase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stockOperationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.stock").value(75));

        verify(increaseStockUseCase, times(1)).increaseStock(any());
    }

    @Test
    void decreaseStock_ShouldReturnUpdatedProduct() throws Exception {
        when(decreaseStockUseCase.decreaseStock(any())).thenReturn(productResponse);

        mockMvc.perform(post("/api/products/1/stock/decrease")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stockOperationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.stock").value(75));

        verify(decreaseStockUseCase, times(1)).decreaseStock(any());
    }

    @Test
    void increaseStock_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        StockOperationRequest invalidRequest = new StockOperationRequest();
        // Missing required fields

        mockMvc.perform(post("/api/products/1/stock/increase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void decreaseStock_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        StockOperationRequest invalidRequest = new StockOperationRequest();
        // Missing required fields

        mockMvc.perform(post("/api/products/1/stock/decrease")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void increaseStock_WithZeroAmount_ShouldHandleCorrectly() throws Exception {
        StockOperationRequest zeroRequest = new StockOperationRequest();
        zeroRequest.setAmount(0);
        zeroRequest.setReason("Test");
        zeroRequest.setOperationType(StockOperationType.ADJUSTMENT);

        // This should fail validation since amount must be positive
        mockMvc.perform(post("/api/products/1/stock/increase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(zeroRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void decreaseStock_WithLargeAmount_ShouldProcessCorrectly() throws Exception {
        StockOperationRequest largeRequest = new StockOperationRequest();
        largeRequest.setAmount(1000);
        largeRequest.setReason("Major Sale");
        largeRequest.setOperationType(StockOperationType.SALE);

        when(decreaseStockUseCase.decreaseStock(any())).thenReturn(productResponse);

        mockMvc.perform(post("/api/products/1/stock/decrease")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(largeRequest)))
                .andExpect(status().isOk());

        verify(decreaseStockUseCase, times(1)).decreaseStock(any());
    }
}