package com.company.retailapiservice.viewModel;

import com.company.retailapiservice.model.Customer;
import com.company.retailapiservice.model.Invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class PurchaseReturnViewModel {

    private Invoice invoice;
    private Customer customer;
    private List<ProductView> productList;
    private int lvlUpPtsBeforePurchase;
    private int lvlUpPtsAfterPurchase;
    private LocalDate memberSince;
    private BigDecimal totalPrice;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<ProductView> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductView> productList) {
        this.productList = productList;
    }

    public int getLvlUpPtsBeforePurchase() {
        return lvlUpPtsBeforePurchase;
    }

    public void setLvlUpPtsBeforePurchase(int lvlUpPtsBeforePurchase) {
        this.lvlUpPtsBeforePurchase = lvlUpPtsBeforePurchase;
    }

    public int getLvlUpPtsAfterPurchase() {
        return lvlUpPtsAfterPurchase;
    }

    public void setLvlUpPtsAfterPurchase(int lvlUpPtsAfterPurchase) {
        this.lvlUpPtsAfterPurchase = lvlUpPtsAfterPurchase;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(LocalDate memberSince) {
        this.memberSince = memberSince;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseReturnViewModel that = (PurchaseReturnViewModel) o;
        return getLvlUpPtsBeforePurchase() == that.getLvlUpPtsBeforePurchase() &&
                getLvlUpPtsAfterPurchase() == that.getLvlUpPtsAfterPurchase() &&
                getInvoice().equals(that.getInvoice()) &&
                getCustomer().equals(that.getCustomer()) &&
                getProductList().equals(that.getProductList()) &&
                getTotalPrice().equals(that.getTotalPrice()) &&
                getMemberSince().equals(that.getMemberSince());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInvoice(), getCustomer(), getProductList(), getLvlUpPtsBeforePurchase(), getLvlUpPtsAfterPurchase(), getTotalPrice(), getMemberSince());
    }
}
