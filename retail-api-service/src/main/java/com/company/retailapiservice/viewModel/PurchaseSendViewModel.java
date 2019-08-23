package com.company.retailapiservice.viewModel;

import com.company.retailapiservice.model.Customer;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class PurchaseSendViewModel {

    private int customerId;
    private Customer customer;
    @NotNull(message = "You must purchase at least one item")
    private List<InventoryView> inventoryList;
    private LocalDate purchaseDate;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<InventoryView> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(List<InventoryView> inventoryList) {
        this.inventoryList = inventoryList;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseSendViewModel that = (PurchaseSendViewModel) o;
        return getCustomerId() == that.getCustomerId() &&
                getCustomer().equals(that.getCustomer()) &&
                getInventoryList().equals(that.getInventoryList()) &&
                getPurchaseDate().equals(that.getPurchaseDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId(), getCustomer(), getInventoryList(), getPurchaseDate());
    }
}
