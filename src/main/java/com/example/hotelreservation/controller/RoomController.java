package com.example.hotelreservation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.example.hotelreservation.dto.RoomDto;
import com.example.hotelreservation.service.RoomService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Room APIs", description = "Manage hotel rooms and availability")
@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService service;
    
    @Operation(summary = "Create a room")
    @PostMapping
    public ResponseEntity<RoomDto> addRoom(@Valid @RequestBody RoomDto dto) {
    	
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addRoom(dto));
    }
    
    @Operation(summary = "Get all rooms")
    @GetMapping
    public ResponseEntity<List<RoomDto>>getAllRooms() {
    	
        return ResponseEntity.ok(service.getAllRooms());
    }
    
    @Operation(summary = "Get all available rooms")
    @GetMapping("/available")
    public ResponseEntity<List<RoomDto>>getAvailableRooms() {
    	
        return ResponseEntity.ok(service.getAvailableRooms());
    }
    
    @Operation(summary = "Get room by id")
    @GetMapping("/{id}")
    public ResponseEntity<RoomDto>getRoomById(@PathVariable Long id) {
    	
        return ResponseEntity.ok(service.getRoomById(id));
    }
    
    @Operation(summary = "Update a room")
    @PutMapping("/{id}")
    public ResponseEntity<RoomDto>updateRoom(@PathVariable Long id, @Valid @RequestBody RoomDto dto) {
    	
        return ResponseEntity.ok(service.updateRoom(id, dto));
    }
    
    @Operation(summary = "Delete a room")
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteRoom(@PathVariable Long id) {
    	
        service.deleteRoom(id);
        
        return ResponseEntity.ok("Room deleted successfully");
    }
    
    @Operation(summary = "Get available room count")
    @GetMapping("/available/count")
    public ResponseEntity<Long> getAvailableRoomCount() {
    	
        return ResponseEntity.ok(service.getAvailableRoomCount());
    }
    
    @Operation(summary = "Get rooms with pagination and sorting")
    @GetMapping("/page")
    public ResponseEntity<Page<RoomDto>> getRoomsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "roomId") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
    	
        if (page < 0 || size < 1 || size > 100) {
        	
        	throw new IllegalArgumentException("Page must be non-negative and size must be between 1 and 100");
        }
        
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        
        return ResponseEntity.ok(service.getRoomsPage(pageable));
    }
}
