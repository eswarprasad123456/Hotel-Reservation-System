package com.example.hotelreservation.serviceimpl;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.hotelreservation.entity.Reservation;
import com.example.hotelreservation.entity.ReservationStatus;
import com.example.hotelreservation.entity.Room;
import com.example.hotelreservation.repository.ReservationRepository;
import com.example.hotelreservation.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReservationScheduler {

    private static final Logger log = LoggerFactory.getLogger(ReservationScheduler.class);
    
    private final ReservationRepository reservationRepository;
    
    private final RoomRepository roomRepository;
    
    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    @CacheEvict(value = "availableRooms", allEntries = true)
    public void completeExpiredReservations() {
    	
        LocalDate today = LocalDate.now();
        
        log.info("Starting expired reservation scheduler for date: {}", today);
        
        List<Reservation> expiredReservations = reservationRepository.findByStatusAndCheckOutDateBefore(ReservationStatus.CONFIRMED, today);
        
        if (expiredReservations.isEmpty()) {
        	
            log.info("No expired reservations found.");
            
            return;
        }
        
        for (Reservation reservation : expiredReservations) {
        	
            reservation.setStatus(ReservationStatus.COMPLETED);
            
            reservationRepository.save(reservation);
            
            Room room = reservation.getRoom();
            
            if (room != null) {
            	
                boolean hasBookingToday = reservationRepository.findByRoomRoomId(room.getRoomId()).stream().anyMatch(r -> 
                r.getStatus() == ReservationStatus.CONFIRMED && !r.getCheckInDate().isAfter(today) && r.getCheckOutDate().isAfter(today));
                
                room.setAvailable(!hasBookingToday);
                
                roomRepository.save(room);
                
                log.info("Room ID: {} availability updated to: {}", room.getRoomId(), !hasBookingToday);
            }
            
            log.info("Reservation ID: {} marked as COMPLETED", reservation.getReservationId());
        }
        
        log.info("Expired reservation scheduler completed. Total reservations processed: {}", expiredReservations.size());
    }
}
