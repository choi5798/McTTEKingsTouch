package com.mcttekingstouch.mcttekingstouch.repository;

import com.mcttekingstouch.mcttekingstouch.model.Burger;
import com.mcttekingstouch.mcttekingstouch.model.BurgerCompany;
import com.mcttekingstouch.mcttekingstouch.model.BurgerType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.mcttekingstouch.mcttekingstouch.JdbcUtils.toUUID;

@Repository
public class BurgerJdbcRepository implements BurgerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BurgerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Burger> burgerRowMapper = (rs, rowNum) -> {

        UUID burgerId = toUUID(rs.getString("burger_id").getBytes());
        String burgerName = rs.getString("burger_name");
        long price = rs.getLong("price");
        BurgerType burgerType = BurgerType.valueOf(rs.getString("burger_type"));
        BurgerCompany burgerCompany = BurgerCompany.valueOf(rs.getString("burger_company"));
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();
        return new Burger(burgerId,
                burgerName,
                price,
                burgerType,
                burgerCompany,
                createdAt,
                updatedAt);
    };

    private Map<String, Object> toParamMap(Burger burger) {
        Map<String, Object> map = new HashMap<>();
        map.put("burgerId", burger.burgerId().toString().getBytes());
        map.put("burgerName", burger.burgerName());
        map.put("price", burger.price());
        map.put("burgerType", burger.burgerType().toString());
        map.put("burgerCompany", burger.burgerCompany().toString());
        map.put("createdAt", burger.createdAt());
        map.put("updatedAt", burger.updatedAt());
        return map;
    }

    /**
     * @return 1 if query is success, or 0
     */
    @Override

    public int save(Burger burger) {
        Optional<Burger> optionalBurger = findById(burger.burgerId());
        // 버거가 없을 경우 새로 만들어줌
        if (optionalBurger.isEmpty()) {
            return jdbcTemplate.update("INSERT INTO burgers(burger_id, burger_name, price, burger_type, burger_company, created_at, updated_at) " +
                    "VALUES (UUID_TO_BIN(:burgerId), :burgerName, :price, :burgerType, :burgerCompany, :createdAt, :updatedAt)", toParamMap(burger));
        }
        // 기존에 있는 버거를 수정해줌
        return jdbcTemplate.update("UPDATE burgers SET price = :price, burger_type = :burgerType, burger_company = :burgerCompany, created_at = :createdAt, updated_at = :updatedAt " +
                "WHERE burger_id = UUID_TO_BIN(:burgerId)", new HashMap<>() {{
            put("burgerId", burger.burgerId().toString().getBytes());
            put("burgerName", burger.burgerName());
            put("price", burger.price());
            put("burgerType", burger.burgerType().toString());
            put("burgerCompany", burger.burgerCompany().toString());
            put("createdAt", burger.createdAt());
            put("updatedAt", LocalDateTime.now());
        }});
    }

    @Override
    public List<Burger> findAll() {
        return jdbcTemplate.query("SELECT * FROM burgers", burgerRowMapper);
    }

    @Override
    public Optional<Burger> findById(UUID burgerId) {
        try {
            Burger burger = jdbcTemplate.queryForObject("SELECT * FROM burgers WHERE burger_id = UUID_TO_BIN(:burgerId)",
                    Collections.singletonMap("burgerId", burgerId.toString().getBytes()),
                    burgerRowMapper);
            return Optional.ofNullable(burger);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Burger> findByName(String burgerName) {
        try {
            Burger burger = jdbcTemplate.queryForObject("SELECT * FROM burgers WHERE burger_name = :burgerName",
                    Collections.singletonMap("burgerName", burgerName),
                    burgerRowMapper);
            return Optional.ofNullable(burger);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Burger> findByBurgerType(BurgerType burgerType) {
        return jdbcTemplate.query("SELECT * FROM burgers WHERE burger_type = :burgerType",
                Collections.singletonMap("burgerType", burgerType.toString()),
                burgerRowMapper);
    }

    @Override
    public List<Burger> findByBurgerCompany(BurgerCompany burgerCompany) {
        return jdbcTemplate.query("SELECT * FROM burgers WHERE burger_company = :burgerCompany",
                Collections.singletonMap("burgerCompany", burgerCompany.toString()),
                burgerRowMapper);
    }

    @Override
    public int count() {
        try {
            return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM burgers", new EmptySqlParameterSource(), Integer.class);
        } catch (RuntimeException e) {
            return 0;
        }

    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM burgers", Collections.emptyMap());
    }
}
