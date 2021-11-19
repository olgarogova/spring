package com.springjpa.service.impl;

import com.springjpa.entity.OrderEntity;
import com.springjpa.exceptions.ResourceNotFoundException;
import com.springjpa.repository.OrderRepository;
import com.springjpa.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderEntity> getAllOrders(String orderNumber){
        List<OrderEntity> orderEntities = new ArrayList<>();
        if (orderNumber == null){
            orderEntities.addAll(orderRepository.findAll());
        } else {
            orderEntities.addAll(orderRepository.findAllByOrderNumber(orderNumber));
        }
        if (orderEntities.isEmpty()) {
            throw new ResourceNotFoundException("Order not found");
        }
        return orderEntities;
    }

    @Override
    public OrderEntity getOrderById(int orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public OrderEntity createOrder(OrderEntity orderEntity) {
        return orderRepository
                    .save(new OrderEntity(orderEntity.getOrderNumber(), orderEntity.getCustomer(), orderEntity.getOrderDate(), orderEntity.getTotalAmount(), orderEntity.getOrderProducts()));
    }

    @Override
    public OrderEntity updateOrder(int orderId, OrderEntity updatedOrderEntity) {
        OrderEntity orderData = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        orderData.setOrderNumber(updatedOrderEntity.getOrderNumber());
        orderData.setCustomer(updatedOrderEntity.getCustomer());
        orderData.setOrderDate(updatedOrderEntity.getOrderDate());
        orderData.setTotalAmount(updatedOrderEntity.getTotalAmount());
        orderData.setOrderProducts(updatedOrderEntity.getOrderProducts());
        orderRepository.save(orderData);
        return orderData;
    }

    @Override
    public void deleteOrder(int orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Order with id " + orderId + " does not exist");
        }
    }
}