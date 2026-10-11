package com.example.hotelreservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hotelreservation.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByAvailableTrue();
    
    Page<Room> findAll(Pageable pageable);
    
    @Query(value = "SELECT COUNT(r) FROM Room r WHERE r.available = :available")
    long countRoomsByAvailability(@Param("available") boolean available);
    
    boolean existsByRoomTypeRoomTypeId(Long roomTypeId);
}