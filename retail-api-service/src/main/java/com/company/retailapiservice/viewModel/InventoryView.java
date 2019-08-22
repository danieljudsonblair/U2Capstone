package com.company.retailapiservice.viewModel;

import javax.validation.constraints.Min;
import java.util.Objects;

public class InventoryView {

    private int inventoryId;
    @Min(1)
    private int quantity;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryView that = (InventoryView) o;
        return getInventoryId() == that.getInventoryId() &&
                getQuantity() == that.getQuantity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInventoryId(), getQuantity());
    }
}