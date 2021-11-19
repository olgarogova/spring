package com.springjpa.controllers;

import com.springjpa.entity.ProductEntity;
import com.springjpa.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductRestController {

    private final ProductServiceImpl productService;

    @Autowired
    public ProductRestController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductEntity> getAllProducts(@RequestParam(required = false) String productName){
        return productService.getAllProducts(productName);
    }

    @GetMapping("/{productId}")
    public ProductEntity getProductById(@PathVariable("productId") int productId) {
        return productService.getProductById(productId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductEntity createProduct(@RequestBody ProductEntity productEntity) {
        return productService.createProduct(productEntity);
    }

    @PutMapping("/{productId}")
    public ProductEntity updateProduct(@PathVariable("productId") int productId, @RequestBody ProductEntity updatedProductEntity) {
        return productService.updateProduct(productId, updatedProductEntity);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable("productId") int productId) {
        productService.deleteProduct(productId);
    }
}
