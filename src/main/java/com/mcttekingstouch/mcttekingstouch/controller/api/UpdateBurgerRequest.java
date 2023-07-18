package com.mcttekingstouch.mcttekingstouch.controller.api;

import com.mcttekingstouch.mcttekingstouch.model.BurgerCompany;
import com.mcttekingstouch.mcttekingstouch.model.BurgerType;

import java.util.UUID;

public record UpdateBurgerRequest(
        UUID burgerId,
        String burgerName,
        Long price,
        BurgerType burgerType,
        BurgerCompany burgerCompany
) {
}
