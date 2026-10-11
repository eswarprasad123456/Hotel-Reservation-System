package com.example.hotelreservation.mapper;

import org.springframework.stereotype.Component;

import com.example.hotelreservation.dto.RoomTypeDto;
import com.example.hotelreservation.entity.RoomType;

@Component
public class RoomTypeMapper {

    public RoomType toEntity(RoomTypeDto dto) {
    	
        RoomType roomType = new RoomType();
        
        roomType.setTypeName(dto.getTypeName());
        
        roomType.setPricePerNight(dto.getPricePerNight());
        
        return roomType;
    }
    
    public RoomTypeDto toResponseDto(RoomType roomType) {
    	
        RoomTypeDto dto = new RoomTypeDto();
        
        dto.setRoomTypeId(roomType.getRoomTypeId());
        
        dto.setTypeName(roomType.getTypeName());
        
        dto.setPricePerNight(roomType.getPricePerNight());
        
        return dto;
    }
    
    public void updateEntity(RoomTypeDto dto, RoomType roomType) {
    	
        roomType.setTypeName(dto.getTypeName());
        
        roomType.setPricePerNight(dto.getPricePerNight());
    }
}
