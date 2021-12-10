package com.springjpa.service;

import com.springjpa.entity.CustomerEntity;

import java.util.List;

public interface CustomerService {
    List<CustomerEntity> getAllCustomers(String customerName);
    CustomerEntity getCustomerById(int customerId);
    CustomerEntity saveCustomer(CustomerEntity customerEntity);
    CustomerEntity updateCustomer(int customerId, CustomerEntity updatedCustomerEntity);
    void deleteCustomer(int customerId);
}
