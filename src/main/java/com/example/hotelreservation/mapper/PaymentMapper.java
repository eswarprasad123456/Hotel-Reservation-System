package com.example.hotelreservation.mapper;

import org.springframework.stereotype.Component;

import com.example.hotelreservation.dto.PaymentDto;
import com.example.hotelreservation.entity.Payment;

@Component
public class PaymentMapper {

    public PaymentDto toResponseDto(Payment payment) {
    	
        PaymentDto dto = new PaymentDto();
        
        dto.setPaymentId(payment.getPaymentId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setPaymentStatus(payment.getPaymentStatus());
        dto.setPaymentDate(payment.getPaymentDate());
        
        if (payment.getReservation() != null) {
        	dto.setReservationId(payment.getReservation().getReservationId());
        }
        
        return dto;
    }
}
