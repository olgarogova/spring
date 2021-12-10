package com.springjpa.controllers;

import com.springjpa.entity.CustomerEntity;
import com.springjpa.service.impl.CustomerServiceImpl;
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

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerRestController {

    private final CustomerServiceImpl customerService;

    @Autowired
    public CustomerRestController(CustomerServiceImpl customerService){
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerEntity> getAllCustomers(@RequestParam(required = false) String customerName){
        return customerService.getAllCustomers(customerName);
    }

    @GetMapping("/{customerId}")
    public CustomerEntity getCustomerById(@PathVariable("customerId") int customerId) {
        return customerService.getCustomerById(customerId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerEntity addCustomer(@RequestBody CustomerEntity customerEntity) {
        return customerService.saveCustomer(customerEntity);
    }

    @PutMapping("/{customerId}")
    public CustomerEntity updateCustomer(@PathVariable("customerId") int customerId, @RequestBody CustomerEntity updatedCustomerEntity) {
        return customerService.updateCustomer(customerId, updatedCustomerEntity);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable("customerId") int customerId) {
        customerService.deleteCustomer(customerId);
    }
}
