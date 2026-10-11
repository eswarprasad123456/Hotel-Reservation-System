package com.example.hotelreservation.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.hotelreservation.dto.PaymentDto;
import com.example.hotelreservation.entity.Payment;
import com.example.hotelreservation.entity.Reservation;
import com.example.hotelreservation.exception.DuplicateResourceException;
import com.example.hotelreservation.exception.PaymentNotFoundException;
import com.example.hotelreservation.exception.ReservationNotFoundException;
import com.example.hotelreservation.mapper.PaymentMapper;
import com.example.hotelreservation.repository.PaymentRepository;
import com.example.hotelreservation.repository.ReservationRepository;
import com.example.hotelreservation.service.PaymentService;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    
    private final ReservationRepository reservationRepository;
    
    private final PaymentMapper paymentMapper;
    
    @Transactional
    @Override
    public PaymentDto addPayment(PaymentDto dto) {
    	
        Reservation reservation = reservationRepository.findById(dto.getReservationId()).orElseThrow(() -> 
        new ReservationNotFoundException("Reservation not found with ID: " + dto.getReservationId()));
        
        if (paymentRepository.existsByReservationReservationId(dto.getReservationId())) {
        	
            throw new DuplicateResourceException("Payment already exists for reservation ID: " + dto.getReservationId());
        }
        
        Payment payment = new Payment();
        
        payment.setAmount(dto.getAmount());
        
        payment.setPaymentMethod(dto.getPaymentMethod());
        
        payment.setPaymentStatus(dto.getPaymentStatus());
        
        payment.setPaymentDate(dto.getPaymentDate() != null ? dto.getPaymentDate(): LocalDateTime.now());
        
        payment.setReservation(reservation);
        
        Payment savedPayment = paymentRepository.save(payment);
        
        log.info("Payment created successfully. Payment ID: {}", savedPayment.getPaymentId());
        
        return paymentMapper.toResponseDto(savedPayment);
    }
    
    @Override
    public List<PaymentDto> getAllPayments() {
    	
        return paymentRepository.findAll().stream().map(paymentMapper::toResponseDto).toList();
    }
    
    @Override
    public PaymentDto getPaymentById(Long id) {
    	
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> 
        new PaymentNotFoundException("Payment not found with ID: " + id));
        
        return paymentMapper.toResponseDto(payment);
    }
    
    @Override
    public PaymentDto getPaymentByReservation(Long reservationId) {
    	
        Payment payment = paymentRepository.findByReservationReservationId(reservationId).orElseThrow(() -> 
        new PaymentNotFoundException("Payment not found for reservation ID: " + reservationId));
        
        return paymentMapper.toResponseDto(payment);
    }
}
