package com.example.OnlineStore.controller;

import com.example.OnlineStore.entity.Order;
import com.example.OnlineStore.service.OrderService;
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

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void shouldReturn_AllOrders() throws Exception {
        Order order = new Order();
        order.setOrderId(1);
        order.setShippingAddress("123 Main St");
        order.setTotalPrice(100.0);

        when(orderService.getAllOrders()).thenReturn(Collections.singletonList(order));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].shippingAddress").value("123 Main St"));
    }

    @Test
    public void shouldReturn_OrderByCurrentId() throws Exception {
        Order order = new Order();
        order.setOrderId(1);
        order.setShippingAddress("123 Main St");
        order.setTotalPrice(100.0);

        when(orderService.getOrderById(anyInt())).thenReturn(Optional.of(order));

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.shippingAddress").value("123 Main St"));
    }

    @Test
    public void shouldCreate_NewOrder() throws Exception {
        Order order = new Order();
        order.setShippingAddress("456 Elm St");
        order.setTotalPrice(150.0);

        when(orderService.createOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shippingAddress").value("456 Elm St"));
    }

    @Test
    public void shouldUpdate_ExistingOrder() throws Exception {
        Order order = new Order();
        order.setOrderId(1);
        order.setShippingAddress("Updated Address");
        order.setTotalPrice(200.0);

        when(orderService.updateOrder(anyInt(), any(Order.class))).thenReturn(order);

        mockMvc.perform(put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shippingAddress").value("Updated Address"));
    }

    @Test
    public void shouldDelete_Order() throws Exception {
        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isNoContent());
    }
}
