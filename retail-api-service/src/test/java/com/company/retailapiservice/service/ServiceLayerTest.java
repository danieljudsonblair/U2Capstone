package com.company.retailapiservice.service;

import com.company.retailapiservice.model.*;
import com.company.retailapiservice.util.feign.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ServiceLayerTest {

    ServiceLayer service;
    InvoiceClient invoiceClient;
    ProductClient productClient;
    InventoryClient inventoryClient;
    LevelUpClient levelUpClient;
    LevelUpProducer levelUpProducer;
    CustomerClient customerClient;

    @Before
    public void setUp() throws Exception{
        setUpInventoryServiceClientMock();
        setUpInvoiceServiceClientMock();
        setUpLevelUpServiceClientMock();
        setUpProductServiceClientMock();

        service = new ServiceLayer(customerClient, invoiceClient, productClient, inventoryClient,levelUpClient, levelUpProducer);
    }

    public void setUpInvoiceServiceClientMock(){
        invoiceClient = mock(InvoiceClient.class);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(1);
        invoiceItem.setUnitPrice(new BigDecimal("19.99"));

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceItemId(1);
        invoiceItem1.setInvoiceId(1);
        invoiceItem1.setInventoryId(1);
        invoiceItem1.setQuantity(1);
        invoiceItem1.setUnitPrice(new BigDecimal("19.99"));

        List<InvoiceItem> iiList = new ArrayList<>();
        iiList.add(invoiceItem);

        List<InvoiceItem> iiList1 = new ArrayList<>();
        iiList1.add(invoiceItem1);

        InvoiceView invoiceView = new InvoiceView();
        invoiceView.setInvoiceId(1);
        invoiceView.setCustomerId(1);
        invoiceView.setPurchaseDate(LocalDate.of(2019,8,8));
        invoiceView.setInvoiceItemList(iiList1);

        InvoiceView invoiceView1 = new InvoiceView();
        invoiceView1.setCustomerId(1);
        invoiceView1.setPurchaseDate(LocalDate.of(2019,8,8));
        invoiceView1.setInvoiceItemList(iiList);

        List<InvoiceView> ivList = new ArrayList<>();
        ivList.add(invoiceView);

        doReturn(invoiceView).when(invoiceClient).createInvoice(invoiceView1);
        doReturn(invoiceView).when(invoiceClient).fetchInvoicesById(1);
        doReturn(ivList).when(invoiceClient).fetchAllInvoices();
        doReturn(ivList).when(invoiceClient).fetchInvoicesByCustomerId(1);
    }

    public void setUpInventoryServiceClientMock(){
        inventoryClient = mock(InventoryClient.class);

        Inventory inventory = new Inventory();
        inventory.setInventoryId(1);
        inventory.setProductId(1);
        inventory.setQuantity(10);

        Inventory inventory1 = new Inventory();
        inventory1.setProductId(1);
        inventory1.setQuantity(10);

        List<Inventory> iList = new ArrayList<>();
        iList.add(inventory);

//        doReturn(inventory).when(inventoryClient).createInventory(inventory1);
        doReturn(inventory).when(inventoryClient).getInventoryById(1);
        doReturn(iList).when(inventoryClient).getAllInventorys();

    }

    public void setUpProductServiceClientMock(){
        productClient = mock(ProductClient.class);

        Product product = new Product();
        product.setProductId(1);
        product.setProductName("name");
        product.setProductDescription("description");
        product.setListPrice(new BigDecimal("19.99"));
        product.setUnitCost(new BigDecimal("9.99"));

        Product product1 = new Product();
        product1.setProductName("name");
        product1.setProductDescription("description");
        product1.setListPrice(new BigDecimal("19.99"));
        product1.setUnitCost(new BigDecimal("9.99"));

        List<Product> pList = new ArrayList<>();
        pList.add(product);

        doReturn(product).when(productClient).getProductById(1);
//        doReturn(product).when(productClient).fetchProduct(1);
        doReturn(pList).when(productClient).getAllProducts();
    }

    public void setUpLevelUpServiceClientMock(){
        levelUpClient = mock(LevelUpClient.class);

        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(1);
        levelUp.setCustomerId(1);
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(2019, 8,8));

        LevelUp levelUp1 = new LevelUp();
        levelUp1.setCustomerId(1);
        levelUp1.setPoints(10);
        levelUp1.setMemberDate(LocalDate.of(2019, 8,8));

        List<LevelUp> lUpList = new ArrayList<>();
        lUpList.add(levelUp);

//        doReturn(levelUp).when(levelUpClient).createLevelUp(levelUp1);
//        doReturn(levelUp).when(levelUpClient).getLevelUp(1);
        doReturn(lUpList).when(levelUpClient).getAllLevelUps();
        doReturn(lUpList).when(levelUpClient).getLevelUpsByCustomerId(1);

    }

    @Test
    public void createGetGetAllGetByCustomerIdInvoice(){
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(1);
        invoiceItem.setUnitPrice(new BigDecimal("19.99"));

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceItemId(1);
        invoiceItem1.setInvoiceId(1);
        invoiceItem1.setInventoryId(1);
        invoiceItem1.setQuantity(1);
        invoiceItem1.setUnitPrice(new BigDecimal("19.99"));

        List<InvoiceItem> iiList = new ArrayList<>();
        iiList.add(invoiceItem);

        List<InvoiceItem> iiList1 = new ArrayList<>();
        iiList1.add(invoiceItem1);

        InvoiceView invoiceView = new InvoiceView();
        invoiceView.setInvoiceId(1);
        invoiceView.setCustomerId(1);
        invoiceView.setPurchaseDate(LocalDate.of(2019,8,8));
        invoiceView.setInvoiceItemList(iiList1);

        InvoiceView invoiceView1 = new InvoiceView();
        invoiceView1.setCustomerId(1);
        invoiceView1.setPurchaseDate(LocalDate.of(2019,8,8));
        invoiceView1.setInvoiceItemList(iiList);

        List<InvoiceView> ivList = new ArrayList<>();
        ivList.add(invoiceView);

        assertEquals(invoiceView1, service.createInvoice(invoiceView));
        assertEquals(invoiceView1, service.getInvoiceByInvoiceId(invoiceView1.getInvoiceId()));
        assertEquals(service.getAllInvoices().size(), 1);
        assertEquals(service.getInvoicesByCustomerId(1).size(), 1);

    }

    @Test
    public void getProductByIdGetByInvoiceIdGetProductsInInventory(){
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("name");
        product.setProductDescription("description");
        product.setListPrice(new BigDecimal("19.99"));
        product.setUnitCost(new BigDecimal("9.99"));

        Product product1 = new Product();
        product1.setProductName("name");
        product1.setProductDescription("description");
        product1.setListPrice(new BigDecimal("19.99"));
        product1.setUnitCost(new BigDecimal("9.99"));

        List<Product> pList = new ArrayList<>();
        pList.add(product);

        assertEquals(product, service.getProductByProductId(1));
        assertEquals( service.getProductsByInvoiceId(1).size(), 1);
        assertEquals(service.getProductsInInventory().size(), 1);
    }

    @Test
    public void getLevelUpPointByCustomerId(){
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(2019,8,8));

        LevelUp levelUp1 = new LevelUp();
        levelUp1.setLevelUpId(1);
        levelUp1.setCustomerId(1);
        levelUp1.setPoints(10);
        levelUp1.setMemberDate(LocalDate.of(2019,8,8));

//        assertEquals(levelUp1, service.saveLevelUp(levelUp));
//        assertEquals(levelUp1, service.fetchLevelUp(levelUp1.getLevelUpId()));
//        assertEquals(service.fetchAllLevelUps().size(), 1);
//        assertEquals(service.fetchLevelUpByCustomerId(1).size(), 1);
        assertEquals(service.getLevelUpPointsByCustomerId(1), 10);
    }



}

