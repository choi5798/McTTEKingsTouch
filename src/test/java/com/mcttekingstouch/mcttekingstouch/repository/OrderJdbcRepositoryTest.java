package com.mcttekingstouch.mcttekingstouch.repository;

import com.mcttekingstouch.mcttekingstouch.model.Email;
import com.mcttekingstouch.mcttekingstouch.model.Order;
import com.mcttekingstouch.mcttekingstouch.model.OrderItems;
import com.mcttekingstouch.mcttekingstouch.model.OrderStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
class OrderJdbcRepositoryTest {

    @Autowired
    private OrderRepository repository;

    private static Order order;

    @BeforeAll
    static void setup() {
        UUID orderId = UUID.randomUUID();
        UUID burgerId = UUID.fromString("6450900f-0ddd-458e-b8bb-844935f34ecf");
        order = new Order(
                orderId,
                new Email("example@example.com"),
                "화성시",
                "12312",
                OrderStatus.ACCEPTED,
                List.of(
                        new OrderItems(burgerId, orderId, "싸이버거", 3, LocalDateTime.now(), LocalDateTime.now())
                ),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    @DisplayName("1. 주문을 저장할 수 있다")
    void testCreateOrder() {
        int saved = repository.save(order);
        assertThat(saved, is(1));
    }

    @Test
    @DisplayName("2. 주문을 변경할 수 있다")
    void testUpdateOrder() {
        order.setAddress("수원시");

        repository.save(order);

        Order newOrder = repository.findById(order.getOrderId())
                .orElseThrow(() -> new IllegalStateException("해당하는 주문이 없습니다"));

        assertThat(newOrder.getAddress(), is("수원시"));
    }

    @Test
    @DisplayName("3. 주문을 모두 삭제할 수 있다")
    @Disabled
    void testDeleteAll() {
        int deleted = repository.deleteAll();

        assertThat(deleted, is(1));
    }
}