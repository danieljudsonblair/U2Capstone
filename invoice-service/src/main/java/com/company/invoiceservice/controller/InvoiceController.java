package com.company.invoiceservice.controller;


import com.company.invoiceservice.model.Invoice;
import com.company.invoiceservice.service.ServiceLayer;
import com.company.invoiceservice.view.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    ServiceLayer service;

    @PostMapping
    public InvoiceViewModel submitInvoice(@RequestBody @Valid InvoiceViewModel ivm) {
        return service.saveInvoice(ivm);
    }

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

    @PutMapping("/{id}")
    public void updateInvoice(@PathVariable int id, @RequestBody InvoiceViewModel ivm) {
        ivm.setInvoiceId(id);
        service.updateInvoice(ivm);
    }

    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable int id) {
        service.deleteInvoice(id);
    }
}
