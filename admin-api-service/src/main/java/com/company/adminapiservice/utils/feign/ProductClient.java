package com.company.adminapiservice.utils.feign;

import com.company.adminapiservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient("product-service")
public interface ProductClient {

    @PostMapping(value = "/products")
    public Product createProduct(@RequestBody @Valid Product product);

    @GetMapping(value = "/products")
    public List<Product> getAllProducts();

    @GetMapping(value = "/products/{id}")
    public Product getProduct(@PathVariable int id);

    @PutMapping(value = "/products")
    public void updateProduct(@RequestBody Product product);

    @DeleteMapping(value = "/products/{product_id}")
    public void deleteProduct(@PathVariable int product_id);

}
