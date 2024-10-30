package com.example.OnlineStore.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "customers")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "first_name")
    @NotBlank(message = "Name required")
    @JsonProperty("firstName")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Last name required")
    @JsonProperty("lastName")
    private String lastName;

    @Email(message = "Email must be valid")
    @NotBlank
    @JsonProperty("email")
    private String email;

    @Column(name = "contact_number")
    @JsonProperty("contactNumber")
    private String contactNumber;

}
