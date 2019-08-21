package com.company.inventoryservice.dao;

import com.company.inventoryservice.model.Inventory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InventoryDaoJdbcTemplateImpl implements InventoryDao {

    public static final String INSERT_INVENTORY = "insert into inventory " +
            "(product_id, quantity) values (?, ?)";
    public static final String SELECT_INVENTORY_BY_ID = "select * from inventory " +
            "where inventory_id = ?";
    public static final String SELECT_ALL_INVENTORIES = "select * from inventory";
    public static final String UPDATE_INVENTORY = "update inventory set " +
            "product_id = ?, quantity = ? where inventory_id = ?";
    public static final String DELETE_INVENTORY = "delete from task";
    @Override
    public Inventory createInvetory(Inventory inventory) {
        return null;
    }

    @Override
    public Inventory getInventory(int inventoryId) {
        return null;
    }

    @Override
    public List<Inventory> getAllInventories() {
        return null;
    }

    @Override
    public void updateInventory(Inventory inventory) {

    }

    @Override
    public void deleteInventory(int inventoryId) {

    }
}
