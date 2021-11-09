package com.springjpa.controllers;

import com.springjpa.entity.Product;
import com.springjpa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/products")
public class ProductRestController {
    @Autowired
    ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String productName) {
        try{
            List<Product> products = new ArrayList<>();

            if (productName == null){
                products.addAll(productRepository.findAll());
            } else {
                products.addAll(productRepository.findAllByProductName(productName));
            }

            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") int productId) {
        Optional<Product> productData = productRepository.findById(productId);

        return productData.map(product -> new ResponseEntity<>(product, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product newProduct = productRepository
                    .save(new Product(product.getProductName(), product.getSupplier(), product.getUnitPrice(), product.isDiscontinued()));
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable("productId") int productId, @RequestBody Product updatedProduct) {
        Optional<Product> productData = productRepository.findById(productId);

        if (productData.isPresent()) {
            Product product = productData.get();
            product.setProductName(updatedProduct.getProductName());
            product.setSupplier(updatedProduct.getSupplier());
            product.setUnitPrice(updatedProduct.getUnitPrice());
            product.setDiscontinued(updatedProduct.isDiscontinued());
            return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("productId") int productId) {
        try {
            productRepository.deleteById(productId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
