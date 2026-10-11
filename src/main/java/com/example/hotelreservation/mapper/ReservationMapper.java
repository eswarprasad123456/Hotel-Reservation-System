package com.example.hotelreservation.mapper;

import org.springframework.stereotype.Component;

import com.example.hotelreservation.dto.ReservationDto;
import com.example.hotelreservation.entity.Reservation;

@Component
public class ReservationMapper {

    public ReservationDto toResponseDto(Reservation reservation) {
    	
        ReservationDto dto = new ReservationDto();
        
        dto.setReservationId(reservation.getReservationId());
        
        dto.setCheckInDate(reservation.getCheckInDate());
        
        dto.setCheckOutDate(reservation.getCheckOutDate());
        
        dto.setNumberOfGuests(reservation.getNumberOfGuests());
        
        if (reservation.getStatus() != null) {
            dto.setStatus(reservation.getStatus().name());
        }
        
        if (reservation.getCustomer() != null) {
            dto.setCustomerId(reservation.getCustomer().getCustomerId());
        }
        
        if (reservation.getRoom() != null) {
            dto.setRoomId(reservation.getRoom().getRoomId());
        }
        
        return dto;
    }
}
