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
        UUID orderId = toUUID(rs.getString("order_id").getBytes());
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
        map.put("orderId", order.orderId().toString().getBytes());
        map.put("email", order.email().toString());
        map.put("address", order.address());
        map.put("postcode", order.postcode());
        map.put("status", order.status().toString());
        map.put("createdAt", order.createdAt());
        map.put("updatedAt", order.updatedAt());
        return map;
    }

    @Override
    public int save(Order order) {
        Optional<Order> optionalOrder = findById(order.orderId());
        if (optionalOrder.isEmpty()) {
            return jdbcTemplate.update("INSERT INTO orders(order_id, email, address, postcode, status, created_at, updated_at) " +
                    "VALUES (UUID_TO_BIN(:orderId), :email, :address, :postcode, :status, :createdAt, :updatedAt)", toParamMap(order));
        }
        return jdbcTemplate.update("UPDATE orders SET email = :email, address = :address, postcode = :postcode, status = :status, created_at = :createdAt, updated_at = :updatedAt " +
                        "WHERE order_id = UUID_TO_BIN(:orderId)",
                new HashMap<>() {{
                    put("orderId", order.orderId().toString().getBytes());
                    put("email", order.email().toString());
                    put("address", order.address());
                    put("postcode", order.postcode());
                    put("status", order.status().toString());
                    put("createdAt", order.createdAt());
                    put("updatedAt", LocalDateTime.now());
                }});

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
                Collections.singletonMap("email", email.toString()),
                orderRowMapper);
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM orders", Collections.emptyMap());
    }
}
