package com.example.OnlineStore.controller;

import com.example.OnlineStore.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getCustomerById(@PathVariable Integer id) {
        try {
            String customerJson = customerService.getCustomerById(id);
            if (customerJson != null) {
                return ResponseEntity.ok().body(customerJson);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<String> getAllCustomers() {
        try {
            String customersJson = customerService.getAllCustomers();
            return ResponseEntity.ok().body(customersJson);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody String customerJson) {
        try {
            String createdCustomerJson = customerService.createCustomer(customerJson);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomerJson);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable Integer id, @RequestBody String customerJson) {
        try {
            String updatedCustomerJson = customerService.updateCustomer(id, customerJson);
            return ResponseEntity.ok(updatedCustomerJson);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
