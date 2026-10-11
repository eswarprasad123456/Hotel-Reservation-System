package com.example.hotelreservation.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.hotelreservation.dto.RoomDto;
import com.example.hotelreservation.entity.Amenity;
import com.example.hotelreservation.entity.Room;
import com.example.hotelreservation.entity.RoomType;
import com.example.hotelreservation.exception.AmenityNotFoundException;
import com.example.hotelreservation.exception.RoomNotFoundException;
import com.example.hotelreservation.exception.RoomTypeNotFoundException;
import com.example.hotelreservation.mapper.RoomMapper;
import com.example.hotelreservation.repository.AmenityRepository;
import com.example.hotelreservation.repository.RoomRepository;
import com.example.hotelreservation.repository.RoomTypeRepository;
import com.example.hotelreservation.service.RoomService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    
    private final RoomTypeRepository roomTypeRepository;
    
    private final AmenityRepository amenityRepository;
    
    private final RoomMapper roomMapper;
    
    @Override
    @CacheEvict(value = "availableRooms", allEntries = true)
    public RoomDto addRoom(RoomDto dto) {
    	
        RoomType roomType = roomTypeRepository.findById(dto.getRoomTypeId()).orElseThrow(() -> 
        new RoomTypeNotFoundException("Room type not found with ID: " + dto.getRoomTypeId()));
        
        Room room = new Room();
        
        room.setRoomNumber(dto.getRoomNumber());
        
        room.setAvailable(dto.getAvailable());
        
        room.setRoomType(roomType);
        
        if (dto.getAmenityIds() != null) {
        	
            List<Amenity> amenities = amenityRepository.findAllById(dto.getAmenityIds());
            
            if (amenities.size() != dto.getAmenityIds().size()) {
            	
                throw new AmenityNotFoundException("One or more amenities not found");
            }
            
            room.setAmenities(amenities);
        }
        
        else {
        	
            room.setAmenities(new ArrayList<>());
        }
        
        return roomMapper.toResponseDto(roomRepository.save(room));
    }
    
    @Override
    public List<RoomDto> getAllRooms() {
    	
        return roomRepository.findAll().stream().map(roomMapper::toResponseDto).toList();
    }
    
    @Override
    @Cacheable("availableRooms")
    public List<RoomDto> getAvailableRooms() {
    	
        return roomRepository.findByAvailableTrue().stream().map(roomMapper::toResponseDto).toList();
    }
    
    @Override
    public RoomDto getRoomById(Long id) {
    	
        Room room = roomRepository.findById(id).orElseThrow(() -> 
        new RoomNotFoundException("Room not found with ID: " + id));
        
        return roomMapper.toResponseDto(room);
    }
    
    @Transactional
    @Override
    @CacheEvict(value = "availableRooms", allEntries = true)
    public RoomDto updateRoom(Long id, RoomDto dto) {
    	
        Room room = roomRepository.findById(id).orElseThrow(() -> 
        new RoomNotFoundException("Room not found with ID: " + id));
        
        RoomType roomType = roomTypeRepository.findById(dto.getRoomTypeId()).orElseThrow(() -> 
        new RoomTypeNotFoundException("Room type not found with ID: " + dto.getRoomTypeId()));
        
        room.setRoomNumber(dto.getRoomNumber());
        
        room.setAvailable(dto.getAvailable());
        
        room.setRoomType(roomType);
        
        if (dto.getAmenityIds() != null) {
        	
            List<Amenity> amenities = amenityRepository.findAllById(dto.getAmenityIds());
            
            if (amenities.size() != dto.getAmenityIds().size()) {
            	
                throw new AmenityNotFoundException("One or more amenities not found");
            }
            
            room.setAmenities(amenities);
        }
        
        return roomMapper.toResponseDto(roomRepository.save(room));
    }
    
    @Override
    @CacheEvict(value = "availableRooms", allEntries = true)
    public void deleteRoom(Long id) {
    	
        Room room = roomRepository.findById(id).orElseThrow(() -> 
        new RoomNotFoundException("Room not found with ID: " + id));
        
        roomRepository.delete(room);
    }
    
    @Override
    public Page<RoomDto> getRoomsPage(Pageable pageable) {
    	
        return roomRepository.findAll(pageable).map(roomMapper::toResponseDto);
    }
    
    @Override
    public long getAvailableRoomCount() {
    	
        return roomRepository.countRoomsByAvailability(true);
    }
}
