package com.example.hotelreservation.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.hotelreservation.dto.RoomTypeDto;
import com.example.hotelreservation.entity.RoomType;
import com.example.hotelreservation.exception.RoomTypeNotFoundException;
import com.example.hotelreservation.mapper.RoomTypeMapper;
import com.example.hotelreservation.repository.RoomRepository;
import com.example.hotelreservation.repository.RoomTypeRepository;
import com.example.hotelreservation.service.RoomTypeService;

@Service
@RequiredArgsConstructor
public class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeRepository repository;
    
    private final RoomTypeMapper mapper;
    
    private final RoomRepository roomRepository;
    
    @Override
    public RoomTypeDto addRoomType(RoomTypeDto dto) {
    	
        RoomType roomType = mapper.toEntity(dto);
        
        return mapper.toResponseDto(repository.save(roomType));
    }
    
    @Override
    public List<RoomTypeDto> getAllRoomTypes() {
    	
        return repository.findAll().stream().map(mapper::toResponseDto).toList();
    }
    
    @Override
    public RoomTypeDto getRoomTypeById(Long id) {
    	
        RoomType roomType = repository.findById(id).orElseThrow(() -> 
        new RoomTypeNotFoundException("Room type not found with ID: " + id));
        
        return mapper.toResponseDto(roomType);
    }
    
    @Override
    public RoomTypeDto updateRoomType(Long id, RoomTypeDto dto) {
    	
        RoomType roomType = repository.findById(id).orElseThrow(() -> 
        new RoomTypeNotFoundException("Room type not found with ID: " + id));
        
        mapper.updateEntity(dto, roomType);
        
        return mapper.toResponseDto(repository.save(roomType));
    }
    
    @Transactional
    @Override
    public void deleteRoomType(Long id) {
    	
        RoomType roomType = repository.findById(id).orElseThrow(() -> 
        new RoomTypeNotFoundException("Room type not found with ID: " + id));
        
        if (roomRepository.existsByRoomTypeRoomTypeId(id)) {
        	
            throw new IllegalArgumentException("Cannot delete room type because rooms are associated with it.");
        }
        
        repository.delete(roomType);
    }
}
