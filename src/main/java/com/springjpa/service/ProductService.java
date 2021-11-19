package com.springjpa.service;

import com.springjpa.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    List<ProductEntity> getAllProducts(String productName);
    ProductEntity getProductById(int productId);
    ProductEntity createProduct(ProductEntity productEntity);
    ProductEntity updateProduct(int productId, ProductEntity updatedProductEntity);
    void deleteProduct(int productId);
}
