package com.example.OnlineStore.controller;

import com.example.OnlineStore.entity.Product;
import com.example.OnlineStore.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void shouldReturn_AllProducts() throws Exception {
        Product product = new Product();
        product.setProductId(1);
        product.setName("Product 1");
        product.setPrice(99.99);
        product.setQuantityInStock(10);

        when(productService.getAllProducts()).thenReturn(Collections.singletonList(product));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Product 1"));
    }

    @Test
    public void shouldReturn_ProductByCurrentId() throws Exception {
        Product product = new Product();
        product.setProductId(1);
        product.setName("Product 1");
        product.setPrice(99.99);
        product.setQuantityInStock(10);

        when(productService.getProductById(anyInt())).thenReturn(Optional.of(product));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Product 1"));
    }

    @Test
    public void shouldCreate_NewProduct() throws Exception {
        Product product = new Product();
        product.setName("Product 2");
        product.setPrice(49.99);
        product.setQuantityInStock(20);

        when(productService.createProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Product 2"));
    }

    @Test
    public void shouldUpdate_ExistingProduct() throws Exception {
        Product product = new Product();
        product.setProductId(1);
        product.setName("Updated Product");
        product.setPrice(59.99);
        product.setQuantityInStock(15);

        when(productService.updateProduct(anyInt(), any(Product.class))).thenReturn(product);

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product"));
    }

    @Test
    public void shouldDelete_Product() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }
}

