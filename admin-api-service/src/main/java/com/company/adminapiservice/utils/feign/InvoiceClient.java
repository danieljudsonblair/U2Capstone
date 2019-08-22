package com.company.adminapiservice.utils.feign;

import com.company.adminapiservice.model.InvoiceView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "invoice-service")
public interface InvoiceClient {

    @PostMapping(value = "/invoices")
    public InvoiceView createInvoice(@RequestBody @Valid InvoiceView iv);

    @GetMapping(value = "/invoices")
    public List<InvoiceView> getAllInvoices();

    @GetMapping(value = "/invoices/{invoice_id}")
    public InvoiceView getInvoiceById(@PathVariable int invoice_id);

    @PutMapping(value = "/invoices/{invoice_id}")
    public void updateInvoice(@RequestBody InvoiceView iv);

    @DeleteMapping(value = "/invoices/{invoice_id}")
    public void deleteInvoice(@PathVariable int invoice_id);
}
