package com.company.productservice.controller;

import com.company.productservice.dao.ProductDao;
import com.company.productservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
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
        try {
            return dao.getProduct(id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return dao.getAllProducts();
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable int id, @RequestBody Product product) {
        product.setProductId(id);
        dao.updateProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        dao.deleteProduct(id);
    }
}
