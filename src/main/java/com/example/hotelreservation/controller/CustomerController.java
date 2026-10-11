package com.example.hotelreservation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.example.hotelreservation.dto.CustomerDto;
import com.example.hotelreservation.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer APIs", description = "Manage hotel customers")
@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;
    
    @Operation(summary = "Create a customer")
    @PostMapping
    public ResponseEntity<CustomerDto> addCustomer(@Valid @RequestBody CustomerDto dto) {
    	
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addCustomer(dto));
    }
    
    @Operation(summary = "Get all customers")
    @GetMapping
    public ResponseEntity<List<CustomerDto>>getAllCustomers() {
    	
        return ResponseEntity.ok(service.getAllCustomers());
    }
    
    @Operation(summary = "Get all customer by id")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto>getCustomerById(@PathVariable Long id) {
    	
        return ResponseEntity.ok(service.getCustomerById(id));
    }
    
    @Operation(summary = "Update a customer")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto>updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDto dto) {
    	
        return ResponseEntity.ok(service.updateCustomer(id, dto));
    }
    
    @Operation(summary = "Delete a customer")
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteCustomer(@PathVariable Long id) {
    	
        service.deleteCustomer(id);
        
        return ResponseEntity.ok("Customer deleted successfully");
    }
}
