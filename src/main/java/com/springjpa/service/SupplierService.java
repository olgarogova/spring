package com.springjpa.service;

import com.springjpa.entity.SupplierEntity;

import java.util.List;

public interface SupplierService {
    List<SupplierEntity> getAllSuppliers(String customerName);
    SupplierEntity getSupplierById(int supplierId);
    SupplierEntity createSupplier(SupplierEntity supplierEntity);
    SupplierEntity updateSupplier(int supplierId, SupplierEntity updatedSupplierEntity);
    void deleteSupplier(int supplierId);
}
