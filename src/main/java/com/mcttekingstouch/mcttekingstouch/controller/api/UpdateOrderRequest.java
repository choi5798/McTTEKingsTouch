package com.mcttekingstouch.mcttekingstouch.controller.api;

import com.mcttekingstouch.mcttekingstouch.model.Email;
import com.mcttekingstouch.mcttekingstouch.model.OrderItems;
import com.mcttekingstouch.mcttekingstouch.model.OrderStatus;

import java.util.List;
import java.util.UUID;

public record UpdateOrderRequest(
        UUID orderId,
        Email email,
        String address,
        String postcode,
        OrderStatus status,
        List<OrderItems> orderItems
) {
}
