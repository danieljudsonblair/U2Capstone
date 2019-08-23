package com.company.retailapiservice.service;

import com.company.retailapiservice.exception.NotFoundException;
import com.company.retailapiservice.model.Inventory;
import com.company.retailapiservice.model.InvoiceItem;
import com.company.retailapiservice.model.InvoiceView;
import com.company.retailapiservice.model.Product;
import com.company.retailapiservice.util.feign.InventoryServiceClient;
import com.company.retailapiservice.util.feign.InvoiceServiceClient;
import com.company.retailapiservice.util.feign.LevelUpServiceClient;
import com.company.retailapiservice.util.feign.ProductServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceLayer {
    InvoiceServiceClient invoiceClient;
    ProductServiceClient productClient;
    InventoryServiceClient inventoryClient;
    LevelUpServiceClient levelUpClient;

    @Autowired
    public ServiceLayer(InvoiceServiceClient invoiceClient, ProductServiceClient productClient, InventoryServiceClient inventoryClient, LevelUpServiceClient levelUpClent){
        this.invoiceClient = invoiceClient;
        this.productClient = productClient;
        this.inventoryClient = inventoryClient;
        this.levelUpClient = levelUpClent;
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
        return productClient.fetchProduct(productId);
    }

    public List<Product> getProductsInInventory(){
        List<Product> inventoryProducts = new ArrayList<>();
        List<Inventory> allInventories = inventoryClient.fetchAllInventories();
        for (Inventory inventory: allInventories){
            if(inventory.getQuantity()>0){
                inventoryProducts.add(productClient.fetchProduct(inventory.getProductId()));
            }
        }return inventoryProducts;

    }

    public List<Product> getProductsByInvoiceId(int invoiceId){
       List<Product> invoiceProducts = new ArrayList<>();
        InvoiceView invoiceView = invoiceClient.fetchInvoicesById(invoiceId);
        List<InvoiceItem> itemList = invoiceView.getInvoiceItemList();
        for (InvoiceItem invoiceItem: itemList){
            int productId = inventoryClient.fetchInventoryById(invoiceItem.getInventoryId()).getProductId();
            invoiceProducts.add(productClient.fetchProduct(productId));
        } return invoiceProducts;

    }

    public int getLevelUpPointsByCustomerId(int customerId){
        return levelUpClient.fetchLevelUpPointByCustomerId(customerId);
    }



}
