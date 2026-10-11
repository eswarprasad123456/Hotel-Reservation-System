package com.example.hotelreservation.service;

import java.util.List;

import com.example.hotelreservation.dto.RoomTypeDto;

public interface RoomTypeService {

    RoomTypeDto addRoomType(RoomTypeDto dto);
    
    List<RoomTypeDto> getAllRoomTypes();
    
    RoomTypeDto getRoomTypeById(Long id);
    
    RoomTypeDto updateRoomType(Long id, RoomTypeDto dto);
    
    void deleteRoomType(Long id);
}
