package com.example.hotelreservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelreservation.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByEmailIgnoreCase(String email);
}