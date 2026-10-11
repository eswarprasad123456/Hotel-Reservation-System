package com.example.hotelreservation.service;

import java.util.List;

import com.example.hotelreservation.dto.RoomDto;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface RoomService {

    RoomDto addRoom(RoomDto dto);
    
    List<RoomDto> getAllRooms();
    
    List<RoomDto> getAvailableRooms();
    
    RoomDto getRoomById(Long id);
    
    RoomDto updateRoom(Long id, RoomDto dto);
    
    void deleteRoom(Long id);
    
    Page<RoomDto>getRoomsPage(Pageable pageable);
    
    long getAvailableRoomCount();
}
