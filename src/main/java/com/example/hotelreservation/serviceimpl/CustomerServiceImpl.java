package com.example.hotelreservation.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.example.hotelreservation.dto.CustomerDto;
import com.example.hotelreservation.entity.Customer;
import com.example.hotelreservation.exception.CustomerNotFoundException;
import com.example.hotelreservation.exception.DuplicateResourceException;
import com.example.hotelreservation.mapper.CustomerMapper;
import com.example.hotelreservation.repository.CustomerRepository;
import com.example.hotelreservation.service.CustomerService;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    
    private final CustomerMapper customerMapper;
    
    @Override
    public CustomerDto addCustomer(CustomerDto dto) {
    	
        if (customerRepository.existsByEmailIgnoreCase(dto.getEmail())) {
        	
            throw new DuplicateResourceException("Email already exists: " + dto.getEmail());
        }
        
        Customer customer = customerMapper.toEntity(dto);
        
        return customerMapper.toResponseDto(customerRepository.save(customer));
    }
    
    @Override
    public List<CustomerDto> getAllCustomers() {
    	
        return customerRepository.findAll().stream().map(customerMapper::toResponseDto).toList();
    }
    
    @Override
    public CustomerDto getCustomerById(Long id) {
    	
        Customer customer = customerRepository.findById(id).orElseThrow(() -> 
        new CustomerNotFoundException("Customer not found with ID: " + id));
        
        return customerMapper.toResponseDto(customer);
    }
    
    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto dto) {
    	
        Customer customer = customerRepository.findById(id).orElseThrow(() -> 
        new CustomerNotFoundException("Customer not found with ID: " + id));
        
        if (customer.getEmail() != null && dto.getEmail() != null && customerRepository.existsByEmailIgnoreCase(dto.getEmail()) 
        		&& !customer.getEmail().equalsIgnoreCase(dto.getEmail())) {
        	
            throw new DuplicateResourceException("Email already exists: " + dto.getEmail());
        }
        
        customerMapper.updateEntity(dto, customer);
        
        return customerMapper.toResponseDto(customerRepository.save(customer));
    }
    
    @Override
    public void deleteCustomer(Long id) {
    	
        Customer customer = customerRepository.findById(id).orElseThrow(() -> 
        new CustomerNotFoundException("Customer not found with ID: " + id));
        
        customerRepository.delete(customer);
    }
}
