package com.springjpa.repository;

import com.springjpa.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findAllByCustomerName(String customerName);
}
