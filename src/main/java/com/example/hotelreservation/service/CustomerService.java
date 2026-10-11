package com.example.hotelreservation.service;

import java.util.List;

import com.example.hotelreservation.dto.CustomerDto;

public interface CustomerService {

    CustomerDto addCustomer(CustomerDto dto);
    
    List<CustomerDto> getAllCustomers();
    
    CustomerDto getCustomerById(Long id);
    
    CustomerDto updateCustomer(Long id, CustomerDto dto);
    
    void deleteCustomer(Long id);
}
