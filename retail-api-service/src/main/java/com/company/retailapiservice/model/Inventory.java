package com.company.retailapiservice.model;

import com.company.retailapiservice.viewModel.InventoryView;

import java.util.Objects;

public class Inventory extends InventoryView {

    private int productId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Inventory inventory = (Inventory) o;
        return getProductId() == inventory.getProductId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProductId());
    }
}
