package com.mcttekingstouch.mcttekingstouch.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Burger {
    private final UUID burgerId;
    private final String burgerName;
    private long price;
    private final BurgerType burgerType;
    private final BurgerCompany burgerCompany;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Burger(UUID burgerId, String burgerName, long price, BurgerType burgerType, BurgerCompany burgerCompany, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.burgerId = burgerId;
        this.burgerName = burgerName;
        this.price = price;
        this.burgerType = burgerType;
        this.burgerCompany = burgerCompany;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getBurgerId() {
        return burgerId;
    }

    public String getBurgerName() {
        return burgerName;
    }

    public long getPrice() {
        return price;
    }

    public BurgerType getBurgerType() {
        return burgerType;
    }

    public BurgerCompany getBurgerCompany() {
        return burgerCompany;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
