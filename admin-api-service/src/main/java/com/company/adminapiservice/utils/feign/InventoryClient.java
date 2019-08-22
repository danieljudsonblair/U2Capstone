package com.company.adminapiservice.utils.feign;

import com.company.adminapiservice.model.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @PostMapping(value = "/inventory")
    public Inventory createInventory(@RequestBody @Valid Inventory inventory);

    @GetMapping(value = "/inventory/{id}")
    public Inventory getInventory(@PathVariable int id);

    @GetMapping(value = "/inventory")
    public List<Inventory> getAllInventory();

    @PutMapping(value = "/inventory/{inventory_id}")
    public void updateInventory(@RequestBody Inventory inventory);

    @DeleteMapping(value = "/inventory/{inventory_id}")
    public void deleteInventory(int inventory_id);
}
