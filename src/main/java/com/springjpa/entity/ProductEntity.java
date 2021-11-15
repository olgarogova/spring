package com.springjpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", updatable = false, nullable = false)
    private int productId;

    @Column(name = "product_name")
    private String productName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplierEntity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "is_discontinued", columnDefinition="boolean default true")
    private boolean isDiscontinued;

    @ManyToMany (mappedBy = "orderProducts")
    @JsonIgnoreProperties("orderProducts")
    Set<OrderEntity> orders;

    public ProductEntity() {
    }

    public ProductEntity(String productName, SupplierEntity supplierEntity, BigDecimal unitPrice, boolean isDiscontinued) {
        this.productName = productName;
        this.supplierEntity = supplierEntity;
        this.unitPrice = unitPrice;
        this.isDiscontinued = isDiscontinued;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public SupplierEntity getSupplier() {
        return supplierEntity;
    }

    public void setSupplier(SupplierEntity supplierEntity) {
        this.supplierEntity = supplierEntity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public boolean isDiscontinued() {
        return isDiscontinued;
    }

    public void setDiscontinued(boolean discontinued) {
        isDiscontinued = discontinued;
    }

    public Set<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderEntity> orderEntities) {
        this.orders = orderEntities;
    }

    @Override
    public String toString() {
        return "Product {" +
                "productId = " + productId +
                ", productName = '" + productName + '\'' +
                ", supplier = " + supplierEntity +
                ", unitPrice = " + unitPrice +
                ", isDiscontinued = " + isDiscontinued +
                '}';
    }
}
