package com.example.OnlineStore.service;

import com.example.OnlineStore.entity.Order;
import com.example.OnlineStore.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;


    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(Order order) {
        order.setOrderDate(new Date());
        return orderRepository.save(order);
    }

    public Order updateOrder(Integer id, Order orderDetails) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("ordder not found"));
        order.setShippingAddress(orderDetails.getShippingAddress());
        order.setTotalPrice(orderDetails.getTotalPrice());
        order.setOrderStatus(orderDetails.getOrderStatus());
        return orderRepository.save(order);
    }

    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }

}
