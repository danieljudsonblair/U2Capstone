package com.company.adminapiservice.controller;

import com.company.adminapiservice.model.*;
import com.company.adminapiservice.service.ServiceLayer;
import com.company.adminapiservice.viewModel.PurchaseReturnViewModel;
import com.company.adminapiservice.viewModel.PurchaseSendViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
public class AdminController {

    @Autowired
    ServiceLayer service;

    @PostMapping("/products")
    public Product createProduct(@RequestBody @Valid Product product) {
        return service.saveProduct(product);
    }

    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody @Valid Customer customer) {
        return service.saveCustomer(customer);
    }

    @PostMapping("/levelup")
    public LevelUp createLevelUp(@RequestBody @Valid LevelUp levelup) {
        return service.saveLevelUp(levelup);
    }

    @PostMapping("/inventory")
    public Inventory createInventory(@RequestBody @Valid Inventory inventory) {
        return service.saveInventory(inventory);
    }

    @PostMapping("/purchase")
    public PurchaseReturnViewModel createInvoice(@RequestBody @Valid PurchaseSendViewModel psvm) {
        return service.savePurchase(psvm);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return service.fetchAllProducts();
    }

    @GetMapping("/products/invoice/{id}")
    public List<Product> getProductsByInvoiceId(@PathVariable int id){
        return service.fetchProductsByInvoiceId(id);
    }

    @GetMapping("/products/inventory")
    public List<Product> getProductsInInventory() {
        return service.fetchProductsInInventory();
    }

    @GetMapping("/inventory")
    public List<Inventory> getAllInventorys() {
        return service.fetchAllInventory();
    }

    @GetMapping("/invoices")
    public List<InvoiceView> getAllInvoices() {
        return service.fetchAllInvoices();
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return service.fetchAllCustomers();
    }

    @GetMapping("/levelup")
    public List<LevelUp> getAllLevelUps() {
        return service.fetchAllLevelUps();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable int id) {
        return service.fetchProduct(id);
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable int id) {
        return service.fetchCustomer(id);
    }

    @GetMapping("/inventory/{id}")
    public Inventory getInventoryById(@PathVariable int id) {
        return service.fetchInventory(id);
    }

    @GetMapping("/invoices/{id}")
    public InvoiceView getInvoiceById(@PathVariable int id) {
        return service.fetchInvoice(id);
    }

    @GetMapping("/levelup/{id}")
    public LevelUp getLevelUpById(@PathVariable int id) {
        return service.fetchLevelUp(id);
    }

    @PutMapping("/products/{id}")
    public void updateProduct(@PathVariable int id, @RequestBody Product product) {
        product.setProductId(id);
        service.updateProduct(product);
    }

    @PutMapping("/customers/{id}")
    public void updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        customer.setCustomerId(id);
        service.updateCustomer(customer);
    }

    @PutMapping("/invoices/{id}")
    public void updateInvoice(@PathVariable int id, @RequestBody InvoiceView invoice) {
        invoice.setInvoiceId(id);
        service.updateInvoice(invoice);
    }

    @PutMapping("/levelups/{id}")
    public void updateLevelUp(@PathVariable int id, @RequestBody LevelUp levelup) {
        levelup.setLevelUpId(id);
        service.updateLevelUp(levelup);
    }

    @PutMapping("/inventory/{id}")
    public void updateInventory(@PathVariable int id, @RequestBody Inventory inventory) {
        inventory.setInventoryId(id);
        service.updateInventory(inventory);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable int id) {
        service.deleteProduct(id);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable int id) {
        service.deleteCustomer(id);
    }

    @DeleteMapping("/invoices/{id}")
    public void deleteInvoice(@PathVariable int id) {
        service.deleteInvoice(id);
    }

    @DeleteMapping("/inventory/{id}")
    public void deleteInventory(@PathVariable int id) {
        service.deleteInventory(id);
    }

    @DeleteMapping("/levelup/{id}")
    public void deleteLevelUp(@PathVariable int id) {
        service.deleteLevelUp(id);
    }
}
