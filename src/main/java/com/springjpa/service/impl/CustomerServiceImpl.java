package com.springjpa.service.impl;

import com.springjpa.entity.CustomerEntity;
import com.springjpa.exceptions.ResourceNotFoundException;
import com.springjpa.repository.CustomerRepository;
import com.springjpa.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerEntity> getAllCustomers(String customerName){
        List<CustomerEntity> customerEntities = new ArrayList<>();
        if (customerName == null){
            customerEntities.addAll(customerRepository.findAll());
        } else {
            customerEntities.addAll(customerRepository.findAllByCustomerName(customerName));
        }
        if (customerEntities.isEmpty()) {
            throw new ResourceNotFoundException("Customer not found");
        }

        return customerEntities;
    }

    @Override
    public CustomerEntity getCustomerById(int customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }

    @Override
    public CustomerEntity saveCustomer(CustomerEntity customerEntity) {
        return customerRepository
                    .save(new CustomerEntity(customerEntity.getCustomerName(), customerEntity.getPhone()));
    }

    @Override
    public CustomerEntity updateCustomer(int customerId, CustomerEntity updatedCustomerEntity) {
        CustomerEntity customerData = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        customerData.setCustomerName(updatedCustomerEntity.getCustomerName());
        customerData.setPhone(updatedCustomerEntity.getPhone());
        customerRepository.save(customerData);
        return customerData;
    }

    @Override
    public void deleteCustomer(int customerId) {
        try {
            customerRepository.deleteById(customerId);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Customer with id " + customerId + " does not exist");
        }
    }
}
