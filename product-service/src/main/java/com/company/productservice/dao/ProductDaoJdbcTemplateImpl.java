package com.company.productservice.dao;

import com.company.productservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
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

    @Transactional
    @Override
    public Product createProduct(Product product) {
        jdbcTemplate.update(
                INSERT_PRODUCT,
                product.getProductName(),
                product.getProductDescription(),
                product.getListPrice(),
                product.getUnitCost()
        );

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        product.setProductId(id);

        return product;
    }

    @Override
    public Product getProduct(int productId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_PRODUCT, this::mapRowToProduct, productId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return jdbcTemplate.query(SELECT_ALL_PRODUCTS, this::mapRowToProduct);
    }

    @Transactional
    @Override
    public void updateProduct(Product product) {
        jdbcTemplate.update(
                UPDATE_PRODUCT,
                product.getProductName(),
                product.getProductDescription(),
                product.getListPrice(),
                product.getUnitCost(),
                product.getProductId()
        );
    }

    @Transactional
    @Override
    public void deleteProduct(int productId) {
        jdbcTemplate.update(DELETE_PRODUCT, productId);
    }

    private Product mapRowToProduct(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));
        product.setProductDescription(rs.getString("product_description"));
        product.setListPrice(rs.getBigDecimal("list_price"));
        product.setUnitCost(rs.getBigDecimal("unit_cost"));
        return product;
    }
}
