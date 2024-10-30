package com.example.OnlineStore.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @NotBlank(message = "Wrong product name")
    @Column(name = "product_name")
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @DecimalMin(value = "0.0", inclusive = false)
    @NotNull
    @JsonProperty("price")
    private double price;

    @DecimalMin(value = "0")
    @Column(name = "quantity_in_stock")
    @JsonProperty("quantityInStock")
    private int quantityInStock;
}
