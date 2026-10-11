package com.example.hotelreservation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.example.hotelreservation.dto.PaymentDto;
import com.example.hotelreservation.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Payment APIs", description = "Manage reservation payments")
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;
    
    @Operation(summary = "Create a payment")
    @PostMapping
    public ResponseEntity<PaymentDto> addPayment(@Valid @RequestBody PaymentDto dto) {
    	
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addPayment(dto));
    }
    
    @Operation(summary = "Get all payments")
    @GetMapping
    public ResponseEntity<List<PaymentDto>>getAllPayments() {
    	
        return ResponseEntity.ok(service.getAllPayments());
    }
    
    @Operation(summary = "Get payment by id")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto>getPaymentById(@PathVariable Long id) {
    	
        return ResponseEntity.ok(service.getPaymentById(id));
    }
    
    @Operation(summary = "Get payment by reservation")
    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<PaymentDto>getPaymentByReservation(@PathVariable Long reservationId) {
    	
        return ResponseEntity.ok(service.getPaymentByReservation(reservationId));
    }
}
