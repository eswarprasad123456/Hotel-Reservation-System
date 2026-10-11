package com.example.hotelreservation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelreservation.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByReservationReservationId(Long reservationId);
    
    boolean existsByReservationReservationId(Long reservationId);
}
