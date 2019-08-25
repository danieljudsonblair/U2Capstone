package com.company.invoiceservice.controller;


import com.company.invoiceservice.model.Invoice;
import com.company.invoiceservice.service.ServiceLayer;
import com.company.invoiceservice.view.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/invoices")
@CacheConfig(cacheNames = {"invoices"})
public class InvoiceController {

    @Autowired
    ServiceLayer service;

    @PostMapping
    @CachePut(key = "#result.getInvoiceId()")
    public InvoiceViewModel submitInvoice(@RequestBody @Valid InvoiceViewModel ivm) {
        return service.saveInvoice(ivm);
    }

    @Cacheable
    @GetMapping("/{id}")
    public InvoiceViewModel getInvoiceById(@PathVariable int id) {
        return service.findInvoice(id);
    }

    @GetMapping
    public List<InvoiceViewModel> getAllInvoices() {
        return service.findAllInvoices();
    }

    @GetMapping("/customer/{id}")
    public List<InvoiceViewModel> getInvoicesByCustomerId(@PathVariable int id) {
        return service.findByCustomerId(id);
    }

    @PutMapping
    @CacheEvict(key = "invoices.getInvoiceId()")
    public void updateInvoice(@RequestBody InvoiceViewModel ivm) {
        service.updateInvoice(ivm);
    }

    @CacheEvict(key = "invoices.getInvoiceId()")
    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable int id) {
        service.deleteInvoice(id);
    }
}
