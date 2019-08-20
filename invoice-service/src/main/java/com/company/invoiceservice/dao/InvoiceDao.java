package com.company.invoiceservice.dao;

import com.company.invoiceservice.model.Invoice;

import java.util.List;

public interface InvoiceDao {
    public Invoice add(Invoice invoice);
    public Invoice get(int invoice_id);
    public List<Invoice> getAll();
    public List<Invoice> getByCustomerId(int customer_id);
    public void update(Invoice invoice);
    public void delete(int invoice_id);
}
