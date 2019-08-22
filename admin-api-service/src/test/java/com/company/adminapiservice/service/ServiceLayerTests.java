package com.company.adminapiservice.service;

import com.company.adminapiservice.model.*;
import com.company.adminapiservice.utils.feign.*;
import com.company.adminapiservice.viewModel.InventoryView;
import com.company.adminapiservice.viewModel.ProductView;
import com.company.adminapiservice.viewModel.PurchaseReturnViewModel;
import com.company.adminapiservice.viewModel.PurchaseSendViewModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ServiceLayerTests {

    ServiceLayer service;
    CustomerClient customerClient;
    InventoryClient inventoryClient;
    InvoiceClient invoiceClient;
    LevelUpClient levelUpClient;
    ProductClient productClient;

    @Before
    public void setUp() throws Exception {

        setUpCustomerClientMock();
        setUpInventoryClientMock();
        setUpInvoiceClientMock();
        setUpLevelUpClientMock();
        setUpProductClientMock();

        service = new ServiceLayer(customerClient, inventoryClient, invoiceClient, levelUpClient, productClient);
    }

    public void setUpCustomerClientMock() {
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
        doReturn(customer).when(customerClient).fetchCustomer(1);
        doReturn(cList).when(customerClient).fetchAllCustomers();
    }

    public void setUpInventoryClientMock() {
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

        doReturn(inventory).when(inventoryClient).createInventory(inventory1);
        doReturn(inventory).when(inventoryClient).getInventory(1);
        doReturn(iList).when(inventoryClient).getAllInventory();
    }

    public void setUpInvoiceClientMock() {
        invoiceClient = mock(InvoiceClient.class);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(1);
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(10);
        invoiceItem.setUnitPrice(new BigDecimal("9.99"));

        List<InvoiceItem> iiList = new ArrayList<>();
        iiList.add(invoiceItem);

        InvoiceView invoiceView = new InvoiceView();
        invoiceView.setInvoiceId(1);
        invoiceView.setCustomerId(1);
        invoiceView.setPurchaseDate(LocalDate.of(2019,8,8));
        invoiceView.setInvoiceItemList(iiList);

        InvoiceView invoiceView1 = new InvoiceView();
        invoiceView1.setCustomerId(1);
        invoiceView1.setPurchaseDate(LocalDate.of(2019,8,8));
        invoiceView1.setInvoiceItemList(iiList);

        List<InvoiceView> ivList = new ArrayList<>();
        ivList.add(invoiceView);

        doReturn(invoiceView).when(invoiceClient).createInvoice(invoiceView1);
        doReturn(invoiceView).when(invoiceClient).getInvoiceById(1);
        doReturn(ivList).when(invoiceClient).getAllInvoices();
    }

    public void setUpLevelUpClientMock() {
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

        doReturn(levelUp).when(levelUpClient).createLevelUp(levelUp1);
        doReturn(levelUp).when(levelUpClient).getLevelUp(1);
        doReturn(lUpList).when(levelUpClient).getAllLevelUps();
    }

    public void setUpProductClientMock() {
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

        doReturn(product).when(productClient).createProduct(product1);
        doReturn(product).when(productClient).getProduct(1);
        doReturn(pList).when(productClient).getAllProducts();
    }

    @Test
    public void saveFetchFetchAllCustomers() {
        Customer customer = new Customer();
        customer.setFirstName("first");
        customer.setLastName("last");
        customer.setStreet("street");
        customer.setCity("city");
        customer.setZip("12345");
        customer.setEmail("firstlast@company.com");
        customer.setPhone("123-456-7891");

        Customer customer1 = new Customer();
        customer1.setCustomerId(1);
        customer1.setFirstName("first");
        customer1.setLastName("last");
        customer1.setStreet("street");
        customer1.setCity("city");
        customer1.setZip("12345");
        customer1.setEmail("firstlast@company.com");
        customer1.setPhone("123-456-7891");

        assertEquals(customer1, service.saveCustomer(customer));
        assertEquals(customer1, service.fetchCustomer(customer1.getCustomerId()));
        assertEquals(service.fetchAllCustomers().size(), 1);

    }

    @Test
    public void saveFetchFetchAllInventory() {
        Inventory inventory = new Inventory();
        inventory.setProductId(1);
        inventory.setQuantity(10);

        Inventory inventory1 = new Inventory();
        inventory1.setInventoryId(1);
        inventory1.setProductId(1);
        inventory1.setQuantity(10);

        assertEquals(inventory1, service.saveInventory(inventory));
        assertEquals(inventory1, service.fetchInventory(inventory1.getInventoryId()));
        assertEquals(service.fetchAllInventory().size(), 1);
    }

    @Test
    public void saveFetchFetchInInventoryFetchAllProducts() {
        Product product = new Product();
        product.setProductName("name");
        product.setProductDescription("description");
        product.setListPrice(new BigDecimal("19.99"));
        product.setUnitCost(new BigDecimal("9.99"));

        Product product1 = new Product();
        product1.setProductId(1);
        product1.setProductName("name");
        product1.setProductDescription("description");
        product1.setListPrice(new BigDecimal("19.99"));
        product1.setUnitCost(new BigDecimal("9.99"));

        assertEquals(product1, service.saveProduct(product));
        assertEquals(product1, service.fetchProduct(product1.getProductId()));
        assertEquals(service.fetchAllProducts().size(), 1);
        assertEquals(service.fetchProductsInInventory().size(), 1);
        assertEquals(service.fetchProductsInInventory().get(0), product1);
//        assertEquals(product1, service.fetchProductsByInvoiceId(1).get(0));
//        assertEquals(service.fetchProductsByInvoiceId(1).size(), 1);
    }

    @Test
    public void saveFetchFetchAllLevelUps() {
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(2019,8,8));

        LevelUp levelUp1 = new LevelUp();
        levelUp1.setLevelUpId(1);
        levelUp1.setCustomerId(1);
        levelUp1.setPoints(10);
        levelUp1.setMemberDate(LocalDate.of(2019,8,8));

        assertEquals(levelUp1, service.saveLevelUp(levelUp));
        assertEquals(levelUp1, service.fetchLevelUp(levelUp1.getLevelUpId()));
        assertEquals(service.fetchAllLevelUps().size(), 1);
    }

    @Test
    public void savePurchase() {
        PurchaseSendViewModel psvm = new PurchaseSendViewModel();
        psvm.setCustomerId(1);

        InventoryView inventory = new Inventory();
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

        prvm.setInvoice(invoiceClient.getInvoiceById(1));
        prvm.setCustomer(customerClient.fetchCustomer(prvm.getInvoice().getCustomerId()));
        prvm.setProductList(pList);
        prvm.setTotalPrice(new BigDecimal("19.99"));
        prvm.setLvlUpPtsAfterPurchase(10);
        prvm.setLvlUpPtsBeforePurchase(10);

        assertEquals(prvm, service.saveInvoice(psvm));
    }

    @Test
    public void updateCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("updated name");
        customer.setCustomerId(1);

        service.updateCustomer(customer);

        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerClient).updateCustomer(customerCaptor.capture());
        assertEquals(customer.getFirstName(), customerCaptor.getValue().getFirstName());
    }

    @Test
    public void updateInventory() {
        Inventory inventory = new Inventory();
        inventory.setQuantity(1);
        inventory.setProductId(1);
        inventory.setInventoryId(1);

        service.updateInventory(inventory);

        ArgumentCaptor<Inventory> inventoryCaptor = ArgumentCaptor.forClass(Inventory.class);
        verify(inventoryClient).updateInventory(inventoryCaptor.capture());
        assertEquals(inventory.getQuantity(), inventoryCaptor.getValue().getQuantity());
    }

    @Test
    public void updateProduct() {
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("updated name");
        product.setProductDescription("updated description");
        product.setUnitCost(new BigDecimal("4.99"));
        product.setListPrice(new BigDecimal("14.99"));

        service.updateProduct(product);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productClient).updateProduct(productCaptor.capture());
        assertEquals(product.getProductName(), productCaptor.getValue().getProductName());
    }

    @Test
    public void updateLevelUp() {
        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(1);
        levelUp.setCustomerId(1);
        levelUp.setPoints(20);
        levelUp.setMemberDate(LocalDate.of(2019, 8, 8));

        service.updateLevelUp(levelUp);

        ArgumentCaptor<LevelUp> levelUpCaptor = ArgumentCaptor.forClass(LevelUp.class);
        verify(levelUpClient).updateLevelUp(levelUpCaptor.capture());
        assertEquals(levelUp.getPoints(), levelUpCaptor.getValue().getPoints());
    }

    @Test
    public void deleteCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(1);

        service.deleteCustomer(customer.getCustomerId());

        ArgumentCaptor<Integer> customerCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(customerClient).deleteCustomer(customerCaptor.capture());
        assertEquals(customer.getCustomerId(), customerCaptor.getValue().intValue());
    }

    @Test
    public void deleteInventory() {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1);

        service.deleteInventory(inventory.getInventoryId());

        ArgumentCaptor<Integer> inventoryCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(inventoryClient).deleteInventory(inventoryCaptor.capture());
        assertEquals(inventory.getInventoryId(), inventoryCaptor.getValue().intValue());
    }

    @Test
    public void deleteProduct() {
        Product product = new Product();
        product.setProductId(1);

        service.deleteProduct(product.getProductId());

        ArgumentCaptor<Integer> productCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(productClient).deleteProduct(productCaptor.capture());
        assertEquals(product.getProductId(), productCaptor.getValue().intValue());
    }

    @Test
    public void deleteLevelUp() {
        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(1);

        service.deleteLevelUp(levelUp.getLevelUpId());

        ArgumentCaptor<Integer> levelUpCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(levelUpClient).deleteLevelUp(levelUpCaptor.capture());
        assertEquals(levelUp.getLevelUpId(), levelUpCaptor.getValue().intValue());
    }
}
