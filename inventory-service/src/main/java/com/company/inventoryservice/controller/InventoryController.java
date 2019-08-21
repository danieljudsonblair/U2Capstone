package com.company.inventoryservice.controller;

import com.company.inventoryservice.dao.InventoryDao;
import com.company.inventoryservice.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    InventoryDao dao;

    @PostMapping
    public Inventory submitInventory(@RequestBody @Valid Inventory inventory) {
        return dao.createInventory(inventory);
    }

    @GetMapping("/{id}")
    public Inventory getInventoryById(@PathVariable int id) {
        try {
            return dao.getInventory(id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @GetMapping
    public List<Inventory> getAllInventorys() {
        return dao.getAllInventories();
    }

    @PutMapping("/{id}")
    public void updateInventory(@PathVariable int id, @RequestBody Inventory inventory) {
        inventory.setInventoryId(id);
        dao.updateInventory(inventory);
    }

    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable int id) {
        dao.deleteInventory(id);
    }
}
