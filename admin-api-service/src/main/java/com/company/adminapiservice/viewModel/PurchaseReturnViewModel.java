package com.company.adminapiservice.viewModel;

import com.company.adminapiservice.model.Customer;
import com.company.adminapiservice.model.Invoice;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class PurchaseReturnViewModel {

    private Invoice invoice;
    private Customer customer;
    private List<ProductView> productList;
    private int lvlUpPtsThisPurchase;
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

    public int getLvlUpPtsThisPurchase() {
        return lvlUpPtsThisPurchase;
    }

    public void setLvlUpPtsThisPurchase(int lvlUpPtsThisPurchase) {
        this.lvlUpPtsThisPurchase = lvlUpPtsThisPurchase;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseReturnViewModel that = (PurchaseReturnViewModel) o;
        return getLvlUpPtsThisPurchase() == that.getLvlUpPtsThisPurchase() &&
                getInvoice().equals(that.getInvoice()) &&
                getCustomer().equals(that.getCustomer()) &&
                getProductList().equals(that.getProductList()) &&
                getTotalPrice().equals(that.getTotalPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInvoice(), getCustomer(), getProductList(), getLvlUpPtsThisPurchase(), getTotalPrice());
    }
}
