package com.company.inventoryservice.dao;

import com.company.inventoryservice.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InventoryDaoJdbcTemplateImpl implements InventoryDao {

    private JdbcTemplate jdbcTemplate;

    public static final String INSERT_INVENTORY =
            "insert into inventory (product_id, quantity) values (?, ?)";

    public static final String SELECT_INVENTORY_BY_ID =
            "select * from inventory where inventory_id = ?";

    public static final String SELECT_ALL_INVENTORIES =
            "select * from inventory";

    public static final String UPDATE_INVENTORY =
            "update inventory set product_id = ?, quantity = ? where inventory_id = ?";

    public static final String DELETE_INVENTORY =
            "delete from inventory where inventory_id = ?";

    @Autowired
    public InventoryDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public Inventory createInventory(Inventory inventory) {
        jdbcTemplate.update(
                INSERT_INVENTORY,
                inventory.getProductId(),
                inventory.getQuantity()
        );

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        inventory.setInventoryId(id);

        return inventory;
    }

    @Override
    public Inventory getInventory(int inventoryId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_INVENTORY_BY_ID, this::mapRowToInventory, inventoryId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Inventory> getAllInventories() {
        return jdbcTemplate.query(SELECT_ALL_INVENTORIES, this::mapRowToInventory);
    }

    @Transactional
    @Override
    public void updateInventory(Inventory inventory) {
        jdbcTemplate.update(
                UPDATE_INVENTORY,
                inventory.getProductId(),
                inventory.getQuantity(),
                inventory.getInventoryId()
        );
    }

    @Transactional
    @Override
    public void deleteInventory(int inventoryId) {
        jdbcTemplate.update(DELETE_INVENTORY, inventoryId);
    }

    private Inventory mapRowToInventory(ResultSet rs, int rowNum) throws SQLException {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(rs.getInt("inventory_id"));
        inventory.setProductId(rs.getInt("product_id"));
        inventory.setQuantity(rs.getInt("quantity"));

        return inventory;
    }
}
