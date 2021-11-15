package com.springjpa.controllers;

import com.springjpa.entity.CustomerEntity;
import com.springjpa.exceptions.InternalServerErrorException;
import com.springjpa.exceptions.ResourceNotFoundException;
import com.springjpa.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerRestController {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerRestController(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public List<CustomerEntity> getAllCustomers(@RequestParam(required = false) String customerName){

        try {
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
        } catch (InternalServerErrorException e){
            throw new InternalServerErrorException("Could not get customers");
        }
    }

    @GetMapping("/{customerId}")
    public Optional<CustomerEntity> getCustomerById(@PathVariable("customerId") int customerId) {
        Optional<CustomerEntity> customerData = customerRepository.findById(customerId);
        if (customerData.isEmpty()){
            throw new ResourceNotFoundException("Customer not found");
        }
        return customerData;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerEntity createCustomer(@RequestBody CustomerEntity customerEntity) {
        try {
            return customerRepository
                    .save(new CustomerEntity(customerEntity.getCustomerName(), customerEntity.getPhone()));
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Could not create customer");
        }
    }

    @PutMapping("/{customerId}")
    public CustomerEntity updateCustomer(@PathVariable("customerId") int customerId, @RequestBody CustomerEntity updatedCustomerEntity) {
        Optional<CustomerEntity> customerData = customerRepository.findById(customerId);

        if (customerData.isPresent()) {
            CustomerEntity customerEntity = customerData.get();
            customerEntity.setCustomerName(updatedCustomerEntity.getCustomerName());
            customerEntity.setPhone(updatedCustomerEntity.getPhone());
            customerRepository.save(customerEntity);
            return customerEntity;
        } else {
            throw new ResourceNotFoundException("Customer not found");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable("customerId") int customerId) {
        try {
            customerRepository.deleteById(customerId);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not delete customer");
        }
    }
}
