package com.mcttekingstouch.mcttekingstouch.service;

import com.mcttekingstouch.mcttekingstouch.controller.api.CreateBurgerRequest;
import com.mcttekingstouch.mcttekingstouch.controller.api.ModifyBurgerRequest;
import com.mcttekingstouch.mcttekingstouch.model.Burger;
import com.mcttekingstouch.mcttekingstouch.repository.BurgerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BurgerService {
    private final int SUCCESS = 1;
    private final BurgerRepository burgerRepository;

    public BurgerService(BurgerRepository burgerRepository) {
        this.burgerRepository = burgerRepository;
    }

    public Burger findById(UUID burgerId) {
        Optional<Burger> burger = burgerRepository.findById(burgerId);
        return burger.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 햄버거 ID입니다"));
    }

    public List<Burger> getAllBurger() {
        return burgerRepository.findAll();
    }

    public Burger findByName(String burgerName) {
        Optional<Burger> burger = burgerRepository.findByName(burgerName);
        return burger.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 햄버거 이름입니다"));
    }

    public void createBurger(CreateBurgerRequest request) {
        Burger burger = new Burger(
                UUID.randomUUID(),
                request.burgerName(),
                request.price(),
                request.burgerType(),
                request.burgerCompany(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        int saved = burgerRepository.save(burger);
        if (saved != SUCCESS) {
            throw new IllegalArgumentException("햄버거 저장에 실패했습니다");
        }

    }

    public void modifyBurger(ModifyBurgerRequest request) {
        Burger burger = burgerRepository
                .findByName(request.burgerName())
                .orElseThrow(() -> new IllegalStateException("해당 햄버거가 존재하지 않습니다"));

        Burger newBurger = new Burger(
                burger.burgerId(),
                request.burgerName(),
                request.price(),
                request.burgerType(),
                request.burgerCompany(),
                request.createdAt(),
                LocalDateTime.now()
        );
        int updated = burgerRepository.save(newBurger);
        if (updated != SUCCESS) {
            throw new IllegalArgumentException("햄버거 갱신에 실패했습니다");
        }
    }

    public void deleteAllBurger() {
        burgerRepository.deleteAll();
    }
}
