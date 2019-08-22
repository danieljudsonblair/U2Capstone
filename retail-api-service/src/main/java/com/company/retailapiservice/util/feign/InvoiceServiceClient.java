package com.company.retailapiservice.util.feign;

import com.company.retailapiservice.model.Invoice;
import com.company.retailapiservice.model.InvoiceView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "invoice-service")
public interface InvoiceServiceClient {
    @PostMapping(value = "/invoices")
    public InvoiceView createInvoice(@RequestBody InvoiceView invoiceView);

    @GetMapping(value = "/invoices")
    public List<InvoiceView> fetchAllInvoices();

    @GetMapping(value = "/invoices/{invoiceId}")
    public InvoiceView fetchInvoicesById(@PathVariable int invoiceId);

    @GetMapping(value = "/invoices/customer/{customerId}")
    public List<InvoiceView> fetchInvoicesByCustomerId(@PathVariable int customerId);



}
