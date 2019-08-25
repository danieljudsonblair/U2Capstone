package com.company.retailapiservice.controller;


import com.company.retailapiservice.model.Inventory;
import com.company.retailapiservice.model.Product;
import com.company.retailapiservice.service.ServiceLayer;
import com.company.retailapiservice.viewModel.ProductView;
import com.company.retailapiservice.viewModel.PurchaseReturnViewModel;
import com.company.retailapiservice.viewModel.PurchaseSendViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@CacheConfig(cacheNames = {"invoices"})

public class RetailController {
    @Autowired
    ServiceLayer service;

//    @CachePut(key = "#results.getInvoice().getInvoiceId()")
    @PostMapping(value = "/purchase")
    public PurchaseReturnViewModel createPurchase(@RequestBody @Valid PurchaseSendViewModel psvm) {
        return service.savePurchase(psvm);
    }

    @GetMapping(value = "/products/inventory")
    public List<Product> fetchProductsCurrentlyInInventory() {
        return service.getProductsInInventory();
    }

    @GetMapping(value = "/inventory")
    public List<Inventory> fetchAllInventories() {
        return service.getAllInventories();
    }

    @GetMapping(value = "/inventory/{inventoryId}")
    public Inventory fetchInventoryById(@PathVariable int inventoryId) {
        return service.getInventoryById(inventoryId);
    }

    @GetMapping(value = "/products/{productId}")
    public Product fetchProduct(@PathVariable int productId) {
        return service.getProductByProductId(productId);
    }

    @GetMapping(value = "/products")
    public List<Product> fetchAllProducts() {
        return service.getAllProducts();
    }
}
