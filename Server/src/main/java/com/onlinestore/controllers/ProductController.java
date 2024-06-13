package com.onlinestore.controllers;

import com.onlinestore.payload.ApiResponse;
import com.onlinestore.payload.ProductDto;
import com.onlinestore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Create Product
    @PostMapping(value = "/add")
    public ResponseEntity<ProductDto> CreateProduct(@RequestParam MultiValueMap<String, String> formData,
            @RequestParam("img") MultipartFile file) throws IOException {
        ProductDto productDto = new ProductDto();
        productDto.setName(formData.getFirst("name"));
        productDto.setDescription(formData.getFirst("description"));
        productDto.setWeight(Double.valueOf(formData.getFirst("weight")));
        productDto.setPrice(Double.valueOf(formData.getFirst("price")));
        productDto.setImg(file.getBytes());

        ProductDto save = this.productService.createProduct(productDto);

        return new ResponseEntity<ProductDto>(save, HttpStatusCode.valueOf(200));
    }

    // Get by Id
    @GetMapping("/{productid}")
    public ResponseEntity<ProductDto> GetById(@PathVariable Integer productid) {
        ProductDto product = this.productService.getProduct(productid);

        return new ResponseEntity<>(product, HttpStatusCode.valueOf(200));
    }

    // Get All Product
    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getAll() {
        List<ProductDto> products = this.productService.getAllProducts();

        return new ResponseEntity<>(products, HttpStatusCode.valueOf(200));
    }

    // Delete Product
    @DeleteMapping(value = "/del/{ProductId}", produces = "application/json")
    public ResponseEntity<ApiResponse> Delete(@PathVariable Integer ProductId) {
        this.productService.deleteProduct(ProductId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Product deleted"), HttpStatusCode.valueOf(200));
    }

    // Update Product
    @PutMapping("/{ProductId}")
    public ResponseEntity<ProductDto> UpdateProduct(@RequestParam MultiValueMap<String, String> formData,
            @RequestParam("img") MultipartFile file,
            @PathVariable Integer ProductId) throws IOException {
        ProductDto productDto = new ProductDto();
        productDto.setName(formData.getFirst("name"));
        productDto.setDescription(formData.getFirst("description"));
        productDto.setWeight(Double.valueOf(formData.getFirst("weight")));
        productDto.setPrice(Double.valueOf(formData.getFirst("price")));
        productDto.setImg(file.getBytes());

        ProductDto save = this.productService.updateProduct(productDto, ProductId);

        return new ResponseEntity<ProductDto>(save, HttpStatusCode.valueOf(200));
    }
}
