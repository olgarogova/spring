package com.springjpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "order")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", updatable = false, nullable = false)
    private int orderId;

    @Column(name = "order_number")
    private String orderNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customerEntity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @ManyToMany
    @JoinTable(name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @JsonIgnoreProperties("orders")
    Set<ProductEntity> orderProducts;

    public OrderEntity() {
    }

    public OrderEntity(String orderNumber, CustomerEntity customerEntity, Date orderDate, BigDecimal totalAmount, Set<ProductEntity> orderProductEntities) {
        this.orderNumber = orderNumber;
        this.customerEntity = customerEntity;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderProducts = orderProductEntities;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public CustomerEntity getCustomer() {
        return customerEntity;
    }

    public void setCustomer(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Set<ProductEntity> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<ProductEntity> orderProductEntities) {
        this.orderProducts = orderProductEntities;
    }

    @Override
    public String toString() {
        return "Order {" +
                "orderId = " + orderId +
                ", orderNumber = '" + orderNumber + '\'' +
                ", customer = " + customerEntity +
                ", orderDate = " + orderDate +
                ", totalAmount = " + totalAmount +
                '}';
    }
}

