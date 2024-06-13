package com.onlinestore.services.impl;

import com.onlinestore.entities.Product;
import com.onlinestore.payload.ProductDto;
import com.onlinestore.repositories.ProductRepo;
import com.onlinestore.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.onlinestore.utils.MiscUtils.compressBytes;
import static com.onlinestore.utils.MiscUtils.decompressBytes;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepo productRepo;

    // Create
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = this.modelMapper.map(productDto, Product.class);
        product.setImg(compressBytes(product.getImg()));

        Product save = this.productRepo.save(product);
        return this.modelMapper.map(save, ProductDto.class);
    }

    // Read One
    @Override
    public ProductDto getProduct(Integer ProductId) {

        Product save = this.productRepo.findById(ProductId).orElseThrow();
        save.setImg(decompressBytes(save.getImg()));

        return this.modelMapper.map(save, ProductDto.class);
    }

    // Read All
    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> all = productRepo.findAll();

        System.out.println(all);

        List<ProductDto> collect = all.stream().map(dto -> new ProductDto(dto.getId(), dto.getName(),
                dto.getDescription(), dto.getPrice(), dto.getWeight(), decompressBytes(dto.getImg())))
                .collect(Collectors.toList());

        System.out.println("collect");
        System.out.println(collect);

        return collect;
    }

    // Delete
    @Override
    public void deleteProduct(Integer productId) {
        this.productRepo.deleteById(productId);
        return;
    }

    // Update
    @Override
    public ProductDto updateProduct(ProductDto productDto, Integer ProductId) {
        Product newProduct = this.productRepo.findById(ProductId).orElseThrow();
        newProduct.setId(ProductId);
        newProduct.setDescription(productDto.getDescription());
        newProduct.setName(productDto.getName());
        newProduct.setWeight(productDto.getWeight());
        newProduct.setPrice(productDto.getPrice());
        newProduct.setImg(productDto.getImg());

        Product savedNewProduct = this.productRepo.save(newProduct);

        System.out.println(savedNewProduct);

        return this.modelMapper.map(savedNewProduct, ProductDto.class);
    }
}
