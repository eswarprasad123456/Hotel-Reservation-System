package com.example.hotelreservation.service;

import java.util.List;

import com.example.hotelreservation.dto.ReservationDto;

public interface ReservationService {

    ReservationDto createReservation(ReservationDto dto);
    
    List<ReservationDto> getAllReservations();
    
    ReservationDto getReservationById(Long id);
    
    List<ReservationDto> getReservationsByCustomer(Long customerId);
    
    ReservationDto updateReservation(Long id, ReservationDto dto);
    
    void cancelReservation(Long id);
    
    void checkOutReservation(Long id);
}
