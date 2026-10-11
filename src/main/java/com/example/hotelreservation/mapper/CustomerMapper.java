package com.example.hotelreservation.mapper;

import org.springframework.stereotype.Component;

import com.example.hotelreservation.dto.CustomerDto;
import com.example.hotelreservation.entity.Customer;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerDto dto) {
    	
        Customer customer = new Customer();
        
        customer.setCustomerId(dto.getCustomerId());
        customer.setCustomerName(dto.getCustomerName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        
        return customer;
    }
    
    public CustomerDto toResponseDto(Customer customer) {
    	
        CustomerDto dto = new CustomerDto();
        
        dto.setCustomerId(customer.getCustomerId());
        dto.setCustomerName(customer.getCustomerName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        
        return dto;
    }
    
    public void updateEntity(CustomerDto dto, Customer customer) {
    	
        customer.setCustomerName(dto.getCustomerName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
    }
}
