package com.example.OnlineStore.controller;

import com.example.OnlineStore.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getOrderById(@PathVariable Integer id) {
        try {
            String orderJson = orderService.getOrderById(id);
            if (orderJson != null) {
                return ResponseEntity.ok().body(orderJson);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch(RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<String> getAllOrders() {
        try {
            String orderJson = orderService.getAllOrders();
            return ResponseEntity.ok().body(orderJson);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody String orderJson) {
        try {
            String createdOrderJson = orderService.createOrder(orderJson);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderJson);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable Integer id, @RequestBody String orderJson) {
        try {
            String updatedOrderJson = orderService.updateOrder(id, orderJson);
            return ResponseEntity.ok(updatedOrderJson);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
