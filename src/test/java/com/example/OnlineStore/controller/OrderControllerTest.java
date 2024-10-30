package com.example.OnlineStore.controller;

import com.example.OnlineStore.service.OrderService;
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

public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturn_OrderById() {
        String orderJson = "{\"id\":1,\"product\":\"Product A\"}";
        when(orderService.getOrderById(1)).thenReturn(orderJson);

        ResponseEntity<String> response = orderController.getOrderById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderJson, response.getBody());
    }

    @Test
    public void shouldReturn_NotFoundWhenOrderNotExists() {
        when(orderService.getOrderById(anyInt())).thenThrow(new RuntimeException("Order not found"));

        ResponseEntity<String> response = orderController.getOrderById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void shouldReturn_AllOrders() {
        String ordersJson = "[{\"id\":1,\"product\":\"Product A\"}]";
        when(orderService.getAllOrders()).thenReturn(ordersJson);

        ResponseEntity<String> response = orderController.getAllOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ordersJson, response.getBody());
    }

    @Test
    public void shouldReturn_CreatedOrder() {
        String orderJson = "{\"product\":\"Product A\"}";
        String createdOrderJson = "{\"id\":1,\"product\":\"Product A\"}";
        when(orderService.createOrder(orderJson)).thenReturn(createdOrderJson);

        ResponseEntity<String> response = orderController.createOrder(orderJson);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdOrderJson, response.getBody());
    }

    @Test
    public void shouldReturn_UpdatedOrder() {
        String orderJson = "{\"product\":\"Product A Updated\"}";
        String updatedOrderJson = "{\"id\":1,\"product\":\"Product A Updated\"}";
        when(orderService.updateOrder(1, orderJson)).thenReturn(updatedOrderJson);

        ResponseEntity<String> response = orderController.updateOrder(1, orderJson);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedOrderJson, response.getBody());
    }

    @Test
    public void shouldReturn_NoContentOnDelete() {
        doNothing().when(orderService).deleteOrder(1);

        ResponseEntity<Void> response = orderController.deleteOrder(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
