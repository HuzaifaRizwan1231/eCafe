package com.SDA.eCafe.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SDA.eCafe.model.Orders;
import com.SDA.eCafe.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public Orders getOrderById(int id) {
        Optional<Orders> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }
}
