package com.example.hotelreservation.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.hotelreservation.dto.RoomDto;
import com.example.hotelreservation.entity.Room;
import com.example.hotelreservation.entity.Amenity;

@Component
public class RoomMapper {

    public RoomDto toResponseDto(Room room) {
    	
        RoomDto dto = new RoomDto();
        
        dto.setRoomId(room.getRoomId());
        
        dto.setRoomNumber(room.getRoomNumber());
        
        dto.setAvailable(room.getAvailable());
        
        if (room.getRoomType() != null) {
        	
            dto.setRoomTypeId(room.getRoomType().getRoomTypeId());
        }
        
        if (room.getAmenities() != null) {
        	
            dto.setAmenityIds(room.getAmenities().stream().map(Amenity::getAmenityId).collect(Collectors.toList()));
        }
        
        return dto;
    }
}
