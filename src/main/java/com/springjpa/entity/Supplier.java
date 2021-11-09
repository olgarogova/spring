package com.springjpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "supplier")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id", updatable = false, nullable = false)
    private int supplierId;

    @Column(name = "company_name", nullable = false)
    private String supplierName;

    @Column(name = "phone")
    private String phone;

    public Supplier() {
    }

    public Supplier(String supplierName, String phone) {
        this.supplierName = supplierName;
        this.phone = phone;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int customerId) {
        this.supplierId = customerId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String customerName) {
        this.supplierName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Supplier {" +
                "supplierId = " + supplierId +
                ", supplierName = '" + supplierName + '\'' +
                ", phone = '" + phone + '\'' +
                '}';
    }
}
