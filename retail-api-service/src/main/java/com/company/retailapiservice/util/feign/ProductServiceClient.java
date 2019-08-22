package com.company.retailapiservice.util.feign;

import com.company.retailapiservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductServiceClient {
    @GetMapping(value = "/products/{productId}")
    public Product fetchProduct(@PathVariable int productId);

    @GetMapping(value = "/products/inventory")
    public List<Product> fetchProductsCurrentlyInInventory();

    @GetMapping(value = "/products/invoice/{invoiceId}")
    public List<Product> fetchProductsByInvoiceId(@PathVariable int invoiceId);


}
