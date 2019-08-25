package com.company.productservice.controller;

import com.company.productservice.dao.ProductDao;
import com.company.productservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/products")
@CacheConfig(cacheNames = "products")
public class ProductController {

    @Autowired
    ProductDao dao;

    @PostMapping
    @CachePut(key = "#result.getProductId()")
    public Product submitProduct(@RequestBody @Valid Product product) {
        return dao.createProduct(product);
    }

    @Cacheable
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) {
         return dao.getProduct(id);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return dao.getAllProducts();
    }

    @PutMapping
    @CacheEvict(key = "#products.getProductId()")
    public void updateProduct(@RequestBody Product product) {
        dao.updateProduct(product);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(key = "#products.getProductId()")
    public void deleteProduct(@PathVariable int id) {
        dao.deleteProduct(id);
    }
}
