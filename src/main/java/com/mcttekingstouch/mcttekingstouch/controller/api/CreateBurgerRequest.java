package com.mcttekingstouch.mcttekingstouch.controller.api;

import com.mcttekingstouch.mcttekingstouch.model.BurgerCompany;
import com.mcttekingstouch.mcttekingstouch.model.BurgerType;

public record CreateBurgerRequest(String burgerName,
                                  long price,
                                  BurgerType burgerType,
                                  BurgerCompany burgerCompany){
}
