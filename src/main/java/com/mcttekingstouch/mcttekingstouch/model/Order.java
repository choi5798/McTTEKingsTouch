package com.mcttekingstouch.mcttekingstouch.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record Order(
        UUID orderId,
        Email email,
        String address,
        String postcode,
        OrderStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
