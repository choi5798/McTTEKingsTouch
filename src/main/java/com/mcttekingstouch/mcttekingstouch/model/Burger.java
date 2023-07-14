package com.mcttekingstouch.mcttekingstouch.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record Burger(
        UUID burgerId,
        String burgerName,
        long price,
        BurgerType burgerType,
        BurgerCompany burgerCompany,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
