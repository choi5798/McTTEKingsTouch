package com.mcttekingstouch.mcttekingstouch.service;

import com.mcttekingstouch.mcttekingstouch.controller.api.CreateOrderRequest;
import com.mcttekingstouch.mcttekingstouch.controller.api.UpdateOrderRequest;
import com.mcttekingstouch.mcttekingstouch.model.Email;
import com.mcttekingstouch.mcttekingstouch.model.Order;
import com.mcttekingstouch.mcttekingstouch.model.OrderStatus;
import com.mcttekingstouch.mcttekingstouch.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findByEmail(Email email) {
        return orderRepository.findByEmail(email);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public void createOrder(CreateOrderRequest request) {
        Order order = new Order(
                UUID.randomUUID(),
                request.email(),
                request.address(),
                request.postcode(),
                OrderStatus.ACCEPTED,
                request.orderItems(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        orderRepository.save(order);
    }

    public void updateOrder(UpdateOrderRequest request) {
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다"));

        if (request.address() != null) {
            order.setAddress(request.address());
        }
        if (request.postcode() != null) {
            order.setPostcode(request.postcode());
        }
        if (request.status() != null) {
            order.setStatus(request.status());
        }
        if (request.orderItems() != null) {
            order.setOrderItems(request.orderItems());
        }
        order.setUpdatedAt(LocalDateTime.now());


        orderRepository.save(order);
    }
}
