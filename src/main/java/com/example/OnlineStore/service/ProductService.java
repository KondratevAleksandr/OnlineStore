package com.example.OnlineStore.service;

import com.example.OnlineStore.entity.Product;
import com.example.OnlineStore.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.objectMapper = new ObjectMapper();
    }

    public String getProductById(Integer id) {
        return productRepository.findById(id)
                .map(product -> {
                    try {
                        return objectMapper.writeValueAsString(product);
                    } catch (Exception e) {
                        throw new RuntimeException("Error serializing product: " + e.getMessage());
                    }
                })
                .orElse(null);
    }

    public String getAllProducts() {
        try {
            List<Product> products = productRepository.findAll();
            return objectMapper.writeValueAsString(products);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing product: " + e.getMessage());
        }
    }

    public String createProduct(String productJson) {
        try {
            Product product = objectMapper.readValue(productJson, Product.class);
            Product createdProduct = productRepository.save(product);
            return objectMapper.writeValueAsString(createdProduct);
        } catch (Exception e) {
            throw new RuntimeException("Error creating product: " + e.getMessage());
        }
    }

    public String updateProduct(Integer id, String productJson) {
        try {
            Product productDetails = objectMapper.readValue(productJson, Product.class);
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setQuantityInStock(productDetails.getQuantityInStock());
            Product updateProduct = productRepository.save(product);
            return objectMapper.writeValueAsString(updateProduct);
        } catch (Exception e) {
            throw new RuntimeException("Error updating product: " + e.getMessage());
        }
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
}
