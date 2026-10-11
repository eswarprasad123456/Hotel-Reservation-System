package com.example.hotelreservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelreservation.entity.RoomType;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
	
}
