package com.example.hotelreservation.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {

    private Long roomId;
    
    @NotBlank(message = "Room number is required")
    private String roomNumber;
    
    @NotNull(message = "Availability is required")
    private Boolean available;
    
    @NotNull(message = "Room type ID is required")
    private Long roomTypeId;
    
    private List<Long> amenityIds;
}
