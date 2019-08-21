package com.company.inventoryservice.dao;

import com.company.inventoryservice.model.Inventory;

import java.util.List;

public interface InventoryDao {
    public Inventory createInvetory(Inventory inventory);
    public Inventory getInventory(int inventoryId);
    public List<Inventory> getAllInventories();
    public void updateInventory(Inventory inventory);
    public void deleteInventory(int inventoryId);
}
