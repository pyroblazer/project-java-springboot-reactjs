package com.onlinestore.utils;

import com.onlinestore.entities.OrderDetails;
import com.onlinestore.entities.Product;
import com.onlinestore.payload.ProductDto;

public class TestDataUtil {

    public static final int productId = 1;
    public static final String productName = "Test Product";
    public static final String productDescription = "This is a test product";
    public static final double productPrice = 19.99;
    public static final double productWeight = 2.5;
    public static final byte[] productImageBytes = {1, 2, 3, 4};

    public static Product createProduct() {
        Product product = new Product();
        product.setName(productName);
        product.setDescription(productDescription);
        product.setPrice(productPrice);
        product.setWeight(productWeight);
        product.setImg(productImageBytes);
        return product;
    }

    public static ProductDto createProductDto() {
        ProductDto productDto = new ProductDto();
        productDto.setName(productName);
        productDto.setDescription(productDescription);
        productDto.setPrice(productPrice);
        productDto.setWeight(productWeight);
        productDto.setImg(productImageBytes);
        return productDto;
    }

    public static OrderDetails createOrderDetails(Product product) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setProduct(product);
        orderDetails.setQuantity(2);
        orderDetails.setAmount(product.getPrice() * 2);
        return orderDetails;
    }
}
