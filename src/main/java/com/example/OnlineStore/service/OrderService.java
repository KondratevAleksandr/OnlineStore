package com.example.OnlineStore.service;

import com.example.OnlineStore.entity.Order;
import com.example.OnlineStore.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    ObjectMapper objectMapper;


    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.objectMapper = new ObjectMapper();
    }

    public String getOrderById(Integer id) {
        return orderRepository.findById(id)
                .map(order -> {
                    try {
                        return objectMapper.writeValueAsString(order);
                    } catch (Exception e) {
                        throw new RuntimeException("Error serializing order: " + e.getMessage());
                    }
                })
                .orElse(null);
    }

    public String getAllOrders() {
        try {
            List<Order> orders = orderRepository.findAll();
            return objectMapper.writeValueAsString(orders);
        } catch (Exception e) {
            throw new RuntimeException("Error get all orders: " + e.getMessage());
        }
    }

    public String createOrder(String orderJson) {
        try {
            Order order = objectMapper.readValue(orderJson, Order.class);
            Order createdOrder = orderRepository.save(order);
            return objectMapper.writeValueAsString(createdOrder);
        } catch (Exception e) {
            throw new RuntimeException(("Error creating order: " + e.getMessage()));
        }
    }

    public String updateOrder(Integer id, String orderJson) {
        try {
            Order orderDetails = objectMapper.readValue(orderJson, Order.class);
            Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
            order.setShippingAddress(orderDetails.getShippingAddress());
            order.setTotalPrice(orderDetails.getTotalPrice());
            order.setOrderStatus(orderDetails.getOrderStatus());
            Order updatedOrder = orderRepository.save(order);
            return objectMapper.writeValueAsString(updatedOrder);
        } catch (Exception e) {
            throw new RuntimeException("Error updating order: " + e.getMessage());
        }
    }

    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }

}
