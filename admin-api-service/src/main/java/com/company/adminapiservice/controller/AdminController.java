package com.company.adminapiservice.controller;

import com.company.adminapiservice.model.*;
import com.company.adminapiservice.service.ServiceLayer;
import com.company.adminapiservice.viewModel.PurchaseReturnViewModel;
import com.company.adminapiservice.viewModel.PurchaseSendViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RefreshScope
public class AdminController {

    @Autowired
    ServiceLayer service;

    @PostMapping("/purchase")
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseReturnViewModel createPurchase(@RequestBody @Valid PurchaseSendViewModel psvm, Principal principal) {
        return service.savePurchase(psvm);
    }

    @PostMapping("/invoices")
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceView saveInvoice(@RequestBody @Valid InvoiceView iv, Principal principal) {
        return service.saveInvoice(iv);
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody @Valid Product product, Principal principal) {
        return service.saveProduct(product);
    }

    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody @Valid Customer customer, Principal principal) {
        return service.saveCustomer(customer);
    }

    @PostMapping("/levelup")
    @ResponseStatus(HttpStatus.CREATED)
    public LevelUp createLevelUp(@RequestBody @Valid LevelUp levelup, Principal principal) {
        return service.saveLevelUp(levelup);
    }

    @PostMapping("/inventory")
    @ResponseStatus(HttpStatus.CREATED)
    public Inventory createInventory(@RequestBody @Valid Inventory inventory, Principal principal) {
        return service.saveInventory(inventory);
    }


    @GetMapping("/products")
    public List<Product> getAllProducts(Principal principal) {
        return service.fetchAllProducts();
    }

    @GetMapping("/products/invoice/{id}")
    public List<Product> getProductsByInvoiceId(@PathVariable int id, Principal principal){
        return service.fetchProductsByInvoiceId(id);
    }

    @GetMapping("/products/inventory")
    public List<Product> getProductsInInventory(Principal principal) {
        return service.fetchProductsInInventory();
    }

    @GetMapping("/inventory")
    public List<Inventory> getAllInventorys(Principal principal) {
        return service.fetchAllInventory();
    }

    @GetMapping("/invoices")
    public List<InvoiceView> getAllInvoices(Principal principal) {
        return service.fetchAllInvoices();
    }

    @GetMapping(value = "/invoices/customer/{customerId}")
    public List<InvoiceView> fetchInvoicesByCustomerId(@PathVariable int customerId, Principal principal) {
        return service.getInvoicesByCustomerId(customerId);
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers(Principal principal) {
        return service.fetchAllCustomers();
    }

    @GetMapping("/levelup")
    public List<LevelUp> getAllLevelUps(Principal principal) {
        return service.fetchAllLevelUps();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable int id, Principal principal) {
        return service.fetchProduct(id);
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable int id, Principal principal) {
        return service.fetchCustomer(id);
    }

    @GetMapping("/inventory/{id}")
    public Inventory getInventoryById(@PathVariable int id, Principal principal) {
        return service.fetchInventory(id);
    }

    @GetMapping("/invoices/{id}")
    public InvoiceView getInvoiceById(@PathVariable int id, Principal principal) {
        return service.fetchInvoice(id);
    }

    @GetMapping("/levelup/{id}")
    public LevelUp getLevelUpById(@PathVariable int id, Principal principal) {
        return service.fetchLevelUp(id);
    }

    @PutMapping("/products/{id}")
    public void updateProduct(@PathVariable int id, @RequestBody Product product, Principal principal) {
        product.setProductId(id);
        service.updateProduct(product);
    }

    @PutMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@PathVariable int id, @RequestBody Customer customer, Principal principal) {
        customer.setCustomerId(id);
        service.updateCustomer(customer);
    }

    @PutMapping("/invoices/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInvoice(@PathVariable int id, @RequestBody InvoiceView invoice, Principal principal) {
        invoice.setInvoiceId(id);
        service.updateInvoice(invoice);
    }

    @PutMapping("/levelups/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLevelUp(@PathVariable int id, @RequestBody LevelUp levelup, Principal principal) {
        levelup.setLevelUpId(id);
        service.updateLevelUp(levelup);
    }

    @PutMapping("/inventory/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInventory(@PathVariable int id, @RequestBody Inventory inventory, Principal principal) {
        inventory.setInventoryId(id);
        service.updateInventory(inventory);
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable int id, Principal principal) {
        service.deleteProduct(id);
    }

    @DeleteMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable int id, Principal principal) {
        service.deleteCustomer(id);
    }

    @DeleteMapping("/invoices/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable int id, Principal principal) {
        service.deleteInvoice(id);
    }

    @DeleteMapping("/inventory/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable int id, Principal principal) {
        service.deleteInventory(id);
    }

    @DeleteMapping("/levelup/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLevelUp(@PathVariable int id, Principal principal) {
        service.deleteLevelUp(id);
    }
}
