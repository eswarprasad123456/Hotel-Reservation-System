package com.example.hotelreservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelreservation.entity.Amenity;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
	
}
