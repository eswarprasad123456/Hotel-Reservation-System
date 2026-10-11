package com.example.hotelreservation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.example.hotelreservation.dto.AmenityDto;
import com.example.hotelreservation.service.AmenityService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Amenity APIs", description = "Manage hotel amenities")
@RestController
@RequestMapping("/amenities")
@RequiredArgsConstructor
public class AmenityController {

    private final AmenityService service;
    
    @Operation(summary = "Create an amenity")
    @PostMapping
    public ResponseEntity<AmenityDto>addAmenity(@Valid @RequestBody AmenityDto dto) {
    	
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addAmenity(dto));
    }
    
    @Operation(summary = "Get all amenities")
    @GetMapping
    public ResponseEntity<List<AmenityDto>>getAllAmenities() {
    	
        return ResponseEntity.ok(service.getAllAmenities());
    }
    
    @Operation(summary = "Get all amenities by id")
    @GetMapping("/{id}")
    public ResponseEntity<AmenityDto>getAmenityById(@PathVariable Long id) {
    	
        return ResponseEntity.ok(service.getAmenityById(id));
    }
    
    @Operation(summary = "Update an amenity")
    @PutMapping("/{id}")
    public ResponseEntity<AmenityDto>updateAmenity(@PathVariable Long id, @Valid @RequestBody AmenityDto dto) {
    	
        return ResponseEntity.ok(service.updateAmenity(id, dto));
    }
    
    @Operation(summary = "Delete an amenity")
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteAmenity(@PathVariable Long id) {
    	
        service.deleteAmenity(id);
        
        return ResponseEntity.ok("Amenity deleted successfully");
    }
}
