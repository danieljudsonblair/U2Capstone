package com.company.retailapiservice.controller;


import com.company.retailapiservice.model.Inventory;
import com.company.retailapiservice.model.InvoiceView;
import com.company.retailapiservice.model.LevelUp;
import com.company.retailapiservice.model.Product;
import com.company.retailapiservice.service.ServiceLayer;
import com.company.retailapiservice.viewModel.PurchaseReturnViewModel;
import com.company.retailapiservice.viewModel.PurchaseSendViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    @CachePut(key = "#results.getId()")
    @PostMapping(value = "/purchase")
    public PurchaseReturnViewModel createPurchase(@RequestBody @Valid PurchaseSendViewModel psvm) {
        return service.savePurchase(psvm);
    }


    @GetMapping(value = "/invoices")
    public List<InvoiceView> fetchAllInvoices() {
        return service.getAllInvoices();
    }


    @GetMapping(value = "/invoices/{invoiceId}")
    public InvoiceView fetchInvoicesById(@PathVariable int invoiceId) {
        return service.getInvoiceByInvoiceId(invoiceId);
    }


    @GetMapping(value = "/invoices/customer/{customerId}")
    public List<InvoiceView> fetchInvoicesByCustomerId(@PathVariable int customerId) {
        return service.getInvoicesByCustomerId(customerId);
    }

    @GetMapping(value = "/products/{productId}")
    public Product fetchProduct(@PathVariable int productId) {
        return service.getProductByProductId(productId);
    }

    @GetMapping(value = "/products/inventory")
    public List<Product> fetchProductsCurrentlyInInventory() {
        return service.getProductsInInventory();
    }

    @GetMapping(value = "/products/invoice/{invoiceId}")
    public List<Product> fetchProductsByInvoiceId(@PathVariable int invoiceId) {
        return service.getProductsByInvoiceId(invoiceId);
    }


    @GetMapping(value = "/levelup/customer/{customerId}")
    public List<LevelUp> fetchLevelUpPointByCustomerId(@PathVariable int customerId) {
        return service.getLevelUpPointsByCustomerId(customerId);
    }

    @GetMapping(value = "/inventory")
    public List<Inventory> fetchAllInventories() {
        return service.getAllInventories();
    }

    @GetMapping(value = "/inventory/{inventoryId}")
    public Inventory fetchInventoryById(@PathVariable int inventoryId) {
        return service.getInventoryById(inventoryId);
    }
}
