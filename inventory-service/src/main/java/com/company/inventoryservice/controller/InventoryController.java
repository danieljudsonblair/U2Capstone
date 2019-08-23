package com.company.inventoryservice.controller;

import com.company.inventoryservice.dao.InventoryDao;
import com.company.inventoryservice.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
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
        return dao.getInventory(id);
    }

    @GetMapping
    public List<Inventory> getAllInventorys() {
        return dao.getAllInventories();
    }

    @PutMapping
    public void updateInventory(@RequestBody Inventory inventory) {
        dao.updateInventory(inventory);
    }

    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable int id) {
        dao.deleteInventory(id);
    }
}
