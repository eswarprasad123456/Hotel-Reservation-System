package com.example.hotelreservation.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.example.hotelreservation.dto.AmenityDto;
import com.example.hotelreservation.entity.Amenity;
import com.example.hotelreservation.exception.AmenityNotFoundException;
import com.example.hotelreservation.mapper.AmenityMapper;
import com.example.hotelreservation.repository.AmenityRepository;
import com.example.hotelreservation.service.AmenityService;

@Service
@RequiredArgsConstructor
public class AmenityServiceImpl implements AmenityService {

    private final AmenityRepository repository;
    
    private final AmenityMapper mapper;
    
    @Override
    public AmenityDto addAmenity(AmenityDto dto) {
    	
        return mapper.toResponseDto(repository.save(mapper.toEntity(dto)));
    }
    
    @Override
    public List<AmenityDto> getAllAmenities() {
    	
        return repository.findAll().stream().map(mapper::toResponseDto).toList();
    }
    
    @Override
    public AmenityDto getAmenityById(Long id) {
    	
        Amenity amenity = repository.findById(id).orElseThrow(() -> 
        new AmenityNotFoundException("Amenity not found with ID: " + id));
        
        return mapper.toResponseDto(amenity);
    }
    
    @Override
    public AmenityDto updateAmenity(Long id, AmenityDto dto) {
    	
        Amenity amenity = repository.findById(id).orElseThrow(() -> 
        new AmenityNotFoundException("Amenity not found with ID: " + id));
        
        mapper.updateEntity(dto, amenity);
        
        return mapper.toResponseDto(repository.save(amenity));
    }
    
    @Override
    public void deleteAmenity(Long id) {
    	
        Amenity amenity = repository.findById(id).orElseThrow(() -> 
        new AmenityNotFoundException("Amenity not found with ID: " + id));
        
        repository.delete(amenity);
    }
}
