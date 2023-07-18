package com.mcttekingstouch.mcttekingstouch.controller.api;

import com.mcttekingstouch.mcttekingstouch.model.Email;
import com.mcttekingstouch.mcttekingstouch.model.OrderItems;
import com.mcttekingstouch.mcttekingstouch.model.OrderStatus;

import java.util.List;

public record CreateOrderRequest(
        Email email,
        String address,
        String postcode,
        OrderStatus status,
        List<OrderItems> orderItems
) {
}
