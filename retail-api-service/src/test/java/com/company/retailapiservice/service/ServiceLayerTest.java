package com.company.retailapiservice.service;

import com.company.retailapiservice.model.*;
import com.company.retailapiservice.util.feign.*;
import com.company.retailapiservice.viewModel.InventoryView;
import com.company.retailapiservice.viewModel.ProductView;
import com.company.retailapiservice.viewModel.PurchaseReturnViewModel;
import com.company.retailapiservice.viewModel.PurchaseSendViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ServiceLayerTest {

    ServiceLayer service;
    InvoiceClient invoiceClient;
    ProductClient productClient;
    InventoryClient inventoryClient;
    LevelUpClient levelUpClient;
    LevelUpProducer levelUpProducer;
    CustomerClient customerClient;

    @Before
    public void setUp() throws Exception {
        setUpInventoryServiceClientMock();
        setUpInvoiceServiceClientMock();
        setUpLevelUpServiceClientMock();
        setUpProductServiceClientMock();
        setUpCustomerServiceClientMock();
        setUpLevelUpProducerMock();

        service = new ServiceLayer(customerClient, invoiceClient, productClient, inventoryClient, levelUpClient, levelUpProducer);
    }

    public void setUpCustomerServiceClientMock() {
        customerClient = mock(CustomerClient.class);

        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("first");
        customer.setLastName("last");
        customer.setStreet("street");
        customer.setCity("city");
        customer.setZip("12345");
        customer.setEmail("firstlast@company.com");
        customer.setPhone("123-456-7891");

        Customer customer1 = new Customer();
        customer1.setFirstName("first");
        customer1.setLastName("last");
        customer1.setStreet("street");
        customer1.setCity("city");
        customer1.setZip("12345");
        customer1.setEmail("firstlast@company.com");
        customer1.setPhone("123-456-7891");

        List<Customer> cList = new ArrayList<>();
        cList.add(customer);

        doReturn(customer).when(customerClient).createCustomer(customer1);
        doReturn(customer).when(customerClient).getCustomer(1);
        doReturn(cList).when(customerClient).getAllCustomers();
    }

    public void setUpInvoiceServiceClientMock() {
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
        invoiceView.setPurchaseDate(LocalDate.of(2019, 8, 8));
        invoiceView.setInvoiceItemList(iiList1);

        InvoiceView invoiceView1 = new InvoiceView();
        invoiceView1.setCustomerId(1);
        invoiceView1.setPurchaseDate(LocalDate.of(2019, 8, 8));
        invoiceView1.setInvoiceItemList(iiList);

        List<InvoiceView> ivList = new ArrayList<>();
        ivList.add(invoiceView);

        doReturn(invoiceView).when(invoiceClient).createInvoice(invoiceView1);
        doReturn(invoiceView).when(invoiceClient).fetchInvoiceById(1);
        doReturn(ivList).when(invoiceClient).fetchAllInvoices();
    }

    public void setUpInventoryServiceClientMock() {
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


        doReturn(inventory).when(inventoryClient).getInventoryById(1);
        doReturn(iList).when(inventoryClient).getAllInventorys();

    }

    public void setUpProductServiceClientMock() {
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
        doReturn(pList).when(productClient).getAllProducts();
    }

    public void setUpLevelUpServiceClientMock() {
        levelUpClient = mock(LevelUpClient.class);

        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(1);
        levelUp.setCustomerId(1);
        levelUp.setPoints(0);
        levelUp.setMemberDate(LocalDate.of(2019, 8, 8));

        LevelUp levelUp1 = new LevelUp();
        levelUp1.setCustomerId(1);
        levelUp1.setPoints(0);
        levelUp1.setMemberDate(LocalDate.of(2019, 8, 8));

        List<LevelUp> lUpList = new ArrayList<>();
        lUpList.add(levelUp);

        doReturn(lUpList).when(levelUpClient).getAllLevelUps();
        doReturn(lUpList).when(levelUpClient).getLevelUpsByCustomerId(1);

    }

    public void setUpLevelUpProducerMock() {
        levelUpProducer = mock(LevelUpProducer.class);

        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(0);
        levelUp.setMemberDate(LocalDate.of(2019,8,8));

        doReturn(LevelUpProducer.SAVE_QUEUED_MSG).when(levelUpProducer).createLevelUp(levelUp);
    }

    @Test
    public void savePurchase() {
        PurchaseSendViewModel psvm = new PurchaseSendViewModel();
        psvm.setCustomerId(1);
        psvm.setPurchaseDate(LocalDate.of(2019, 8, 8));

        InventoryView inventory = new InventoryView();
        inventory.setInventoryId(1);
        inventory.setQuantity(1);

        ProductView product = new ProductView();
        product.setProductName("name");
        product.setProductDescription("description");
        product.setListPrice(new BigDecimal("19.99"));
        product.setProductTotal(new BigDecimal("19.99"));
        product.setQuantity(new BigDecimal("1"));

        List<InventoryView> iList = new ArrayList<>();
        List<ProductView> pList = new ArrayList<>();

        iList.add(inventory);
        pList.add(product);

        psvm.setInventoryList(iList);

        PurchaseReturnViewModel prvm = new PurchaseReturnViewModel();
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019, 8, 8));

        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("first");
        customer.setLastName("last");
        customer.setStreet("street");
        customer.setCity("city");
        customer.setZip("12345");
        customer.setEmail("firstlast@company.com");
        customer.setPhone("123-456-7891");

        prvm.setInvoice(invoice);
        prvm.setCustomer(customer);
        prvm.setProductList(pList);
        prvm.setTotalPrice(new BigDecimal("19.99"));
        prvm.setLvlUpPtsThisPurchase(0);
        prvm.setTotalLvlUpPts("0");

        PurchaseReturnViewModel prvm1 = service.savePurchase(psvm);

        assertEquals(prvm, prvm1);
    }

    @Test
    public void getProductByIdGetProductsInInventoryGetProdcutsByInvoiceId() {
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
        assertEquals(service.getProductsInInventory().size(), 1);
        assertEquals(service.getProductsInInventory().get(0), product);
        assertEquals(service.getProductsByInvoiceId(1).size(), 1);
        assertEquals(service.getProductsByInvoiceId(1).get(0), product);
    }

    @Test
    public void getInvoiceGetAllInvoicesGetInvoicesByCustomerId() {
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
        invoiceView.setPurchaseDate(LocalDate.of(2019, 8, 8));
        invoiceView.setInvoiceItemList(iiList1);

        InvoiceView invoiceView1 = new InvoiceView();
        invoiceView1.setCustomerId(1);
        invoiceView1.setPurchaseDate(LocalDate.of(2019, 8, 8));
        invoiceView1.setInvoiceItemList(iiList);

        List<InvoiceView> ivList = new ArrayList<>();
        ivList.add(invoiceView);

        assertEquals(service.getInvoice(1), invoiceView);
        assertEquals(service.getAllInvoices().size(), 1);
        assertEquals(service.getAllInvoices().get(0), invoiceView);
        assertEquals(service.getInvoicesByCustomerId(1).size(), 1);
        assertEquals(service.getInvoicesByCustomerId(1).get(0), invoiceView);
    }

    @Test
    public void getLevelUpsByCustomerId() {
        assertEquals(service.getLevelUpByCustomerId(1), 0);
    }
}

