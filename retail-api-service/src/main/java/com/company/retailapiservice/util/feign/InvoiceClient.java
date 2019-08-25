package com.company.retailapiservice.util.feign;

import com.company.retailapiservice.model.InvoiceView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "invoice-service")
public interface InvoiceClient {
    @PostMapping(value = "/invoices")
    public InvoiceView createInvoice(@RequestBody InvoiceView invoiceView);

    @GetMapping(value = "/invoices")
    public List<InvoiceView> fetchAllInvoices();

    @GetMapping(value = "/invoices/{invoiceId}")
    public InvoiceView fetchInvoicesById(@PathVariable int invoiceId);

    @PutMapping(value = "/invoices")
    public void updateInvoice(@RequestBody InvoiceView ivm);

    @DeleteMapping(value = "/invoices/{id}")
    public void deleteInvoice(@PathVariable int id);

}
