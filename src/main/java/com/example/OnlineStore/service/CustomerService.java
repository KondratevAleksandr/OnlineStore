package com.example.OnlineStore.service;

import com.example.OnlineStore.entity.Customer;
import com.example.OnlineStore.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.objectMapper = new ObjectMapper();
    }

    public String getCustomerById(Integer id) {
        return customerRepository.findById(id)
                .map(customer -> {
                    try {
                        return objectMapper.writeValueAsString(customer);
                    } catch (Exception e) {
                        throw new RuntimeException("Error serializing customer: " + e.getMessage());
                    }
                })
                .orElse(null);
    }

    public String getAllCustomers() {
        try {
            List<Customer> customers = customerRepository.findAll();
            return objectMapper.writeValueAsString(customers);
        } catch (Exception e) {
            throw new RuntimeException("Error get all customer: " + e.getMessage());
        }
    }

    public String createCustomer(String customerJson) {
        try {
            Customer customer = objectMapper.readValue(customerJson, Customer.class);
            Customer createdCustomer = customerRepository.save(customer);
            return objectMapper.writeValueAsString(createdCustomer);
        } catch (Exception e) {
            throw new RuntimeException("Error creating customer:" + e.getMessage());
        }
    }

    public String updateCustomer(Integer id, String customerJson) {
        try {
            Customer customerDetails = objectMapper.readValue(customerJson, Customer.class);
            Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found."));
            customer.setFirstName(customerDetails.getFirstName());
            customer.setLastName(customerDetails.getLastName());
            customer.setEmail(customerDetails.getEmail());
            customer.setContactNumber(customerDetails.getContactNumber());
            Customer updatedCustomer = customerRepository.save(customer);
            return objectMapper.writeValueAsString(updatedCustomer);
        } catch (Exception e) {
            throw new RuntimeException("Error updating customer: " + e.getMessage());
        }
    }

    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }
}
