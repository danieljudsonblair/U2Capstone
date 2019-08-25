package com.company.retailapiservice.service;

import com.company.retailapiservice.exception.NotFoundException;
import com.company.retailapiservice.model.*;
import com.company.retailapiservice.util.feign.*;
import com.company.retailapiservice.viewModel.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceLayer {
    CustomerClient customerClient;
    InvoiceClient invoiceClient;
    ProductClient productClient;
    InventoryClient inventoryClient;
    LevelUpClient levelUpClient;
    LevelUpProducer levelUpProducer;

    @Autowired
    public ServiceLayer(CustomerClient customerClient, InvoiceClient invoiceClient, ProductClient productClient, InventoryClient inventoryClient, LevelUpClient levelUpClient, LevelUpProducer levelUpProducer) {
        this.customerClient = customerClient;
        this.invoiceClient = invoiceClient;
        this.productClient = productClient;
        this.inventoryClient = inventoryClient;
        this.levelUpClient = levelUpClient;
        this.levelUpProducer = levelUpProducer;
    }

    private String notFound(String objectName, int id) {
        return "No " + objectName + " exists @ ID " + id;
    }

    public PurchaseReturnViewModel savePurchase(PurchaseSendViewModel psvm) {
        return buildPurchaseReturnViewModel(psvm);
    }

    public Product getProductByProductId(int productId){
        return productClient.getProductById(productId);
    }

    public List<Product> getProductsInInventory(){
        List<Product> inventoryProducts = new ArrayList<>();
        inventoryClient.getAllInventorys().stream().forEach(i -> {
            if (i.getQuantity() > 0)
                inventoryProducts.add(productClient.getProductById(i.getProductId()));
        });
        return inventoryProducts;
    }

    public InvoiceView getInvoice(int id) {
        InvoiceView invoice = invoiceClient.fetchInvoiceById(id);
        if (invoice == null)
            throw new NotFoundException(notFound("invoice", id));
        return invoice;
    }

    public List<InvoiceView> getAllInvoices() {
        return invoiceClient.fetchAllInvoices();
    }

    public List<InvoiceView> getInvoicesByCustomerId(int id) {
        List<InvoiceView> ciList = new ArrayList<>();
        List<InvoiceView> iList = invoiceClient.fetchAllInvoices();
        for (InvoiceView iv : iList) {
            if (iv.getCustomerId() == id) {
                ciList.add(iv);
            }
        }
        if (ciList.size() == 0)
            throw new NotFoundException("No invoices could be found for customer id " + id);
        return ciList;
    }

    public List<Product> getProductsByInvoiceId(int id) {
        InvoiceView invoice = invoiceClient.fetchInvoiceById(id);
        List<Product> pList = new ArrayList<>();
        if (invoice == null)
            throw new NotFoundException(notFound("invoice", id));
        invoice.getInvoiceItemList().stream().forEach(invoiceItem ->
            pList.add(productClient.getProductById(
                        inventoryClient.getInventoryById(invoiceItem.getInventoryId()).getProductId())));

        return pList;
    }
/*
 circuit breaker works OK when method is called from controller layer,
 but not when called from levelUpHelper method below
 */
    @HystrixCommand(fallbackMethod = "otherfallback")
    public int getLevelUpByCustomerId(int customer_id) {
        int total = 0;
        List<LevelUp> levelUpList = levelUpClient.getLevelUpsByCustomerId(customer_id);
        for (LevelUp lu : levelUpList) {
            total += lu.getPoints();
        }
        return total;
    }


    // when levelup service is down, this method IS NOT CALLED and an error is thrown.  I have no idea why
    public int otherfallback(int customer_id) {
        return -1;
    }

    private PurchaseReturnViewModel buildPurchaseReturnViewModel(PurchaseSendViewModel psvm) {
        PurchaseReturnViewModel prvm = new PurchaseReturnViewModel();
        List<InvoiceItem> iiList = new ArrayList<>();

        customerHelper(psvm, prvm);
        productViewAndInvoiceItemHelper(psvm, prvm, iiList);
        levelUpHelper(prvm, psvm);
        invoiceHelper(psvm, prvm, iiList);

        return prvm;
    }

    private void customerHelper(PurchaseSendViewModel psvm, PurchaseReturnViewModel prvm) {
        if (psvm.getCustomer() == null) {
            Customer customer = customerClient.getCustomer(psvm.getCustomerId());
            if (customer == null)
                throw new NotFoundException(notFound("customer", psvm.getCustomerId()));
            prvm.setCustomer(customer);
        } else if (psvm.getCustomer() != null && psvm.getCustomerId() == 0) {
            prvm.setCustomer(customerClient.createCustomer(psvm.getCustomer()));
        }
    }

    private void productViewAndInvoiceItemHelper(PurchaseSendViewModel psvm, PurchaseReturnViewModel prvm, List<InvoiceItem> iiList) {
        List<InventoryView> ivList;
        ivList = psvm.getInventoryList();
        Inventory clientInventory;
        Product clientProduct;
        Inventory updatedInventory = new Inventory();
        List<ProductView> pvList = new ArrayList<>();

        for (InventoryView i : ivList) {
            InvoiceItem ii = new InvoiceItem();
            ProductView pv = new ProductView();
            clientInventory = inventoryClient.getInventoryById(i.getInventoryId());
            clientProduct = productClient.getProductById(clientInventory.getProductId());


            if (clientInventory == null)
                throw new NotFoundException(notFound("inventory", i.getInventoryId()));
            if (clientInventory.getQuantity() < i.getQuantity())
                throw new IllegalArgumentException("You must select a lower quantity");

            updatedInventory.setInventoryId(i.getInventoryId());
            updatedInventory.setProductId(clientInventory.getProductId());
            updatedInventory.setQuantity(clientInventory.getQuantity() - i.getQuantity());

            inventoryClient.updateInventory(updatedInventory);
            ii.setInventoryId(i.getInventoryId());

            pv.setListPrice(clientProduct.getListPrice());
            pv.setQuantity(BigDecimal.valueOf(i.getQuantity()));
            pv.setProductTotal(pv.getQuantity().multiply(pv.getListPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
            pv.setProductName(clientProduct.getProductName());
            pv.setProductDescription(clientProduct.getProductDescription());
            ii.setUnitPrice(pv.getListPrice());
            ii.setQuantity(pv.getQuantity().intValue());
            iiList.add(ii);

            pvList.add(pv);
        }

        prvm.setProductList(pvList);
    }

    private void levelUpHelper(PurchaseReturnViewModel prvm, PurchaseSendViewModel psvm) {
        List<LevelUp> clientLevelUpList = new ArrayList<>();
        BigDecimal invoiceTotalPrice = new BigDecimal("0");
        LevelUp newLevelUp = new LevelUp();

        for (ProductView p : prvm.getProductList()) {
            invoiceTotalPrice = invoiceTotalPrice.add(p.getProductTotal());
        }
        prvm.setTotalPrice(invoiceTotalPrice);

        int levelUp = invoiceTotalPrice.divide(new BigDecimal("50")).setScale(1, BigDecimal.ROUND_FLOOR).intValue() * 10;
        int clientLevelUp = getLevelUpByCustomerId(prvm.getCustomer().getCustomerId());
        if (clientLevelUp == -1) {
            prvm.setTotalLvlUpPts("not available");
        } else {
            Integer totalPts = levelUp + clientLevelUp;

            prvm.setTotalLvlUpPts(totalPts.toString());

            newLevelUp.setPoints(levelUp);
            newLevelUp.setCustomerId(prvm.getCustomer().getCustomerId());
            newLevelUp.setMemberDate(psvm.getPurchaseDate());
            levelUpProducer.createLevelUp(newLevelUp);
        }

        prvm.setLvlUpPtsThisPurchase(levelUp);
    }

    private void invoiceHelper(PurchaseSendViewModel psvm, PurchaseReturnViewModel prvm, List<InvoiceItem> iiList) {
        InvoiceView iv = new InvoiceView();
        Invoice invoice = new Invoice();

        iv.setCustomerId(prvm.getCustomer().getCustomerId());
        iv.setPurchaseDate(psvm.getPurchaseDate());
        iv.setInvoiceItemList(iiList);

        iv = invoiceClient.createInvoice(iv);
        invoice.setPurchaseDate(iv.getPurchaseDate());
        invoice.setCustomerId(prvm.getCustomer().getCustomerId());
        invoice.setInvoiceId(iv.getInvoiceId());

        prvm.setInvoice(invoice);
    }
}
