package com.example.OnlineStore.controller;

import com.example.OnlineStore.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturn_ProductById() {
        String productJson = "{\"id\":1,\"name\":\"Product A\"}";
        when(productService.getProductById(1)).thenReturn(productJson);

        ResponseEntity<String> response = productController.getProductById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productJson, response.getBody());
    }

    @Test
    public void shouldReturn_NotFoundWhenProductNotExists() {
        when(productService.getProductById(anyInt())).thenThrow(new RuntimeException("Product not found"));

        ResponseEntity<String> response = productController.getProductById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void shouldReturn_AllProducts() {
        String productsJson = "[{\"id\":1,\"name\":\"Product A\"}]";
        when(productService.getAllProducts()).thenReturn(productsJson);

        ResponseEntity<String> response = productController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productsJson, response.getBody());
    }

    @Test
    public void shouldReturn_CreatedProduct() {
        String productJson = "{\"name\":\"Product A\"}";
        String createdProductJson = "{\"id\":1,\"name\":\"Product A\"}";
        when(productService.createProduct(productJson)).thenReturn(createdProductJson);

        ResponseEntity<String> response = productController.createProduct(productJson);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdProductJson, response.getBody());
    }

    @Test
    public void shouldReturn_UpdatedProduct() {
        String productJson = "{\"name\":\"Product A Updated\"}";
        String updatedProductJson = "{\"id\":1,\"name\":\"Product A Updated\"}";
        when(productService.updateProduct(1, productJson)).thenReturn(updatedProductJson);

        ResponseEntity<String> response = productController.updateProduct(1, productJson);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedProductJson, response.getBody());
    }

    @Test
    public void shouldReturn_NoContentOnDelete() {
        doNothing().when(productService).deleteProduct(1);

        ResponseEntity<Void> response = productController.deleteProduct(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
