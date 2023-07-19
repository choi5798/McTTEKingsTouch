package com.mcttekingstouch.mcttekingstouch.repository;

import com.mcttekingstouch.mcttekingstouch.model.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.mcttekingstouch.mcttekingstouch.JdbcUtils.toUUID;

@Repository
public class OrderJdbcRepository implements OrderRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Order> orderRowMapper = (rs, rowNum) -> {
        UUID orderId = toUUID(rs.getBytes("order_id"));
        Email email = new Email(rs.getString("email"));
        String address = rs.getString("address");
        String postcode = rs.getString("postcode");
        OrderStatus orderStatus = OrderStatus.valueOf(rs.getString("status"));
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();
        return new Order(orderId,
                email,
                address,
                postcode,
                orderStatus,
                createdAt,
                updatedAt);
    };

    private Map<String, Object> toParamMap(Order order) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", order.getOrderId().toString().getBytes());
        map.put("email", order.getEmail().getAddress());
        map.put("address", order.getAddress());
        map.put("postcode", order.getPostcode());
        map.put("status", order.getStatus().toString());
        map.put("createdAt", order.getCreatedAt());
        map.put("updatedAt", order.getUpdatedAt());
        return map;
    }

    @Override
    public int save(Order order) {
        Optional<Order> optionalOrder = findById(order.getOrderId());
        int updated = 0;
        // 주문이 없는 경우 새로 만들기
        if (optionalOrder.isEmpty()) {
            updated = jdbcTemplate.update("INSERT INTO orders(order_id, email, address, postcode, status, created_at, updated_at) " +
                    "VALUES (UUID_TO_BIN(:orderId), :email, :address, :postcode, :status, :createdAt, :updatedAt)", toParamMap(order));

            order.getOrderItems().forEach(orderItems -> {
                jdbcTemplate.update("INSERT INTO order_items(burger_id, order_id, burger_name, quantity, created_at, updated_at) " +
                        "VALUES (UUID_TO_BIN(:burgerId), UUID_TO_BIN(:orderId), :burgerName, :quantity, :createdAt, :updatedAt)", new HashMap<>() {{
                    put("burgerId", orderItems.burgerId().toString().getBytes());
                    put("orderId", order.getOrderId().toString().getBytes());
                    put("burgerName", orderItems.burgerName());
                    put("quantity", orderItems.quantity());
                    put("createdAt", LocalDateTime.now());
                    put("updatedAt", LocalDateTime.now());
                }});
            });
        }
        // 기존에 주문이 있는 경우 변경하기
        if (optionalOrder.isPresent()) {
            updated = jdbcTemplate.update("UPDATE orders SET email = :email, address = :address, postcode = :postcode, status = :status, created_at = :createdAt, updated_at = :updatedAt " +
                            "WHERE order_id = UUID_TO_BIN(:orderId)",
                    toParamMap(order));
        }
        return updated;

    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query("SELECT * FROM orders", orderRowMapper);
    }

    @Override
    public Optional<Order> findById(UUID orderId) {
        try {
            Order order = jdbcTemplate.queryForObject("SELECT * FROM orders WHERE order_id = UUID_TO_BIN(:orderId)",
                    Collections.singletonMap("orderId", orderId.toString().getBytes()),
                    orderRowMapper);
            return Optional.ofNullable(order);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Order> findByEmail(Email email) {
        return jdbcTemplate.query("SELECT * FROM orders WHERE email = :email",
                Collections.singletonMap("email", email.getAddress()),
                orderRowMapper);
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM orders", Collections.emptyMap());
    }
}
