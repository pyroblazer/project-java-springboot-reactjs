package com.onlinestore.services;

import com.onlinestore.payload.ProductDto;

import java.util.List;

public interface ProductService {

    //create
    ProductDto CreateProduct(ProductDto productDto);

    //read
    ProductDto ReadProduct(Integer ProductId);


    //readAll
    List<ProductDto> ReadAllProduct();


    //delete
    void DeleteProduct(Integer productId);


    //update
    ProductDto UpdateProduct(ProductDto productDto,Integer ProductId);



}

