package com.mcttekingstouch.mcttekingstouch.repository;

import com.mcttekingstouch.mcttekingstouch.model.Burger;
import com.mcttekingstouch.mcttekingstouch.model.BurgerCompany;
import com.mcttekingstouch.mcttekingstouch.model.BurgerType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class BurgerJdbcRepositoryTest {
    private final int SUCCESS = 1;

    @Autowired
    private BurgerRepository burgerJdbcRepository;

    static Burger burger;

    static UUID testUuid = UUID.randomUUID();

    @BeforeAll
    static void setup() {
        burger = new Burger(testUuid,
                "시험용 버거",
                1000,
                BurgerType.BEEF_BURGER,
                BurgerCompany.BURGER_KING,
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    @Test
    @Order(1)
    @DisplayName("버거를 저장할 수 있다")
    void testSave() {
        int isSuccess = burgerJdbcRepository.save(burger);
        System.out.println("테스트1: 버거 ID : " + burger.getBurgerId());
        assertThat(isSuccess, is(SUCCESS));
    }

    @Test
    @Order(2)
    @DisplayName("기존의 버거를 수정할 수 있다")
    void testUpdate() {
        Burger newBurger = new Burger(testUuid,
                "시험용 버거2",
                3000,
                BurgerType.CHICKEN_BURGER,
                BurgerCompany.MCDONALDS,
                burger.getCreatedAt(),
                LocalDateTime.now());

        int isSuccess = burgerJdbcRepository.save(newBurger);
        System.out.println("테스트2: 버거2 ID : " + newBurger.getBurgerId());
        assertThat(isSuccess, is(SUCCESS));
        assertThat(burgerJdbcRepository.count(), is(1));
    }

    @Test
    @Order(3)
    @DisplayName("버거 종류의 갯수를 구할 수 있다")
    void testCount() {
        int count = burgerJdbcRepository.count();

        assertThat(count, is(1));
    }

    @Test
    @Order(4)
    @DisplayName("모든 버거를 삭제할 수 있다")
    void testDeleteAll() {

        int deleted = burgerJdbcRepository.deleteAll();

        assertThat(deleted, is(1));
    }

    @Test
    @Order(5)
    @DisplayName("비어있는 상태의 갯수를 구할 수 있다")
    void testCountEmpty() {
        int count = burgerJdbcRepository.count();

        assertThat(count, is(0));
    }
}