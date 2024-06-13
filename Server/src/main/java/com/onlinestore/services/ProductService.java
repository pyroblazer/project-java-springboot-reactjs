package com.onlinestore.services;

import com.onlinestore.payload.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto getProduct(Integer ProductId);
    List<ProductDto> getAllProducts();
    void deleteProduct(Integer productId);
    ProductDto updateProduct(ProductDto productDto, Integer ProductId);
}
