package com.company.invoiceservice.dao;

import com.company.invoiceservice.model.InvoiceItem;

import java.util.List;

public interface InvoiceItemDao {
    public InvoiceItem add(InvoiceItem invoiceItem);
    public InvoiceItem get(int invoiceItem_id);
    public List<InvoiceItem> getAll();
    public void update(InvoiceItem invoiceItem);
    public void delete(int invoiceItem_id);
}
