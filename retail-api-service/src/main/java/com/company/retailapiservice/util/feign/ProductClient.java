package com.company.retailapiservice.util.feign;

import com.company.retailapiservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {
    @PostMapping(value = "/products")
    public Product submitProduct(@RequestBody @Valid Product product);

    @GetMapping(value = "/products/{id}")
    public Product getProductById(@PathVariable int id);

    @GetMapping(value = "/products")
    public List<Product> getAllProducts();

    @PutMapping(value = "/products")
    public void updateProduct(@RequestBody Product product);

    @DeleteMapping(value = "/products/{id}")
    public void deleteProduct(@PathVariable int id);
}
