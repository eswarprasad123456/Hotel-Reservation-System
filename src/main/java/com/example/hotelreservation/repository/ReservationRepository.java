package com.example.hotelreservation.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelreservation.entity.Reservation;
import com.example.hotelreservation.entity.ReservationStatus;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByCustomerCustomerId(Long customerId);
    
    List<Reservation> findByStatus(ReservationStatus status);
    
    boolean existsByRoomRoomIdAndStatusAndCheckInDateLessThanAndCheckOutDateGreaterThan(
    		Long roomId, ReservationStatus status, LocalDate checkOutDate, LocalDate checkInDate);
    
    List<Reservation>findByStatusAndCheckOutDateBefore(ReservationStatus status, LocalDate date);
    
    List<Reservation>findByRoomRoomId(Long roomId);
    
}