package com.example.hotelreservation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.example.hotelreservation.dto.ReservationDto;
import com.example.hotelreservation.service.ReservationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Reservation APIs", description = "Manage hotel reservations and booking status")
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;
    
    @Operation(summary = "Create a reservation")
    @PostMapping
    public ResponseEntity<ReservationDto>createReservation(@Valid @RequestBody ReservationDto dto) {
    	
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createReservation(dto));
    }
    
    @Operation(summary = "Get all reservations")
    @GetMapping
    public ResponseEntity<List<ReservationDto>>getAllReservations() {
    	
        return ResponseEntity.ok(service.getAllReservations());
    }
    
    @Operation(summary = "Get reservation by id")
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto>getReservationById(@PathVariable Long id) {
    	
        return ResponseEntity.ok(service.getReservationById(id));
    }
    
    @Operation(summary = "Get reservation by customer")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ReservationDto>>getReservationsByCustomer(@PathVariable Long customerId) {
    	
        return ResponseEntity.ok(service.getReservationsByCustomer(customerId));
    }
    
    @Operation(summary = "Update a reservation")
    @PutMapping("/{id}")
    public ResponseEntity<ReservationDto>updateReservation(@PathVariable Long id, @Valid @RequestBody ReservationDto dto) {
    	
        return ResponseEntity.ok(service.updateReservation(id, dto));
    }
    
    @Operation(summary = "Cancel a reservation")
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<String>cancelReservation(@PathVariable Long id) {
    	
        service.cancelReservation(id);
        
        return ResponseEntity.ok("Reservation cancelled successfully");
    }
    
    @Operation(summary = "Check out a reservation")
    @PutMapping("/{id}/checkout")
    public ResponseEntity<String> checkOutReservation(@PathVariable Long id) {
    	
        service.checkOutReservation(id);
        
        return ResponseEntity.ok("Customer checked out. Room is now available.");
    }
}
