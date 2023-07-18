package com.mcttekingstouch.mcttekingstouch.service;

import com.mcttekingstouch.mcttekingstouch.controller.api.CreateBurgerRequest;
import com.mcttekingstouch.mcttekingstouch.controller.api.UpdateBurgerRequest;
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

    public void updateBurger(UpdateBurgerRequest request) {
        Burger burger = burgerRepository
                .findById(request.burgerId())
                .orElseThrow(() -> new IllegalStateException("해당 햄버거가 존재하지 않습니다"));

        if (request.burgerName() != null) {
            burger.setBurgerName(request.burgerName());
        }
        if (request.price() != null) {
            burger.setPrice(request.price());
        }
        if (request.burgerType() != null) {
            burger.setBurgerType(request.burgerType());
        }
        if (request.burgerCompany() != null) {
            burger.setBurgerCompany(request.burgerCompany());
        }
        burger.setUpdatedAt(LocalDateTime.now());

        int updated = burgerRepository.save(burger);
        if (updated != SUCCESS) {
            throw new IllegalArgumentException("햄버거 갱신에 실패했습니다");
        }
    }

    public void deleteAllBurger() {
        burgerRepository.deleteAll();
    }
}
