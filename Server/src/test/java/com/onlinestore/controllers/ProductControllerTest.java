package com.onlinestore.controllers;

import com.onlinestore.payload.ApiResponse;
import com.onlinestore.payload.ProductDto;
import com.onlinestore.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private MultipartFile mockFile;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct() throws IOException {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("name", "Test Product");
        formData.add("description", "Test Description");
        formData.add("weight", "1.5");
        formData.add("price", "19.99");

        byte[] mockImageBytes = new byte[]{1, 2, 3};
        when(mockFile.getBytes()).thenReturn(mockImageBytes);

        ProductDto savedProductDto = new ProductDto(1, "Test Product", "Test Description", 19.99, 1.5, mockImageBytes);
        when(productService.createProduct(any(ProductDto.class))).thenReturn(savedProductDto);

        ResponseEntity<ProductDto> response = productController.CreateProduct(formData, mockFile);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedProductDto, response.getBody());
        verify(productService, times(1)).createProduct(any(ProductDto.class));
    }

    @Test
    public void testGetById() {
        Integer productId = 1;
        ProductDto productDto = new ProductDto(productId, "Test Product", "Test Description", 19.99, 1.5, new byte[]{});
        when(productService.getProduct(productId)).thenReturn(productDto);

        ResponseEntity<ProductDto> response = productController.GetById(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDto, response.getBody());
        verify(productService, times(1)).getProduct(productId);
    }

    @Test
    public void testGetAll() {
        List<ProductDto> products = Arrays.asList(
                new ProductDto(1, "Product 1", "Description 1", 19.99, 1.5, new byte[]{}),
                new ProductDto(2, "Product 2", "Description 2", 29.99, 2.0, new byte[]{})
        );
        when(productService.getAllProducts()).thenReturn(products);

        ResponseEntity<List<ProductDto>> response = productController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void testDelete() {
        Integer productId = 1;

        ResponseEntity<ApiResponse> response = productController.Delete(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product deleted", response.getBody().getMessage());
        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    public void testUpdateProduct() throws IOException {
        Integer productId = 1;
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("name", "Updated Product");
        formData.add("description", "Updated Description");
        formData.add("weight", "2.0");
        formData.add("price", "29.99");

        byte[] mockImageBytes = new byte[]{4, 5, 6};
        when(mockFile.getBytes()).thenReturn(mockImageBytes);

        ProductDto updatedProductDto = new ProductDto(productId, "Updated Product", "Updated Description", 29.99, 2.0, mockImageBytes);
        when(productService.updateProduct(any(ProductDto.class), eq(productId))).thenReturn(updatedProductDto);

        ResponseEntity<ProductDto> response = productController.UpdateProduct(formData, mockFile, productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedProductDto, response.getBody());
        verify(productService, times(1)).updateProduct(any(ProductDto.class), eq(productId));
    }
}