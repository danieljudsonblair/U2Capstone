package com.company.productservice.dao;

import com.company.productservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductDaoJdbcTemplateImpl implements ProductDao {
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_PRODUCT = "insert into product " +
            "(product_name, product_description, list_price, unit_cost) values (?, ?, ?, ?)";
    private static final String SELECT_PRODUCT = "select * from product where product_id = ?";
    private static final String SELECT_ALL_PRODUCTS = "select * from product";
    private static final String UPDATE_PRODUCT = "update product set product_name = ?, " +
            "product_description = ?, list_price = ?, unit_cost = ? where product_id = ?";
    private static final String DELETE_PRODUCT = "delete from product where product_id = ?";

    @Autowired
    public ProductDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public Product getProduct(int productId) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public void updateProduct(Product product) {

    }

    @Override
    public void deleteProduct(int productId) {

    }

    private Product mapRowToProduct(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));
        product.setProductDescription(rs.getString("product_description"));
        product.setListPrice(rs.getBigDecimal("list_price"));
        product.setUnitCost(rs.getBigDecimal("unit_price"));
        return product;
    }
}
