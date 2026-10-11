package com.example.hotelreservation.service;

import java.util.List;

import com.example.hotelreservation.dto.PaymentDto;

public interface PaymentService {

    PaymentDto addPayment(PaymentDto dto);
    
    List<PaymentDto> getAllPayments();
    
    PaymentDto getPaymentById(Long id);
    
    PaymentDto getPaymentByReservation(Long reservationId);
}
