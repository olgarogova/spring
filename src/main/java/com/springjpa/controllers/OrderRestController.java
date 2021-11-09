package com.springjpa.controllers;

import com.springjpa.entity.Order;
import com.springjpa.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/orders")
public class OrderRestController {
    @Autowired
    OrderRepository orderRepository;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam(required = false) String orderNumber) {
        try{
            List<Order> orders = new ArrayList<>();

            if (orderNumber == null){
                orders.addAll(orderRepository.findAll());
            } else {
                orders.addAll(orderRepository.findAllByOrderNumber(orderNumber));
            }

            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable("orderId") int orderId) {
        Optional<Order> orderData = orderRepository.findById(orderId);

        return orderData.map(order -> new ResponseEntity<>(order, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        try {
            Order newOrder = orderRepository
                    .save(new Order(order.getOrderNumber(), order.getCustomer(), order.getOrderDate(), order.getTotalAmount(), order.getOrderProducts()));
            return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable("orderId") int orderId, @RequestBody Order updatedOrder) {
        Optional<Order> orderData = orderRepository.findById(orderId);

        if (orderData.isPresent()) {
            Order order = orderData.get();
            order.setOrderNumber(updatedOrder.getOrderNumber());
            order.setCustomer(updatedOrder.getCustomer());
            order.setOrderDate(updatedOrder.getOrderDate());
            order.setTotalAmount(updatedOrder.getTotalAmount());
            order.setOrderProducts(updatedOrder.getOrderProducts());
            return new ResponseEntity<>(orderRepository.save(order), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("orderId") int orderId) {
        try {
            orderRepository.deleteById(orderId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
