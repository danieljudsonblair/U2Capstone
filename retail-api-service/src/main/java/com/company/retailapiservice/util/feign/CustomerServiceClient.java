package com.company.retailapiservice.util.feign;

import com.company.retailapiservice.model.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "inventory-service")
public interface CustomerServiceClient {
//    @GetMapping(value = "/products/inventory")
//    public List<Inventory> fetchAllInventories();
}