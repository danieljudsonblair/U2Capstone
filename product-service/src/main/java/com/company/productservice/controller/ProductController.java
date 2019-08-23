package com.company.productservice.controller;

import com.company.productservice.dao.ProductDao;
import com.company.productservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductDao dao;

    @PostMapping
    public Product submitProduct(@RequestBody @Valid Product product) {
        return dao.createProduct(product);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) {
         return dao.getProduct(id);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return dao.getAllProducts();
    }

    @PutMapping
    public void updateProduct(@RequestBody Product product) {
        dao.updateProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        dao.deleteProduct(id);
    }
}
