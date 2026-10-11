package com.example.hotelreservation.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmenityDto {

    private Long amenityId;
    
    @NotBlank(message = "Amenity name is required")
    private String amenityName;
}
