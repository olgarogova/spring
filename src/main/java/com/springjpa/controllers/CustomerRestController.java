package com.springjpa.controllers;

import com.springjpa.entity.Customer;
import com.springjpa.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/customers")
public class CustomerRestController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(@RequestParam(required = false) String customerName) {
        try{
            List<Customer> customers = new ArrayList<>();

            if (customerName == null){
                customers.addAll(customerRepository.findAll());
            } else {
                customers.addAll(customerRepository.findAllByCustomerName(customerName));
            }

            if (customers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") int customerId) {
        Optional<Customer> customerData = customerRepository.findById(customerId);

        return customerData.map(customer -> new ResponseEntity<>(customer, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        try {
            Customer newCustomer = customerRepository
                    .save(new Customer(customer.getCustomerName(), customer.getPhone()));
            return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") int customerId, @RequestBody Customer updatedCustomer) {
        Optional<Customer> customerData = customerRepository.findById(customerId);

        if (customerData.isPresent()) {
            Customer customer = customerData.get();
            customer.setCustomerName(updatedCustomer.getCustomerName());
            customer.setPhone(updatedCustomer.getPhone());
            return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("customerId") int customerId) {
        try {
            customerRepository.deleteById(customerId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
