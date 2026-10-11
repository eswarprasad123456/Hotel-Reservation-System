package com.example.hotelreservation.mapper;

import org.springframework.stereotype.Component;

import com.example.hotelreservation.dto.AmenityDto;
import com.example.hotelreservation.entity.Amenity;

@Component
public class AmenityMapper {

    public Amenity toEntity(AmenityDto dto) {
    	
        Amenity amenity = new Amenity();
        
        amenity.setAmenityId(dto.getAmenityId());
        
        amenity.setAmenityName(dto.getAmenityName());
        
        return amenity;
    }
    
    public AmenityDto toResponseDto(Amenity amenity) {
    	
        AmenityDto dto = new AmenityDto();
        
        dto.setAmenityId(amenity.getAmenityId());
        
        dto.setAmenityName(amenity.getAmenityName());
        
        return dto;
    }
    
    public void updateEntity(AmenityDto dto, Amenity amenity) {
    	
        amenity.setAmenityName(dto.getAmenityName());
    }
}
