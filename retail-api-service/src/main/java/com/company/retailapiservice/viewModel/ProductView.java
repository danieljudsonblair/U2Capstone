package com.company.retailapiservice.viewModel;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductView {

    private String productName;
    private String productDescription;
    private BigDecimal quantity;
    private BigDecimal listPrice;
    private BigDecimal productTotal;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(BigDecimal productTotal) {
        this.productTotal = productTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductView that = (ProductView) o;
        return getProductName().equals(that.getProductName()) &&
                getProductDescription().equals(that.getProductDescription()) &&
                getQuantity().equals(that.getQuantity()) &&
                getListPrice().equals(that.getListPrice()) &&
                getProductTotal().equals(that.getProductTotal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductName(), getProductDescription(), getQuantity(), getListPrice(), getProductTotal());
    }
}
