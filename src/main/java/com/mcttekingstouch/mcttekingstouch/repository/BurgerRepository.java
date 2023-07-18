package com.mcttekingstouch.mcttekingstouch.repository;

import com.mcttekingstouch.mcttekingstouch.model.Burger;
import com.mcttekingstouch.mcttekingstouch.model.BurgerCompany;
import com.mcttekingstouch.mcttekingstouch.model.BurgerType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BurgerRepository {
    int save(Burger burger);

    List<Burger> findAll();

    Optional<Burger> findById(UUID burgerId);

    Optional<Burger> findByName(String burgerName);

    List<Burger> findByBurgerType(BurgerType burgerType);

    List<Burger> findByBurgerCompany(BurgerCompany burgerCompany);

    int count();

    int deleteAll();

    int deleteByName(String name);
}
