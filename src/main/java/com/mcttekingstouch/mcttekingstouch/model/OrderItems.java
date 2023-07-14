package com.mcttekingstouch.mcttekingstouch.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderItems(
        UUID burgerId,
        UUID orderId,
        String burgerName,
        int quantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
