package com.company.retailapiservice.model;

import java.util.List;
import java.util.Objects;

public class InvoiceView extends Invoice{
    private List<InvoiceItem> invoiceItemList;
    public List<InvoiceItem> getInvoiceItemList() {
        return invoiceItemList;
    }
    public void setInvoiceItemList(List<InvoiceItem> invoiceItemList) {
        this.invoiceItemList = invoiceItemList;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        InvoiceView that = (InvoiceView) o;
        return getInvoiceItemList().equals(that.getInvoiceItemList());
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getInvoiceItemList());
    }
}
