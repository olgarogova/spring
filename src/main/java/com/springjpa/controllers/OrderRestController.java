package com.springjpa.controllers;

import com.springjpa.entity.OrderEntity;
import com.springjpa.service.impl.OrderServiceImpl;
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
@RequestMapping("/orders")
public class OrderRestController {

    private final OrderServiceImpl orderService;

    @Autowired
    public OrderRestController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderEntity> getAllOrders(@RequestParam(required = false) String orderNumber){
        return orderService.getAllOrders(orderNumber);
    }

    @GetMapping("/{orderId}")
    public OrderEntity getOrderById(@PathVariable("orderId") int orderId) {
        return orderService.getOrderById(orderId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderEntity createOrder(@RequestBody OrderEntity orderEntity) {
        return orderService.createOrder(orderEntity);
    }

    @PutMapping("/{orderId}")
    public OrderEntity updateOrder(@PathVariable("orderId") int orderId, @RequestBody OrderEntity updatedOrderEntity) {
        return orderService.updateOrder(orderId, updatedOrderEntity);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable("orderId") int orderId) {
        orderService.deleteOrder(orderId);
    }
}