package com.example.hotelreservation.serviceimpl;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.example.hotelreservation.dto.ReservationDto;
import com.example.hotelreservation.entity.Customer;
import com.example.hotelreservation.entity.Reservation;
import com.example.hotelreservation.entity.ReservationStatus;
import com.example.hotelreservation.entity.Room;
import com.example.hotelreservation.exception.CustomerNotFoundException;
import com.example.hotelreservation.exception.ReservationNotFoundException;
import com.example.hotelreservation.exception.RoomNotFoundException;
import com.example.hotelreservation.mapper.ReservationMapper;
import com.example.hotelreservation.repository.CustomerRepository;
import com.example.hotelreservation.repository.ReservationRepository;
import com.example.hotelreservation.repository.RoomRepository;
import com.example.hotelreservation.service.ReservationService;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    
    private final CustomerRepository customerRepository;
    
    private final RoomRepository roomRepository;
    
    private final ReservationMapper reservationMapper;
    
    private static final Logger log = LoggerFactory.getLogger(ReservationServiceImpl.class);
    
    private void validateReservationDates(ReservationDto dto) {
    	
        if (dto.getCheckInDate() == null || dto.getCheckOutDate() == null) {
        	
            throw new IllegalArgumentException("Check-in and check-out dates are required");
        }
        
        if (!dto.getCheckOutDate().isAfter(dto.getCheckInDate())) {
        	
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
    }
    
    @Transactional
    @Override
    @CacheEvict(value = "availableRooms", allEntries = true)
    public ReservationDto createReservation(ReservationDto dto) {
    	
        validateReservationDates(dto);
        
        Customer customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(() -> 
        new CustomerNotFoundException("Customer not found with ID: " + dto.getCustomerId()));
        
        Room room = roomRepository.findById(dto.getRoomId()).orElseThrow(() -> 
        new RoomNotFoundException("Room not found with ID: " + dto.getRoomId()));
        
        validateRoomAvailability(room.getRoomId(), dto.getCheckInDate(), dto.getCheckOutDate(), null);
        
        Reservation reservation = new Reservation();
        
        reservation.setCheckInDate(dto.getCheckInDate());
        
        reservation.setCheckOutDate(dto.getCheckOutDate());
        
        reservation.setNumberOfGuests(dto.getNumberOfGuests());
        
        reservation.setStatus(ReservationStatus.CONFIRMED);
        
        reservation.setCustomer(customer);
        
        reservation.setRoom(room);
        
        Reservation savedReservation = reservationRepository.save(reservation);
        
        refreshRoomAvailability(room);
        
        log.info("Reservation created successfully. Reservation ID: {}", savedReservation.getReservationId());
        
        return reservationMapper.toResponseDto(savedReservation);
    }
    
    @Override
    public List<ReservationDto> getAllReservations() {
    	
        return reservationRepository.findAll().stream().map(reservationMapper::toResponseDto).toList();
    }
    
    @Override
    public ReservationDto getReservationById(Long id) {
    	
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> 
        new ReservationNotFoundException("Reservation not found with ID: " + id));
        
        return reservationMapper.toResponseDto(reservation);
    }
    
    @Override
    public List<ReservationDto> getReservationsByCustomer(Long customerId) {
    	
        if (!customerRepository.existsById(customerId)) {
        	
            throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
        }
        
        return reservationRepository.findByCustomerCustomerId(customerId).stream().map(reservationMapper::toResponseDto).toList();
    }
    
    @Transactional
    @Override
    @CacheEvict(value = "availableRooms", allEntries = true)
    public ReservationDto updateReservation(Long id, ReservationDto dto) {
    	
        log.info("Updating reservation with ID: {}", id);
        
        validateReservationDates(dto);
        
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> 
        new ReservationNotFoundException("Reservation not found with ID: " + id));
        
        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
        	
            throw new IllegalArgumentException("Only confirmed reservations can be updated");
        }
        
        Customer customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(() -> 
        new CustomerNotFoundException("Customer not found with ID: " + dto.getCustomerId()));
        
        Room oldRoom = reservation.getRoom();
        
        Room newRoom = roomRepository.findById(dto.getRoomId()).orElseThrow(() -> 
        new RoomNotFoundException("Room not found with ID: " + dto.getRoomId()));
        
        validateRoomAvailability(newRoom.getRoomId(), dto.getCheckInDate(), dto.getCheckOutDate(), reservation.getReservationId());
        
        reservation.setCheckInDate(dto.getCheckInDate());
        
        reservation.setCheckOutDate(dto.getCheckOutDate());
        
        reservation.setNumberOfGuests(dto.getNumberOfGuests());
        
        reservation.setCustomer(customer);
        
        reservation.setRoom(newRoom);
        
        Reservation updatedReservation = reservationRepository.save(reservation);
        
        refreshRoomAvailability(oldRoom);
        
        if (!oldRoom.getRoomId().equals(newRoom.getRoomId())) {
        	
            refreshRoomAvailability(newRoom);
        }
        
        log.info("Reservation updated successfully. ID: {}", id);
        
        return reservationMapper.toResponseDto(updatedReservation);
    }
    
    @Transactional
    @Override
    @CacheEvict(value = "availableRooms", allEntries = true)
    public void cancelReservation(Long id) {
    	
        log.info("Cancelling reservation with ID: {}", id);
        
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> 
        new ReservationNotFoundException("Reservation not found with ID: " + id));
        
        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
        	
            throw new IllegalArgumentException("Only confirmed reservations can be cancelled");
        }
        
        reservation.setStatus(ReservationStatus.CANCELLED);
        
        reservationRepository.save(reservation);
        
        Room room = reservation.getRoom();
        
        if (room != null) {
        	
        	refreshRoomAvailability(room);
        	
        	log.info("Room availability refreshed after cancellation: {}", room.getRoomId());
        }
        
        log.info("Reservation cancelled successfully. ID: {}", id);
    }
    
    @Transactional
    @Override
    @CacheEvict(value = "availableRooms", allEntries = true)
    public void checkOutReservation(Long id) {
    	
        log.info("Processing checkout for reservation ID: {}", id);
        
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> 
        new ReservationNotFoundException("Reservation not found with ID: " + id));
        
        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
        	
            throw new IllegalArgumentException("Only confirmed reservations can be checked out");
        }
        
        java.time.LocalDate today = java.time.LocalDate.now();
        
        if (today.isBefore(reservation.getCheckInDate()) || !today.isAfter(reservation.getCheckOutDate())) {
        	
            throw new IllegalArgumentException("Checkout is allowed only between check-in and check-out dates");
        }
        
        Room room = reservation.getRoom();
        
        reservation.setStatus(ReservationStatus.COMPLETED);
        
        reservationRepository.save(reservation);
        
        if (room != null) {
        	
            refreshRoomAvailability(room);
            
            log.info("Room availability refreshed after checkout: {}", room.getRoomId());
        }
        
        log.info("Checkout completed successfully. Reservation ID: {}", id);
    }
    
    private void validateRoomAvailability(Long roomId, java.time.LocalDate checkInDate, 
    		java.time.LocalDate checkOutDate, Long excludedReservationId) {
    	
        boolean overlapping = reservationRepository.findByRoomRoomId(roomId).stream().filter(r -> 
        r.getStatus() == ReservationStatus.CONFIRMED).filter(r -> 
        excludedReservationId == null || !r.getReservationId().equals(excludedReservationId)).anyMatch(r -> 
        r.getCheckInDate().isBefore(checkOutDate) && r.getCheckOutDate().isAfter(checkInDate));
        
        if (overlapping) {
        	
            throw new IllegalArgumentException("Room is already reserved for the selected dates.");
        }
    }
    
    private boolean hasConfirmedReservationForToday(Long roomId) {
    	
        java.time.LocalDate today = java.time.LocalDate.now();
        
        return reservationRepository.findByRoomRoomId(roomId).stream().anyMatch(r -> 
        r.getStatus() == ReservationStatus.CONFIRMED && !r.getCheckInDate().isAfter(today) && r.getCheckOutDate().isAfter(today));
    }
    
    private void refreshRoomAvailability(Room room) {
    	
        if (room == null) {
        	
            return;
        }
        
        boolean hasBookingToday = hasConfirmedReservationForToday(room.getRoomId());
        
        room.setAvailable(!hasBookingToday);
        
        roomRepository.save(room);
    }
}
