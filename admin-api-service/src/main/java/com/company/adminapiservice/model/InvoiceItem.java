package com.company.adminapiservice.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceItem {
    @Min(0)
    private int invoiceItemId;
    @Min(1)
    private int invoiceId;
    @Min(1)
    private int inventoryId;
    @Min(0)
    private int quantity;
    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "99999.99", inclusive = true)
    private BigDecimal unitPrice;

    public int getInvoiceItemId() {
        return invoiceItemId;
    }

    public void setInvoiceItemId(int invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceItem that = (InvoiceItem) o;
        return getInvoiceItemId() == that.getInvoiceItemId() &&
                getInvoiceId() == that.getInvoiceId() &&
                getInventoryId() == that.getInventoryId() &&
                getQuantity() == that.getQuantity() &&
                getUnitPrice().equals(that.getUnitPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInvoiceItemId(), getInvoiceId(), getInventoryId(), getQuantity(), getUnitPrice());
    }
}
