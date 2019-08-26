package com.company.retailapiservice.util.feign;

import com.company.retailapiservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping(value = "/products/{id}")
    public Product getProductById(@PathVariable int id);

    @GetMapping(value = "/products")
    public List<Product> getAllProducts();
}
