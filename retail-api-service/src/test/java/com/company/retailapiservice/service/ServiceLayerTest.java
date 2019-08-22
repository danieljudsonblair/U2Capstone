package com.company.retailapiservice.service;

import com.company.retailapiservice.model.*;
import com.company.retailapiservice.util.feign.InventoryServiceClient;
import com.company.retailapiservice.util.feign.InvoiceServiceClient;
import com.company.retailapiservice.util.feign.LevelUpServiceClient;
import com.company.retailapiservice.util.feign.ProductServiceClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class ServiceLayerTest {

    ServiceLayer service;
    InvoiceServiceClient invoiceClient;
    ProductServiceClient productClient;
    InventoryServiceClient inventoryClient;
    LevelUpServiceClient levelUpClient;

    @Before
    public void setUp() throws Exception{

    }

    public void setUpInvoiceServiceClientMock(){
        invoiceClient = mock(InvoiceServiceClient.class);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(1);
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(20);
        invoiceItem.setUnitPrice(new BigDecimal(125.99));

        List<InvoiceItem> itemList = new ArrayList<>();
        itemList.add(invoiceItem);

        InvoiceView invoiceView = new InvoiceView();
        invoiceView.setInvoiceId(1);
        invoiceView.setCustomerId(1);
        invoiceView.setPurchaseDate(LocalDate.of(2019,1,1));
        invoiceView.setInvoiceItemList(itemList);
    }

    public void setUpInventoryServiceClientMock(){
        inventoryClient = mock(InventoryServiceClient.class);

        Inventory inventory = new Inventory();
        inventory.setInventoryId(1);
        inventory.setProductId(1);
        inventory.setQuantity(25);
    }

    public void setUpProductServiceClientMock(){
        productClient = mock(ProductServiceClient.class);

        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Playstation");
        product.setProductDescription("Game console");
        product.setListPrice(new BigDecimal(125.99));
        product.setUnitCost(new BigDecimal(100.99));
    }

    public void setUpLevelUpServiceClientMock(){
        levelUpClient = mock(LevelUpServiceClient.class);

        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(1);
        levelUp.setCustomerId(1);
        levelUp.setMemberDate(LocalDate.of(2019,1,1));
        levelUp.setPoints(10);

    }
}
