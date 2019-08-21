package com.company.levelupservice.dao;

import com.company.levelupservice.model.LevelUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LevelUpDaoJdbcTemplateImpl implements LevelUpDao {

    private JdbcTemplate jdbcTemplate;

    public static final String INSERT_LEVELUP_SQL =
            "insert into level_up (customer_id, points, member_date) values (?, ?, ?)";

    public static final String SELECT_LEVELUP_SQL_BY_ID =
            "select * from level_up where level_up_id = ?";

    public static final String SELECT_ALL_LEVELUP_SQLS =
            "select * from level_up";

    public static final String SELECT_LEVELUP_SQLS_BY_CUSTOMER_ID =
            "select * from level_up where customer_id = ?";

    public static final String UPDATE_LEVELUP_SQL =
            "update level_up set customer_id = ?, points = ?, member_date = ? where level_up_id = ?";

    public static final String DELETE_LEVELUP_SQL =
            "delete from level_up where level_up_id = ?";

    @Autowired
    public LevelUpDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public LevelUp add(LevelUp levelUp) {
        jdbcTemplate.update(
                INSERT_LEVELUP_SQL,
                levelUp.getCustomerId(),
                levelUp.getPoints(),
                levelUp.getMemberDate()
        );

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        levelUp.setLevelUpId(id);

        return levelUp;
    }

    @Override
    public LevelUp get(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_LEVELUP_SQL_BY_ID, this::mapRowToLevelUp, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<LevelUp> getAll() {
        return jdbcTemplate.query(SELECT_ALL_LEVELUP_SQLS, this::mapRowToLevelUp);
    }

    @Override
    public List<LevelUp> getByCustomerId(int customer_id) {
        return jdbcTemplate.query(SELECT_LEVELUP_SQLS_BY_CUSTOMER_ID, this::mapRowToLevelUp, customer_id);
    }

    @Transactional
    @Override
    public void update(LevelUp levelUp) {
        jdbcTemplate.update(
                UPDATE_LEVELUP_SQL,
                levelUp.getCustomerId(),
                levelUp.getPoints(),
                levelUp.getMemberDate(),
                levelUp.getLevelUpId()
        );
    }

    @Transactional
    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_LEVELUP_SQL, id);
    }

    private LevelUp mapRowToLevelUp(ResultSet rs, int rowNum) throws SQLException {
        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(rs.getInt("level_up_id"));
        levelUp.setCustomerId(rs.getInt("customer_id"));
        levelUp.setPoints(rs.getInt("points"));
        levelUp.setMemberDate(rs.getDate("member_date").toLocalDate());

        return levelUp;
    }
}
