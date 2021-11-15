package com.springjpa.controllers;

import com.springjpa.entity.OrderEntity;
import com.springjpa.exceptions.InternalServerErrorException;
import com.springjpa.exceptions.ResourceNotFoundException;
import com.springjpa.repository.OrderRepository;
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
@RequestMapping("/orders")
public class OrderRestController {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderRestController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public List<OrderEntity> getAllOrders(@RequestParam(required = false) String orderNumber){

        try {
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
        } catch (InternalServerErrorException e){
            throw new InternalServerErrorException("Could not get orders");
        }
    }

    @GetMapping("/{orderId}")
    public Optional<OrderEntity> getOrderById(@PathVariable("orderId") int orderId) {
        Optional<OrderEntity> orderData = orderRepository.findById(orderId);
        if (orderData.isEmpty()){
            throw new ResourceNotFoundException("Order not found");
        }
        return orderData;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderEntity createOrder(@RequestBody OrderEntity orderEntity) {
        try {
            return orderRepository
                    .save(new OrderEntity(orderEntity.getOrderNumber(), orderEntity.getCustomer(), orderEntity.getOrderDate(), orderEntity.getTotalAmount(), orderEntity.getOrderProducts()));
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Could not create order");
        }
    }

    @PutMapping("/{orderId}")
    public OrderEntity updateOrder(@PathVariable("orderId") int orderId, @RequestBody OrderEntity updatedOrderEntity) {
        Optional<OrderEntity> orderData = orderRepository.findById(orderId);

        if (orderData.isPresent()) {
            OrderEntity orderEntity = orderData.get();
            orderEntity.setOrderNumber(updatedOrderEntity.getOrderNumber());
            orderEntity.setCustomer(updatedOrderEntity.getCustomer());
            orderEntity.setOrderDate(updatedOrderEntity.getOrderDate());
            orderEntity.setTotalAmount(updatedOrderEntity.getTotalAmount());
            orderEntity.setOrderProducts(updatedOrderEntity.getOrderProducts());
            return orderEntity;
        } else {
            throw new ResourceNotFoundException("Order not found");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable("orderId") int orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not delete order");
        }
    }
}