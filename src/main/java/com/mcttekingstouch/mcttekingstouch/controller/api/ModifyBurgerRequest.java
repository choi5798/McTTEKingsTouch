package com.mcttekingstouch.mcttekingstouch.controller.api;

import com.mcttekingstouch.mcttekingstouch.model.BurgerCompany;
import com.mcttekingstouch.mcttekingstouch.model.BurgerType;

import java.time.LocalDateTime;

public record ModifyBurgerRequest(String burgerName,
                                  long price,
                                  BurgerType burgerType,
                                  BurgerCompany burgerCompany,
                                  LocalDateTime createdAt) {
}
