package com.onlinestore.services.impl;

import com.onlinestore.entities.Product;
import com.onlinestore.payload.ProductDto;
import com.onlinestore.repositories.ProductRepo;
import com.onlinestore.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.onlinestore.utils.TestDataUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepo productRepo;

    @Test
    public void testCreateProduct_ShouldSaveAndReturnProductDto() throws Exception {
        ProductDto productDto = createProductDto();

        Product savedProduct = createProduct();
        Mockito.when(productRepo.save(Mockito.any(Product.class))).thenReturn(savedProduct);

        ProductDto savedProductDto = productService.createProduct(productDto);

        assertEquals(productName, savedProductDto.getName());
        assertEquals(productDescription, savedProductDto.getDescription());
        assertEquals(productPrice, savedProductDto.getPrice(), 0.001); // Delta for floating-point comparison
        assertEquals(productWeight, savedProductDto.getWeight(), 0.001);
    }

    @Test
    public void testGetProduct_ShouldFindProductByIdAndReturnDto() throws Exception {
        int productId = 1;

        Product expectedProduct = createProduct();
        expectedProduct.setId(productId);
        Mockito.when(productRepo.findById(productId)).thenReturn(Optional.of(expectedProduct));

        ProductDto retrievedProductDto = productService.getProduct(productId);

        assertEquals(productId, retrievedProductDto.getId());
        assertEquals(productName, retrievedProductDto.getName()); // Assuming these are set in the expectedProduct
        assertEquals(productDescription, retrievedProductDto.getDescription());
        assertEquals(productPrice, retrievedProductDto.getPrice(), 0.001);
        assertEquals(productWeight, retrievedProductDto.getWeight(), 0.001);
    }

    @Test
    public void testGetAllProducts_ShouldReturnListOfProductDtos() throws Exception {
        List<Product> mockProducts = Arrays.asList(
                Product.builder()
                        .id(1)
                        .name("Product 1")
                        .description("Description 1")
                        .price(10.99)
                        .weight(1.0)
                        .build(),
                Product.builder()
                        .id(2)
                        .name("Product 2")
                        .description("Description 2")
                        .price(24.50)
                        .weight(2.0)
                        .build()
        );


        Mockito.when(productRepo.findAll()).thenReturn(mockProducts);

        List<ProductDto> allProductsDto = productService.getAllProducts();

        assertEquals(2, allProductsDto.size());

        int i = 0;
        for (ProductDto productDto : allProductsDto) {
            int expectedProductId = productDto.getId();

            Product expectedProduct = mockProducts.get(i);

            assertEquals(expectedProductId, expectedProduct.getId());
            assertEquals(expectedProduct.getName(), productDto.getName());
            assertEquals(expectedProduct.getDescription(), productDto.getDescription());
            assertEquals(expectedProduct.getPrice(), productDto.getPrice(), 0.001);
            assertEquals(expectedProduct.getWeight(), productDto.getWeight(), 0.001);

            i++;
        }
    }

    @Test
    public void testDeleteProduct_ShouldDeleteProductById() throws Exception {
        int productIdToDelete = 1;

        Product productToDelete = Mockito.mock(Product.class);
        Mockito.when(productToDelete.getId()).thenReturn(productIdToDelete);

        productService.deleteProduct(productIdToDelete);

        Mockito.verify(productRepo).deleteById(productIdToDelete);
    }

    @Test
    public void testUpdateProduct_ShouldUpdateProductAndReturnDto() throws Exception {
        String updatedProductName = "Updated Product Name";
        String updatedDescription = "This product description is updated";
        double updatedPrice = 19.99;
        double updatedWeight = 3.0;
        byte[] updatedImageBytes = {5, 6, 7, 8};

        int productIdToUpdate = 1;

        Product productToUpdate = createProduct();
        productToUpdate.setId(productIdToUpdate);
        Mockito.when(productRepo.findById(productIdToUpdate)).thenReturn(Optional.of(productToUpdate));

        Product updatedProduct = createProduct();
        updatedProduct.setId(productIdToUpdate);
        updatedProduct.setName(updatedProductName);
        updatedProduct.setDescription(updatedDescription);
        updatedProduct.setPrice(updatedPrice);
        updatedProduct.setWeight(updatedWeight);
        updatedProduct.setImg(updatedImageBytes);
        Mockito.when(productRepo.save(Mockito.any(Product.class))).thenReturn(updatedProduct);

        ProductDto updatedProductDto = new ProductDto(productIdToUpdate, updatedProductName, updatedDescription, updatedPrice, updatedWeight, updatedImageBytes);

        ProductDto returnedProductDto = productService.updateProduct(updatedProductDto, productIdToUpdate);

        assertEquals(productIdToUpdate, returnedProductDto.getId());
        assertEquals(updatedProductName, returnedProductDto.getName());
        assertEquals(updatedDescription, returnedProductDto.getDescription());
        assertEquals(updatedPrice, returnedProductDto.getPrice(), 0.001);
        assertEquals(updatedWeight, returnedProductDto.getWeight(), 0.001);
    }
}
