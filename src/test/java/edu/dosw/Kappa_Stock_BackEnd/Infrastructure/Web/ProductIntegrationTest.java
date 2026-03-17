package edu.dosw.Kappa_Stock_BackEnd.Infrastructure.Web;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.ProductRequest;
import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.StockOperationRequest;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.Product;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockOperationType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Disabled;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled("Requires Docker/Testcontainers")
class ProductIntegrationTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    private String createdProductId;

    @BeforeEach
    void setUp() {
        mongoTemplate.getDb().drop();
    }

    @Test
    @Order(1)
    void createProduct_ShouldPersistToDatabase() throws Exception {
        ProductRequest request = new ProductRequest();
        request.setName("Integration Test Product");
        request.setDescription("Test Description");
        request.setPrice(BigDecimal.valueOf(99.99));
        request.setCategory("Electronics");

        String response = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Integration Test Product"))
                .andExpect(jsonPath("$.stock").value(100))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Product savedProduct = objectMapper.readValue(response, Product.class);
        createdProductId = savedProduct.getId();

        // Verify in database
        Product dbProduct = mongoTemplate.findById(createdProductId, Product.class);
        Assertions.assertNotNull(dbProduct);
        Assertions.assertEquals("Integration Test Product", dbProduct.getName());
    }

    @Test
    @Order(2)
    void fullProductLifecycle_ShouldWorkEndToEnd() throws Exception {
        // 1. Create product
        ProductRequest createRequest = new ProductRequest();
        createRequest.setName("Lifecycle Product");
        createRequest.setDescription("For testing");
        createRequest.setPrice(BigDecimal.valueOf(50));
        createRequest.setCategory("Test");

        String createResponse = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String productId = objectMapper.readTree(createResponse).get("id").asText();

        // 2. Get product by ID
        mockMvc.perform(get("/api/products/" + productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Lifecycle Product"));

        // 3. Increase stock
        StockOperationRequest increaseRequest = new StockOperationRequest();
        increaseRequest.setAmount(25);
        increaseRequest.setReason("Restock");
        increaseRequest.setOperationType(StockOperationType.RESTOCK);

        mockMvc.perform(post("/api/products/" + productId + "/stock/increase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(increaseRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stock").value(75));

        // 4. Decrease stock
        StockOperationRequest decreaseRequest = new StockOperationRequest();
        decreaseRequest.setAmount(10);
        decreaseRequest.setReason("Sale");
        decreaseRequest.setOperationType(StockOperationType.SALE);

        mockMvc.perform(post("/api/products/" + productId + "/stock/decrease")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(decreaseRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stock").value(65));

        // 5. Delete product
        mockMvc.perform(delete("/api/products/" + productId))
                .andExpect(status().isNoContent());

        // 6. Verify deletion
        Product deletedProduct = mongoTemplate.findById(productId, Product.class);
        Assertions.assertNull(deletedProduct);
    }

    @Test
    void getAllProducts_ShouldReturnAllProducts() throws Exception {
        // Create multiple products
        for (int i = 1; i <= 3; i++) {
            ProductRequest request = new ProductRequest();
            request.setName("Product " + i);
            request.setDescription("Description " + i);
            request.setPrice(BigDecimal.valueOf(100 * i));
            request.setCategory("Category " + i);

            mockMvc.perform(post("/api/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated());
        }

        // Get all products
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[1].name").exists())
                .andExpect(jsonPath("$[2].name").exists());
    }

    @Test
    void stockOperations_ShouldTriggerLowStockAlert() throws Exception {
        // Create product with low stock threshold
        ProductRequest request = new ProductRequest();
        request.setName("Alert Product");
        request.setDescription("Testing alerts");
        request.setPrice(BigDecimal.valueOf(100));
        request.setCategory("Test");

        String response = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String productId = objectMapper.readTree(response).get("id").asText();

        // Decrease stock below threshold
        StockOperationRequest decreaseRequest = new StockOperationRequest();
        decreaseRequest.setAmount(10);
        decreaseRequest.setReason("Sale");
        decreaseRequest.setOperationType(StockOperationType.SALE);

        mockMvc.perform(post("/api/products/" + productId + "/stock/decrease")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(decreaseRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stock").value(5));

        // Verify product is now in low stock state
        Product product = mongoTemplate.findById(productId, Product.class);
        Assertions.assertNotNull(product);
        Assertions.assertTrue(product.isLowStock());
    }
}