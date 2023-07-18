package com.mcttekingstouch.mcttekingstouch.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Burger {
    private final UUID burgerId;
    private String burgerName;
    private Long price;
    private BurgerType burgerType;
    private BurgerCompany burgerCompany;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Burger(
            UUID burgerId,
            String burgerName,
            long price,
            BurgerType burgerType,
            BurgerCompany burgerCompany,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
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

    public Long getPrice() {
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

    public void setBurgerName(String burgerName) {
        this.burgerName = burgerName;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setBurgerType(BurgerType burgerType) {
        this.burgerType = burgerType;
    }

    public void setBurgerCompany(BurgerCompany burgerCompany) {
        this.burgerCompany = burgerCompany;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
