package com.springjpa.repository;

import com.springjpa.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    List<Supplier> findAllBySupplierName(String supplierName);
}
