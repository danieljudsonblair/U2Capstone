package com.company.retailapiservice.controller;


import com.company.retailapiservice.model.Inventory;
import com.company.retailapiservice.model.InvoiceView;
import com.company.retailapiservice.model.Product;
import com.company.retailapiservice.service.ServiceLayer;
import com.company.retailapiservice.viewModel.ProductView;
import com.company.retailapiservice.viewModel.PurchaseReturnViewModel;
import com.company.retailapiservice.viewModel.PurchaseSendViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
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
    @PostMapping(value = "/invoices")
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseReturnViewModel createPurchase(@RequestBody @Valid PurchaseSendViewModel psvm) {
        return service.savePurchase(psvm);
    }

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    public InvoiceView getInvoiceById(@PathVariable int id) {
        return service.getInvoice(id);
    }

    @RequestMapping(value = "/invoices/customer/{id}", method = RequestMethod.GET)
    public List<InvoiceView> getInvoicesByCustomerId(@PathVariable int id) {
        return service.getInvoicesByCustomerId(id);
    }

    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public List<InvoiceView> getAllInvoices() {
        return service.getAllInvoices();
    }

    @GetMapping(value = "/products/inventory")
    public List<Product> fetchProductsCurrentlyInInventory() {
        return service.getProductsInInventory();
    }

    @GetMapping(value = "/products/{id}")
    public Product fetchProduct(@PathVariable int id) {
        return service.getProductByProductId(id);
    }

    @RequestMapping(value = "/products/invoice/{id}", method = RequestMethod.GET)
    public List<Product> getProductsByInvoiceId(@PathVariable int id) {
        return service.getProductsByInvoiceId(id);
    }

    @RequestMapping(value = "/levelup/customer/{id}", method = RequestMethod.GET)
    public int getLevelUpPointsByCustomerId(@PathVariable int id) {
        return service.getLevelUpByCustomerId(id);
    }


}
