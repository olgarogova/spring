package com.springjpa.controllers;

import com.springjpa.entity.ProductEntity;
import com.springjpa.exceptions.InternalServerErrorException;
import com.springjpa.exceptions.ResourceNotFoundException;
import com.springjpa.repository.ProductRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductRestController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductRestController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<ProductEntity> getAllProducts(@RequestParam(required = false) String productName){

        try {
            List<ProductEntity> productEntities = new ArrayList<>();

            if (productName == null){
                productEntities.addAll(productRepository.findAll());
            } else {
                productEntities.addAll(productRepository.findAllByProductName(productName));
            }

            if (productEntities.isEmpty()) {
                throw new ResourceNotFoundException("Product not found");
            }
            return productEntities;
        } catch (InternalServerErrorException e){
            throw new InternalServerErrorException("Could not get products");
        }
    }

    @GetMapping("/{productId}")
    public Optional<ProductEntity> getProductById(@PathVariable("productId") int productId) {
        Optional<ProductEntity> productData = productRepository.findById(productId);
        if (productData.isEmpty()){
            throw new ResourceNotFoundException("Product not found");
        }
        return productData;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductEntity createProduct(@RequestBody ProductEntity productEntity) {
        try {
            return productRepository
                    .save(new ProductEntity(productEntity.getProductName(), productEntity.getSupplier(), productEntity.getUnitPrice(), productEntity.isDiscontinued()));
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Could not create product");
        }
    }

    @PutMapping("/{productId}")
    public ProductEntity updateProduct(@PathVariable("productId") int productId, @RequestBody ProductEntity updatedProductEntity) {
        Optional<ProductEntity> productData = productRepository.findById(productId);

        if (productData.isPresent()) {
            ProductEntity productEntity = productData.get();
            productEntity.setProductName(updatedProductEntity.getProductName());
            productEntity.setSupplier(updatedProductEntity.getSupplier());
            productEntity.setUnitPrice(updatedProductEntity.getUnitPrice());
            productEntity.setDiscontinued(updatedProductEntity.isDiscontinued());
            return productEntity;
        } else {
            throw new ResourceNotFoundException("Product not found");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable("productId") int productId) {
        try {
            productRepository.deleteById(productId);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not delete product");
        }
    }
}
