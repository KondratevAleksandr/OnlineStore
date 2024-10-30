package com.example.OnlineStore.controller;


import com.example.OnlineStore.service.CustomerService;
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

public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturn_CustomerById() {
        String customerJson = "{\"id\":1,\"name\":\"John Doe\"}";
        when(customerService.getCustomerById(1)).thenReturn(customerJson);

        ResponseEntity<String> response = customerController.getCustomerById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerJson, response.getBody());
    }

    @Test
    public void shouldReturn_NotFoundWhenCustomerNotExists() {
        when(customerService.getCustomerById(anyInt())).thenThrow(new RuntimeException("Customer not found"));

        ResponseEntity<String> response = customerController.getCustomerById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void shouldReturn_AllCustomers() {
        String customersJson = "[{\"id\":1,\"name\":\"John Doe\"}]";
        when(customerService.getAllCustomers()).thenReturn(customersJson);

        ResponseEntity<String> response = customerController.getAllCustomers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customersJson, response.getBody());
    }

    @Test
    public void shouldReturn_CreatedCustomer() {
        String customerJson = "{\"name\":\"John Doe\"}";
        String createdCustomerJson = "{\"id\":1,\"name\":\"John Doe\"}";
        when(customerService.createCustomer(customerJson)).thenReturn(createdCustomerJson);

        ResponseEntity<String> response = customerController.createCustomer(customerJson);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdCustomerJson, response.getBody());
    }

    @Test
    public void shouldReturn_UpdatedCustomer() {
        String customerJson = "{\"name\":\"John Doe Updated\"}";
        String updatedCustomerJson = "{\"id\":1,\"name\":\"John Doe Updated\"}";
        when(customerService.updateCustomer(1, customerJson)).thenReturn(updatedCustomerJson);

        ResponseEntity<String> response = customerController.updateCustomer(1, customerJson);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCustomerJson, response.getBody());
    }

    @Test
    public void shouldReturn_NoContentOnDelete() {
        doNothing().when(customerService).deleteCustomer(1);

        ResponseEntity<Void> response = customerController.deleteCustomer(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
