package com.example.OnlineStore.controller;

import com.example.OnlineStore.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable Integer id) {
        try {
            String productJson = productService.getProductById(id);
            if (productJson != null) {
                return ResponseEntity.ok().body(productJson);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<String> getAllProducts() {
        try {
            String productsJson = productService.getAllProducts();
            return ResponseEntity.ok().body(productsJson);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody String productJson) {
        try {
            String createProductJson = productService.createProduct(productJson);
            return ResponseEntity.status(HttpStatus.CREATED).body(createProductJson);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Integer id, @RequestBody String productJson) {
        try {
            String updatedProductJson = productService.updateProduct(id, productJson);
            return ResponseEntity.ok(updatedProductJson);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
