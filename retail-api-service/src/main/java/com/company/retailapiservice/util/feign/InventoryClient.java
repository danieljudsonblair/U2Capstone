package com.company.retailapiservice.util.feign;

import com.company.retailapiservice.model.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
    @PostMapping(value = "/inventory")
    public Inventory submitInventory(@RequestBody @Valid Inventory inventory);

    @GetMapping(value = "/inventory/{id}")
    public Inventory getInventoryById(@PathVariable int id);

    @GetMapping(value = "/productId/inventory/{inventory_id}")
    public int getProductIdByInventoryId(@PathVariable int inventory_id);

    @GetMapping(value = "/inventory")
    public List<Inventory> getAllInventorys();

    @PutMapping(value = "/inventory")
    public void updateInventory(@RequestBody Inventory inventory);

    @DeleteMapping(value = "/inventory/{id}")
    public void deleteInventory(@PathVariable int id);
}
