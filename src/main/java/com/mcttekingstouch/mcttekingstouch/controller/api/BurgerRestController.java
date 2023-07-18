package com.mcttekingstouch.mcttekingstouch.controller.api;

import com.mcttekingstouch.mcttekingstouch.model.Burger;
import com.mcttekingstouch.mcttekingstouch.service.BurgerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class BurgerRestController {
    private final BurgerService burgerService;

    public BurgerRestController(BurgerService burgerService) {
        this.burgerService = burgerService;
    }

    @GetMapping("/burgers")
    public ResponseEntity<List<Burger>> getBurgersByIdOrName(@RequestParam(required = false) UUID id,
                                                             @RequestParam(required = false) String name) {
        if (id != null) {
            Burger burger = burgerService.findById(id);
            return ResponseEntity.ok(Collections.singletonList(burger));
        }
        if (name != null) {
            Burger burger = burgerService.findByName(name);
            return ResponseEntity.ok(Collections.singletonList(burger));
        }
        List<Burger> burgers = burgerService.getAllBurger();
        return ResponseEntity.ok(burgers);
    }

    @PostMapping("/burgers")
    public ResponseEntity<Void> createBurger(@RequestBody CreateBurgerRequest request) {
        burgerService.createBurger(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/burgers")
    public ResponseEntity<Void> updateBurger(@RequestBody UpdateBurgerRequest request) {
        burgerService.updateBurger(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/burgers")
    public ResponseEntity<Void> deleteAllBurger() {
        burgerService.deleteAllBurger();
        return ResponseEntity.ok().build();
    }

}
