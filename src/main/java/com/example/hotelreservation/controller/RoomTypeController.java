package com.example.hotelreservation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.example.hotelreservation.dto.RoomTypeDto;
import com.example.hotelreservation.service.RoomTypeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Room Type APIs", description = "Manage room categories and nightly prices")
@RestController
@RequestMapping("/room-types")
@RequiredArgsConstructor
public class RoomTypeController {

    private final RoomTypeService service;
    
    @Operation(summary = "Create a room type")
    @PostMapping
    public ResponseEntity<RoomTypeDto> addRoomType(@Valid @RequestBody RoomTypeDto dto) {
    	
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addRoomType(dto));
    }
    
    @Operation(summary = "Get all room types")
    @GetMapping
    public ResponseEntity<List<RoomTypeDto>>getAllRoomTypes() {
    	
        return ResponseEntity.ok(service.getAllRoomTypes());
    }
    
    @Operation(summary = "Get all rooms types by id")
    @GetMapping("/{id}")
    public ResponseEntity<RoomTypeDto>getRoomTypeById(@PathVariable Long id) {
    	
        return ResponseEntity.ok(service.getRoomTypeById(id));
    }
    
    @Operation(summary = "Update a room type")
    @PutMapping("/{id}")
    public ResponseEntity<RoomTypeDto>updateRoomType(@PathVariable Long id, @Valid @RequestBody RoomTypeDto dto) {
    	
        return ResponseEntity.ok(service.updateRoomType(id, dto));
    }
    
    @Operation(summary = "Delete a room type")
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteRoomType(@PathVariable Long id) {
    	
        service.deleteRoomType(id);
        
        return ResponseEntity.ok("Room type deleted successfully");
    }
}
