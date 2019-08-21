package com.company.productservice.dao;

import com.company.productservice.model.Product;

import java.util.List;

public interface ProductDao {
    public Product createProduct(Product product);
    public Product getProduct(int productId);
    public List<Product> getAllProducts();
    public void updateProduct(Product product);
    public void deleteProduct(int productId);

}
