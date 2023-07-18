package com.mcttekingstouch.mcttekingstouch.controller.api;

import com.mcttekingstouch.mcttekingstouch.model.Email;
import com.mcttekingstouch.mcttekingstouch.model.Order;
import com.mcttekingstouch.mcttekingstouch.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderRestController {
    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrdersByEmail(@RequestParam(required = false) Email email) {
        if (email != null) {
            List<Order> orders = orderService.findByEmail(email);
            return ResponseEntity.ok(orders);
        }
        List<Order> orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/orders")
    public ResponseEntity<Void> createdOrder(@RequestBody CreateOrderRequest request) {
        orderService.createOrder(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/orders")
    public ResponseEntity<Void> updateOrder(@RequestBody UpdateOrderRequest request) {
        orderService.updateOrder(request);
        return ResponseEntity.ok().build();
    }
}
