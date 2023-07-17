package com.mcttekingstouch.mcttekingstouch.repository;

import com.mcttekingstouch.mcttekingstouch.model.Email;
import com.mcttekingstouch.mcttekingstouch.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    int save(Order order);

    List<Order> findAll();

    Optional<Order> findById(UUID orderId);

    Optional<Order> findByEmail(Email email);

    int deleteAll();
}
