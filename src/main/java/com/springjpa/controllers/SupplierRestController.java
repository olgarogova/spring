package com.springjpa.controllers;

import com.springjpa.entity.SupplierEntity;
import com.springjpa.service.impl.SupplierServiceImpl;
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
@RequestMapping("/suppliers")
public class SupplierRestController {

    private final SupplierServiceImpl supplierService;

    @Autowired
    public SupplierRestController(SupplierServiceImpl supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public List<SupplierEntity> getAllSuppliers(@RequestParam(required = false) String supplierName){
        return supplierService.getAllSuppliers(supplierName);
    }

    @GetMapping("/{supplierId}")
    public SupplierEntity getSupplierById(@PathVariable("supplierId") int supplierId) {
        return supplierService.getSupplierById(supplierId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SupplierEntity createSupplier(@RequestBody SupplierEntity supplierEntity) {
        return supplierService.createSupplier(supplierEntity);
    }

    @PutMapping("/{supplierId}")
    public SupplierEntity updateSupplier(@PathVariable("supplierId") int supplierId, @RequestBody SupplierEntity updatedSupplierEntity) {
        return supplierService.updateSupplier(supplierId, updatedSupplierEntity);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{supplierId}")
    public void deleteSupplier(@PathVariable("supplierId") int supplierId) {
        supplierService.deleteSupplier(supplierId);
    }
}
