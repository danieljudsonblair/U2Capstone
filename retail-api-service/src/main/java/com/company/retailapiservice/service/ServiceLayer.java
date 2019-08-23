package com.company.retailapiservice.service;

import com.company.retailapiservice.exception.NotFoundException;
import com.company.retailapiservice.model.*;
import com.company.retailapiservice.util.feign.*;
import com.company.retailapiservice.viewModel.InventoryView;
import com.company.retailapiservice.viewModel.ProductView;
import com.company.retailapiservice.viewModel.PurchaseReturnViewModel;
import com.company.retailapiservice.viewModel.PurchaseSendViewModel;
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

    private BigDecimal min = new BigDecimal("0");
    private BigDecimal max = new BigDecimal("99999.99");

    private String emptyField(String fieldName) {
        return "Field name " + fieldName + " cannot be an empty string";
    }

    private String notFound(String objectName, int id) {
        return "No " + objectName + " exists @ ID " + id;
    }

    public InvoiceView createInvoice(InvoiceView invoiceView){
        return invoiceClient.createInvoice(invoiceView);
    }

    public List<InvoiceView> getAllInvoices(){
        return invoiceClient.fetchAllInvoices();
    }

    public InvoiceView getInvoiceByInvoiceId(int invoiceId){
        if (invoiceClient.fetchInvoicesById(invoiceId) == null)
            throw new NotFoundException();
    return invoiceClient.fetchInvoicesById(invoiceId);
    }

    public List<InvoiceView> getInvoicesByCustomerId(int customerId) {
//        List<InvoiceView> customerInvoices = new ArrayList<>();
//        List<InvoiceView> allInvoices = invoiceClient.fetchAllInvoices();
//        for (InvoiceView view : allInvoices) {
//            if (view.getCustomerId() == customerId) {
//                customerInvoices.add(view);
//            }
//
//        }return customerInvoices;
        if (invoiceClient.fetchInvoicesByCustomerId(customerId) == null)
            throw new NotFoundException();
        return invoiceClient.fetchInvoicesByCustomerId(customerId);
    }

    public Product getProductByProductId(int productId){
        return productClient.getProductById(productId);
    }

    public List<Product> getProductsInInventory(){
        List<Product> inventoryProducts = new ArrayList<>();
        List<Inventory> allInventories = inventoryClient.getAllInventorys();
        for (Inventory inventory: allInventories){
            if(inventory.getQuantity()>0){
                inventoryProducts.add(productClient.getProductById(inventory.getProductId()));
            }
        }return inventoryProducts;

    }

    public List<Product> getProductsByInvoiceId(int invoiceId){
       List<Product> invoiceProducts = new ArrayList<>();
        InvoiceView invoiceView = invoiceClient.fetchInvoicesById(invoiceId);
        List<InvoiceItem> itemList = invoiceView.getInvoiceItemList();
        for (InvoiceItem invoiceItem: itemList){
            int productId = inventoryClient.getInventoryById(invoiceItem.getInventoryId()).getProductId();
            invoiceProducts.add(productClient.getProductById(productId));
        } return invoiceProducts;

    }

    public List<LevelUp> getLevelUpPointsByCustomerId(int customerId){
        return levelUpClient.getLevelUpsByCustomerId(customerId);
    }

    public List<Inventory> getAllInventories(){
        return inventoryClient.getAllInventorys();
    }

    public Inventory getInventoryById(int inventoryId){
        return inventoryClient.getInventoryById(inventoryId);
    }

    private PurchaseReturnViewModel buildPurchaseReturnViewModel(PurchaseSendViewModel psvm) {
        PurchaseReturnViewModel prvm = new PurchaseReturnViewModel();
        List<ProductView> pvList = new ArrayList<>();
        List<InvoiceItem> iiList = new ArrayList<>();
        List<InventoryView> ivList;
        BigDecimal invoiceTotalPrice = new BigDecimal("0");
        Inventory clientInventory;
        Product clientProduct;
        Inventory updatedInventory = new Inventory();


        if (psvm.getCustomer() == null) {
            Customer customer = customerClient.getCustomer(psvm.getCustomerId());
            if (customer == null)
                throw new NotFoundException(notFound("customer", psvm.getCustomerId()));
            prvm.setCustomer(customer);
        } else if (psvm.getCustomer() != null && psvm.getCustomerId() == 0) {
            prvm.setCustomer(customerClient.createCustomer(psvm.getCustomer()));
        }

        ivList = psvm.getInventoryList();
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

        for (ProductView p : pvList) {
            invoiceTotalPrice = invoiceTotalPrice.add(p.getProductTotal());
        }
        prvm.setTotalPrice(invoiceTotalPrice);
        List<LevelUp> clientLevelUpList = new ArrayList<>();
        LevelUp newLevelUp = new LevelUp();
        LevelUp nextLevelUp = new LevelUp();
        boolean newCustomer = false;
        int levelUp = invoiceTotalPrice.divide(new BigDecimal("50")).setScale(1, BigDecimal.ROUND_FLOOR).intValue() * 10;

        try {
            clientLevelUpList = levelUpClient.getLevelUpsByCustomerId(prvm.getCustomer().getCustomerId());
        } catch (Exception e) {
            newLevelUp.setMemberDate(psvm.getPurchaseDate());
            newCustomer = true;

        }
        if (!newCustomer && clientLevelUpList.get(0).getPoints() == -1) {
            newLevelUp.setMemberDate(LocalDate.of(1999, 9, 9));
        } else if (!newCustomer && clientLevelUpList.get(0).getPoints() != 1) {
            newLevelUp.setMemberDate(clientLevelUpList.get(0).getMemberDate());
        }
        newLevelUp.setPoints(levelUp);
        newLevelUp.setCustomerId(prvm.getCustomer().getCustomerId());
        levelUpProducer.createLevelUp(newLevelUp);

        prvm.setLvlUpPtsThisPurchase(levelUp);

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

        return prvm;
    }

}
