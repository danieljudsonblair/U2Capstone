package com.company.productservice.daoTest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class productDaoJdbcTemplateImplTest {

    @Autowired
    ProductDao productDao;

    @Before
    public void setUp() throws Exception{
        List<Product> productList = productDao.getAllProducts();
        for(Product product: productList){
            productDao.deleteProduct(product.getProductId());
        }
    }

    @Test
    public void addGetDeleteProduct(){
        Product product = new Product();
        product.setProductName("Playstation");
        product.setProductDescription("Game console");
        product.setListPrice(new BigDecimal(350.00));
        product.setUnitCost(new BigDecimal(200.00));

        productDao.createProduct(product);

        Product product1 = productDao.getProduct(product.getProductId());
        assertEquals(product, product1);

        productDao.deleteProduct(product.getProductId);
        product1 = productDao.getProduct(product.getProductId());
        assertNull(product1);
    }

    @Test
    public void updateProduct(){
        Product product = new Product();
        product.setProductName("Playstation");
        product.setProductDescription("Game console");
        product.setListPrice(new BigDecimal(350.00));
        product.setUnitCost(new BigDecimal(200.00));

        productDao.createProduct(product);

        product.setProductName("Playstation2");
        product.setProductDescription("Game console");
        product.setListPrice(new BigDecimal(350.00));
        product.setUnitCost(new BigDecimal(250.00));

        productDao.updateProduct(product);

        Product product1 = productDao.getProduct(product.getProductId());
        assertEquals(product1, product);
    }

    @Test
    public void getAllProducts(){
        Product product = new Product();
        product.setProductName("Playstation");
        product.setProductDescription("Game console");
        product.setListPrice(new BigDecimal(350.00));
        product.setUnitCost(new BigDecimal(200.00));

        productDao.createProduct(product);

        Product product1 = new Product();
        product1.setProductName("Playstation2");
        product1.setProductDescription("Game console");
        product1.setListPrice(new BigDecimal(350.00));
        product1.setUnitCost(new BigDecimal(250.00));

        productDao.createProduct(product1);

        List<Product> productList = productDao.getAllProducts();
        assertEquals(2, productList.size());
    }
}
