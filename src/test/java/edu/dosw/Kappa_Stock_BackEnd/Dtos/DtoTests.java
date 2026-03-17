package edu.dosw.Kappa_Stock_BackEnd.Dtos;

import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.*;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.Product;
import edu.dosw.Kappa_Stock_BackEnd.Domain.Model.StockOperationType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DTO Tests for Complete Coverage")
class DtoTests {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // ==================== ProductRequest Tests ====================

    @Test
    @DisplayName("ProductRequest with all valid fields should pass validation")
    void productRequest_WithAllValidFields_ShouldPassValidation() {
        ProductRequest request = new ProductRequest();
        request.setName("Test Product");
        request.setPrice(BigDecimal.valueOf(100));
        request.setDescription("Test Description");
        request.setCategory("Test Category");
        request.setImageUrl("http://example.com/image.jpg");
        request.setPreparationTime(15);

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("ProductRequest with blank name should fail validation")
    void productRequest_WithBlankName_ShouldFailValidation() {
        ProductRequest request = new ProductRequest();
        request.setName("");
        request.setPrice(BigDecimal.valueOf(100));

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    @DisplayName("ProductRequest with null name should fail validation")
    void productRequest_WithNullName_ShouldFailValidation() {
        ProductRequest request = new ProductRequest();
        request.setName(null);
        request.setPrice(BigDecimal.valueOf(100));

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("ProductRequest with negative price should fail validation")
    void productRequest_WithNegativePrice_ShouldFailValidation() {
        ProductRequest request = new ProductRequest();
        request.setName("Test Product");
        request.setPrice(BigDecimal.valueOf(-10));

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("price")));
    }

    @Test
    @DisplayName("ProductRequest with zero price should fail validation")
    void productRequest_WithZeroPrice_ShouldFailValidation() {
        ProductRequest request = new ProductRequest();
        request.setName("Test Product");
        request.setPrice(BigDecimal.ZERO);

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("ProductRequest with negative preparation time should fail validation")
    void productRequest_WithNegativePreparationTime_ShouldFailValidation() {
        ProductRequest request = new ProductRequest();
        request.setName("Test Product");
        request.setPrice(BigDecimal.valueOf(100));
        request.setPreparationTime(-5);

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("ProductRequest optional fields can be null")
    void productRequest_OptionalFields_CanBeNull() {
        ProductRequest request = new ProductRequest();
        request.setName("Test Product");
        request.setPrice(BigDecimal.valueOf(100));
        request.setDescription(null);
        request.setCategory(null);
        request.setImageUrl(null);
        request.setPreparationTime(null);

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("ProductRequest getters and setters should work")
    void productRequest_GettersAndSetters_ShouldWork() {
        ProductRequest request = new ProductRequest();

        request.setName("Product Name");
        request.setPrice(BigDecimal.valueOf(50.5));
        request.setDescription("Description");
        request.setCategory("Category");
        request.setImageUrl("http://url.com");
        request.setPreparationTime(10);

        assertEquals("Product Name", request.getName());
        assertEquals(BigDecimal.valueOf(50.5), request.getPrice());
        assertEquals("Description", request.getDescription());
        assertEquals("Category", request.getCategory());
        assertEquals("http://url.com", request.getImageUrl());
        assertEquals(10, request.getPreparationTime());
    }

    @Test
    @DisplayName("ProductRequest equals and hashCode should work")
    void productRequest_EqualsAndHashCode_ShouldWork() {
        ProductRequest request1 = new ProductRequest();
        request1.setName("Test");
        request1.setPrice(BigDecimal.valueOf(100));

        ProductRequest request2 = new ProductRequest();
        request2.setName("Test");
        request2.setPrice(BigDecimal.valueOf(100));

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    @DisplayName("ProductRequest toString should work")
    void productRequest_ToString_ShouldWork() {
        ProductRequest request = new ProductRequest();
        request.setName("Test");
        request.setPrice(BigDecimal.valueOf(100));

        String toString = request.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Test"));
    }

    // ==================== UpdateProductRequest Tests ====================

    @Test
    @DisplayName("UpdateProductRequest with all valid fields should pass validation")
    void updateProductRequest_WithAllValidFields_ShouldPassValidation() {
        UpdateProductRequest request = new UpdateProductRequest();
        request.setName("Updated Product");
        request.setPrice(BigDecimal.valueOf(150));
        request.setDescription("Updated Description");
        request.setCategory("Updated Category");
        request.setImageUrl("http://example.com/new.jpg");
        request.setPreparationTime(20);
        request.setAvailable(true);
        request.setMinStockLevel(10);

        Set<ConstraintViolation<UpdateProductRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("UpdateProductRequest all fields can be null")
    void updateProductRequest_AllFields_CanBeNull() {
        UpdateProductRequest request = new UpdateProductRequest();

        Set<ConstraintViolation<UpdateProductRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("UpdateProductRequest with negative price should fail validation")
    void updateProductRequest_WithNegativePrice_ShouldFailValidation() {
        UpdateProductRequest request = new UpdateProductRequest();
        request.setPrice(BigDecimal.valueOf(-50));

        Set<ConstraintViolation<UpdateProductRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("UpdateProductRequest with negative preparation time should fail")
    void updateProductRequest_WithNegativePreparationTime_ShouldFailValidation() {
        UpdateProductRequest request = new UpdateProductRequest();
        request.setPreparationTime(-10);

        Set<ConstraintViolation<UpdateProductRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("UpdateProductRequest with negative minStockLevel should fail")
    void updateProductRequest_WithNegativeMinStockLevel_ShouldFailValidation() {
        UpdateProductRequest request = new UpdateProductRequest();
        request.setMinStockLevel(-5);

        Set<ConstraintViolation<UpdateProductRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("UpdateProductRequest getters and setters should work")
    void updateProductRequest_GettersAndSetters_ShouldWork() {
        UpdateProductRequest request = new UpdateProductRequest();

        request.setName("Updated Name");
        request.setPrice(BigDecimal.valueOf(200));
        request.setDescription("Updated Desc");
        request.setCategory("Updated Cat");
        request.setImageUrl("http://new.url");
        request.setPreparationTime(25);
        request.setAvailable(false);
        request.setMinStockLevel(15);

        assertEquals("Updated Name", request.getName());
        assertEquals(BigDecimal.valueOf(200), request.getPrice());
        assertEquals("Updated Desc", request.getDescription());
        assertEquals("Updated Cat", request.getCategory());
        assertEquals("http://new.url", request.getImageUrl());
        assertEquals(25, request.getPreparationTime());
        assertFalse(request.getAvailable());
        assertEquals(15, request.getMinStockLevel());
    }

    @Test
    @DisplayName("UpdateProductRequest equals and hashCode should work")
    void updateProductRequest_EqualsAndHashCode_ShouldWork() {
        UpdateProductRequest request1 = new UpdateProductRequest();
        request1.setName("Test");
        request1.setPrice(BigDecimal.valueOf(100));

        UpdateProductRequest request2 = new UpdateProductRequest();
        request2.setName("Test");
        request2.setPrice(BigDecimal.valueOf(100));

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    // ==================== StockOperationRequest Tests ====================

    @Test
    @DisplayName("StockOperationRequest with all valid fields should pass validation")
    void stockOperationRequest_WithAllValidFields_ShouldPassValidation() {
        StockOperationRequest request = new StockOperationRequest();
        request.setAmount(50);
        request.setReason("Restock from supplier");
        request.setOperationType(StockOperationType.RESTOCK);

        Set<ConstraintViolation<StockOperationRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("StockOperationRequest with null amount should fail validation")
    void stockOperationRequest_WithNullAmount_ShouldFailValidation() {
        StockOperationRequest request = new StockOperationRequest();
        request.setAmount(null);
        request.setOperationType(StockOperationType.SALE);

        Set<ConstraintViolation<StockOperationRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("StockOperationRequest with negative amount should fail validation")
    void stockOperationRequest_WithNegativeAmount_ShouldFailValidation() {
        StockOperationRequest request = new StockOperationRequest();
        request.setAmount(-10);
        request.setOperationType(StockOperationType.SALE);

        Set<ConstraintViolation<StockOperationRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("StockOperationRequest with zero amount should fail validation")
    void stockOperationRequest_WithZeroAmount_ShouldFailValidation() {
        StockOperationRequest request = new StockOperationRequest();
        request.setAmount(0);
        request.setOperationType(StockOperationType.ADJUSTMENT);

        Set<ConstraintViolation<StockOperationRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("StockOperationRequest with null operationType should fail validation")
    void stockOperationRequest_WithNullOperationType_ShouldFailValidation() {
        StockOperationRequest request = new StockOperationRequest();
        request.setAmount(100);
        request.setOperationType(null);

        Set<ConstraintViolation<StockOperationRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("StockOperationRequest reason can be null")
    void stockOperationRequest_Reason_CanBeNull() {
        StockOperationRequest request = new StockOperationRequest();
        request.setAmount(50);
        request.setOperationType(StockOperationType.RESTOCK);
        request.setReason(null);

        Set<ConstraintViolation<StockOperationRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("StockOperationRequest getters and setters should work")
    void stockOperationRequest_GettersAndSetters_ShouldWork() {
        StockOperationRequest request = new StockOperationRequest();

        request.setAmount(75);
        request.setReason("Testing");
        request.setOperationType(StockOperationType.DAMAGE);

        assertEquals(75, request.getAmount());
        assertEquals("Testing", request.getReason());
        assertEquals(StockOperationType.DAMAGE, request.getOperationType());
    }

    @Test
    @DisplayName("StockOperationRequest with all operation types should be valid")
    void stockOperationRequest_WithAllOperationTypes_ShouldBeValid() {
        for (StockOperationType type : StockOperationType.values()) {
            StockOperationRequest request = new StockOperationRequest();
            request.setAmount(10);
            request.setOperationType(type);

            Set<ConstraintViolation<StockOperationRequest>> violations = validator.validate(request);
            assertTrue(violations.isEmpty(), "Failed for type: " + type);
        }
    }

    @Test
    @DisplayName("StockOperationRequest equals and hashCode should work")
    void stockOperationRequest_EqualsAndHashCode_ShouldWork() {
        StockOperationRequest request1 = new StockOperationRequest();
        request1.setAmount(50);
        request1.setOperationType(StockOperationType.SALE);

        StockOperationRequest request2 = new StockOperationRequest();
        request2.setAmount(50);
        request2.setOperationType(StockOperationType.SALE);

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    // ==================== ProductResponse Tests ====================

    @Test
    @DisplayName("ProductResponse builder should create response correctly")
    void productResponse_Builder_ShouldCreateCorrectly() {
        ProductResponse response = ProductResponse.builder()
                .id("123")
                .name("Test Product")
                .price(BigDecimal.valueOf(100))
                .description("Test Description")
                .category("Test Category")
                .imageUrl("http://example.com/image.jpg")
                .preparationTime(15)
                .stock(50)
                .available(true)
                .build();

        assertEquals("123", response.getId());
        assertEquals("Test Product", response.getName());
        assertEquals(BigDecimal.valueOf(100), response.getPrice());
        assertEquals("Test Description", response.getDescription());
        assertEquals("Test Category", response.getCategory());
        assertEquals("http://example.com/image.jpg", response.getImageUrl());
        assertEquals(15, response.getPreparationTime());
        assertEquals(50, response.getStock());
        assertTrue(response.getAvailable());
    }

    @Test
    @DisplayName("ProductResponse from Product should map correctly")
    void productResponse_FromProduct_ShouldMapCorrectly() {
        Product product = Product.builder()
                .id("456")
                .name("Product Name")
                .price(BigDecimal.valueOf(75.5))
                .description("Product Description")
                .category("Product Category")
                .imageUrl("http://example.com/product.jpg")
                .preparationTime(20)
                .stock(100)
                .available(false)
                .build();

        ProductResponse response = ProductResponse.from(product);

        assertEquals("456", response.getId());
        assertEquals("Product Name", response.getName());
        assertEquals(BigDecimal.valueOf(75.5), response.getPrice());
        assertEquals("Product Description", response.getDescription());
        assertEquals("Product Category", response.getCategory());
        assertEquals("http://example.com/product.jpg", response.getImageUrl());
        assertEquals(20, response.getPreparationTime());
        assertEquals(100, response.getStock());
        assertFalse(response.getAvailable());
    }

    @Test
    @DisplayName("ProductResponse from Product with null fields should handle gracefully")
    void productResponse_FromProductWithNulls_ShouldHandleGracefully() {
        Product product = Product.builder()
                .id("789")
                .name("Minimal Product")
                .build();

        ProductResponse response = ProductResponse.from(product);

        assertEquals("789", response.getId());
        assertEquals("Minimal Product", response.getName());
        assertNull(response.getPrice());
        assertNull(response.getDescription());
        assertNull(response.getCategory());
        assertNull(response.getImageUrl());
        assertNull(response.getPreparationTime());
        assertNull(response.getStock());
        assertNull(response.getAvailable());
    }

    @Test
    @DisplayName("ProductResponse setters and getters should work")
    void productResponse_SettersAndGetters_ShouldWork() {
        ProductResponse response = ProductResponse.builder().build();

        response.setId("999");
        response.setName("New Name");
        response.setPrice(BigDecimal.valueOf(250));
        response.setDescription("New Desc");
        response.setCategory("New Cat");
        response.setImageUrl("http://new.url");
        response.setPreparationTime(30);
        response.setStock(200);
        response.setAvailable(true);

        assertEquals("999", response.getId());
        assertEquals("New Name", response.getName());
        assertEquals(BigDecimal.valueOf(250), response.getPrice());
        assertEquals("New Desc", response.getDescription());
        assertEquals("New Cat", response.getCategory());
        assertEquals("http://new.url", response.getImageUrl());
        assertEquals(30, response.getPreparationTime());
        assertEquals(200, response.getStock());
        assertTrue(response.getAvailable());
    }

    @Test
    @DisplayName("ProductResponse equals and hashCode should work")
    void productResponse_EqualsAndHashCode_ShouldWork() {
        ProductResponse response1 = ProductResponse.builder()
                .id("1")
                .name("Test")
                .price(BigDecimal.valueOf(100))
                .build();

        ProductResponse response2 = ProductResponse.builder()
                .id("1")
                .name("Test")
                .price(BigDecimal.valueOf(100))
                .build();

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    @DisplayName("ProductResponse toString should work")
    void productResponse_ToString_ShouldWork() {
        ProductResponse response = ProductResponse.builder()
                .id("123")
                .name("Test Product")
                .build();

        String toString = response.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("123"));
        assertTrue(toString.contains("Test Product"));
    }

    @Test
    @DisplayName("ProductResponse with extreme values should handle correctly")
    void productResponse_WithExtremeValues_ShouldHandleCorrectly() {
        ProductResponse response = ProductResponse.builder()
                .id("x".repeat(100))
                .name("y".repeat(500))
                .price(new BigDecimal("999999999.99"))
                .stock(Integer.MAX_VALUE)
                .preparationTime(Integer.MAX_VALUE)
                .build();

        assertEquals(100, response.getId().length());
        assertEquals(500, response.getName().length());
        assertEquals(new BigDecimal("999999999.99"), response.getPrice());
        assertEquals(Integer.MAX_VALUE, response.getStock());
    }
}