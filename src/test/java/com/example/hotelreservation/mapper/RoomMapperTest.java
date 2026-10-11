package com.example.hotelreservation.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.example.hotelreservation.dto.RoomDto;
import com.example.hotelreservation.entity.Room;

class RoomMapperTest {

    private final RoomMapper roomMapper = new RoomMapper();
    
    @Test
    void shouldConvertRoomToResponseDto() {
    	
        Room room = new Room();
        
        room.setRoomId(1L);
        
        room.setRoomNumber("101");
        
        room.setAvailable(true);
        
        RoomDto dto = roomMapper.toResponseDto(room);
        
        assertEquals(1L, dto.getRoomId());
        
        assertEquals("101", dto.getRoomNumber());
        
        assertTrue(dto.getAvailable());
    }
}

