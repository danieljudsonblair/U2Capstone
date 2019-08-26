package com.company.retailapiservice.model;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    @Min(0)
    private int productId;
    @NotNull(message = "Product must contain field productName")
    @NotEmpty(message = "productName must not be empty")
    private String productName;
    @NotNull(message = "Product must contain field productDescription")
    @NotEmpty(message = "productDescription must not be empty")
    private String productDescription;
    @NotNull(message = "Product must contain field listPrice")
    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "99999.99", inclusive = true)
    private BigDecimal listPrice;
    @NotNull(message = "Product must contain field unitCost")
    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "99999.99", inclusive = true)
    private BigDecimal unitCost;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

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

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId &&
                productName.equals(product.productName) &&
                productDescription.equals(product.productDescription) &&
                listPrice.equals(product.listPrice) &&
                unitCost.equals(product.unitCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, productDescription, listPrice, unitCost);
    }
}