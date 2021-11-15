package com.springjpa.controllers;

import com.springjpa.entity.SupplierEntity;
import com.springjpa.exceptions.InternalServerErrorException;
import com.springjpa.exceptions.ResourceNotFoundException;
import com.springjpa.repository.SupplierRepository;
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
@RequestMapping("/suppliers")
public class SupplierRestController {

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierRestController(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @GetMapping
    public List<SupplierEntity> getAllSuppliers(@RequestParam(required = false) String supplierName){

        try {
            List<SupplierEntity> supplierEntities = new ArrayList<>();

            if (supplierName == null){
                supplierEntities.addAll(supplierRepository.findAll());
            } else {
                supplierEntities.addAll(supplierRepository.findAllBySupplierName(supplierName));
            }

            if (supplierEntities.isEmpty()) {
                throw new ResourceNotFoundException("Supplier not found");
            }
            return supplierEntities;
        } catch (InternalServerErrorException e){
            throw new InternalServerErrorException("Could not get suppliers");
        }
    }

    @GetMapping("/{supplierId}")
    public Optional<SupplierEntity> getSupplierById(@PathVariable("supplierId") int supplierId) {
        Optional<SupplierEntity> supplierData = supplierRepository.findById(supplierId);
        if (supplierData.isEmpty()){
            throw new ResourceNotFoundException("Supplier not found");
        }
        return supplierData;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SupplierEntity createSupplier(@RequestBody SupplierEntity supplierEntity) {
        try {
            return supplierRepository
                    .save(new SupplierEntity(supplierEntity.getSupplierName(), supplierEntity.getPhone()));
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Could not create supplier");
        }
    }

    @PutMapping("/{supplierId}")
    public SupplierEntity updateSupplier(@PathVariable("supplierId") int supplierId, @RequestBody SupplierEntity updatedSupplierEntity) {
        Optional<SupplierEntity> supplierData = supplierRepository.findById(supplierId);

        if (supplierData.isPresent()) {
            SupplierEntity supplierEntity = supplierData.get();
            supplierEntity.setSupplierName(updatedSupplierEntity.getSupplierName());
            supplierEntity.setPhone(updatedSupplierEntity.getPhone());
            supplierRepository.save(supplierEntity);
            return supplierEntity;
        } else {
            throw new ResourceNotFoundException("Supplier not found");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{supplierId}")
    public void deleteSupplier(@PathVariable("supplierId") int supplierId) {
        try {
            supplierRepository.deleteById(supplierId);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not delete supplier");
        }
    }
}
