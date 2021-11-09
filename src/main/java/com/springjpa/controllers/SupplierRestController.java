package com.springjpa.controllers;

import com.springjpa.entity.Supplier;
import com.springjpa.repository.SupplierRepository;
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
@RequestMapping("/suppliers")
public class SupplierRestController {
    @Autowired
    SupplierRepository supplierRepository;

    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers(@RequestParam(required = false) String supplierName) {
        try{
            List<Supplier> suppliers = new ArrayList<>();

            if (supplierName == null){
                suppliers.addAll(supplierRepository.findAll());
            } else {
                suppliers.addAll(supplierRepository.findAllBySupplierName(supplierName));
            }

            if (suppliers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(suppliers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{supplierId}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable("supplierId") int supplierId) {
        Optional<Supplier> supplierData = supplierRepository.findById(supplierId);

        return supplierData.map(supplier -> new ResponseEntity<>(supplier, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) {
        try {
            Supplier newSupplier = supplierRepository
                    .save(new Supplier(supplier.getSupplierName(), supplier.getPhone()));
            return new ResponseEntity<>(newSupplier, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{supplierId}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable("supplierId") int supplierId, @RequestBody Supplier updatedSupplier) {
        Optional<Supplier> supplierData = supplierRepository.findById(supplierId);

        if (supplierData.isPresent()) {
            Supplier supplier = supplierData.get();
            supplier.setSupplierName(updatedSupplier.getSupplierName());
            supplier.setPhone(updatedSupplier.getPhone());
            return new ResponseEntity<>(supplierRepository.save(supplier), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{supplierId}")
    public ResponseEntity<HttpStatus> deleteSupplier(@PathVariable("supplierId") int supplierId) {
        try {
            supplierRepository.deleteById(supplierId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
