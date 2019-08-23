package com.company.adminapiservice.service;

import com.company.adminapiservice.exception.NotFoundException;
import com.company.adminapiservice.model.*;
import com.company.adminapiservice.utils.feign.*;
import com.company.adminapiservice.viewModel.InventoryView;
import com.company.adminapiservice.viewModel.ProductView;
import com.company.adminapiservice.viewModel.PurchaseReturnViewModel;
import com.company.adminapiservice.viewModel.PurchaseSendViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;git 
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceLayer {

    CustomerClient customerClient;
    InventoryClient inventoryClient;
    InvoiceClient invoiceClient;
    LevelUpClient levelUpClient;
    ProductClient productClient;

    @Autowired
    public ServiceLayer(CustomerClient customerClient, InventoryClient inventoryClient, InvoiceClient invoiceClient, LevelUpClient levelUpClient, ProductClient productClient) {
        this.customerClient = customerClient;
        this.inventoryClient = inventoryClient;
        this.invoiceClient = invoiceClient;
        this.levelUpClient = levelUpClient;
        this.productClient = productClient;
    }

    private BigDecimal min = new BigDecimal("0");
    private BigDecimal max = new BigDecimal("99999.99");

    private String emptyField(String fieldName) {
        return "Field name " + fieldName + " cannot be an empty string";
    }

    private String notFound(String objectName, int id) {
        return "No " + objectName + " exists @ ID " + id;
    }

    public Customer saveCustomer(Customer customer) {
        return customerClient.createCustomer(customer);
    }

    public Inventory saveInventory(Inventory inventory) {
        if (productClient.getProduct(inventory.getProductId()) == null)
            throw new NotFoundException(notFound("product", inventory.getProductId()));
        return inventoryClient.createInventory(inventory);
    }

    public Product saveProduct(Product product) {
        return productClient.createProduct(product);
    }

    public LevelUp saveLevelUp(LevelUp levelUp) {
        return levelUpClient.createLevelUp(levelUp);
    }

    public PurchaseReturnViewModel saveInvoice(PurchaseSendViewModel psvm) {
        return buildPurchaseReturnViewModel(psvm);
    }

    public Customer fetchCustomer(int customer_id) {
        return customerClient.fetchCustomer(customer_id);
    }

    public Inventory fetchInventory(int inventory_id) {
        return inventoryClient.getInventory(inventory_id);
    }

    public Product fetchProduct(int product_id) {
        return productClient.getProduct(product_id);
    }

    public LevelUp fetchLevelUp(int levelUp_id) {
        return levelUpClient.getLevelUp(levelUp_id);
    }

    public List<LevelUp> fetchLevelUpByCustomerId(int customer_id) {
        return levelUpClient.getLevelUpByCustomerId(customer_id);
    }

    public List<Product> fetchProductsByInvoiceId(int invoice_id) {
        List<Product> pList = new ArrayList<>();
        invoiceClient.getInvoiceById(invoice_id).getInvoiceItemList().stream().forEach(ii -> {
            pList.add(productClient.getProduct(inventoryClient.getInventory(ii.getInventoryId()).getProductId()));
        });

        return pList;
    }

    public InvoiceView fetchInvoice(int invoice_id) {
        return invoiceClient.getInvoiceById(invoice_id);
    }

    public List<Customer> fetchAllCustomers() {
        return customerClient.fetchAllCustomers();
    }

    public List<Inventory> fetchAllInventory() {
        return inventoryClient.getAllInventory();
    }

    public List<LevelUp> fetchAllLevelUps() {
        return levelUpClient.getAllLevelUps();
    }

    public List<Product> fetchAllProducts() {
        return productClient.getAllProducts();
    }

    public List<InvoiceView> fetchAllInvoices() {
        return invoiceClient.getAllInvoices();
    }

    public List<Product> fetchProductsInInventory() {
        List<Product> pList = new ArrayList<>();
        inventoryClient.getAllInventory().stream().forEach(i -> {
            if (i.getQuantity() > 0)
                pList.add(productClient.getProduct(i.getProductId()));
        });

        return pList;
    }

    public void updateCustomer(Customer customer) {
        Customer uc = customerClient.fetchCustomer(customer.getCustomerId());
        if (uc == null)
            throw new NotFoundException(notFound("customer", customer.getCustomerId()));

        if (customer.getFirstName() != null && customer.getFirstName().equals(""))
            throw new IllegalArgumentException(emptyField("firstName"));
        if (customer.getFirstName() != null)
            uc.setFirstName(customer.getFirstName());
        if (customer.getLastName() != null && customer.getLastName().equals(""))
            throw new IllegalArgumentException(emptyField("lastName"));
        if (customer.getLastName() != null)
            uc.setLastName(customer.getLastName());
        if (customer.getStreet() != null && customer.getStreet().equals(""))
            throw new IllegalArgumentException(emptyField("street"));
        if (customer.getStreet() != null)
            uc.setStreet(customer.getStreet());
        if (customer.getCity() != null && customer.getCity().equals(""))
            throw new IllegalArgumentException(emptyField("city"));
        if (customer.getCity() != null)
            uc.setCity(customer.getCity());
        if (customer.getZip() != null && customer.getZip().equals(""))
            throw new IllegalArgumentException(emptyField("zip"));
        if (customer.getZip() != null)
            uc.setZip(customer.getZip());
        if (customer.getEmail() != null && customer.getEmail().equals(""))
            throw new IllegalArgumentException(emptyField("email"));
        if (customer.getEmail() != null)
            uc.setEmail(customer.getEmail());
        if (customer.getPhone() != null && customer.getPhone().equals(""))
            throw new IllegalArgumentException(emptyField("phone"));
        if (customer.getPhone() != null)
            uc.setPhone(customer.getPhone());

        customerClient.updateCustomer(uc);
    }

    public void updateInventory(Inventory inventory) {
        Inventory ui = inventoryClient.getInventory(inventory.getInventoryId());
        if (ui == null)
            throw new NotFoundException(notFound("inventory", inventory.getInventoryId()));
        if (productClient.getProduct(ui.getProductId()) == null)
            throw new NotFoundException(notFound("product", inventory.getProductId()));
        if (inventory.getQuantity() < 0)
            throw new IllegalArgumentException("Quantity cannot be updated to a value less than 0");
        ui.setQuantity(inventory.getQuantity());

        inventoryClient.updateInventory(ui);
    }

    public void updateLevelUp(LevelUp levelUp) {
        LevelUp ul = levelUpClient.getLevelUp(levelUp.getLevelUpId());
        if (ul == null)
            throw new NotFoundException(notFound("levelUp", levelUp.getLevelUpId()));
        if (customerClient.fetchCustomer(levelUp.getCustomerId()) == null)
            throw new NotFoundException(notFound("customer", levelUp.getCustomerId()));
        if (levelUp.getPoints() < 0)
            throw new IllegalArgumentException("Points cannot be updated to a value less than 0");
        ul.setPoints(levelUp.getPoints());
        if (levelUp.getMemberDate() != null)
            ul.setMemberDate(levelUp.getMemberDate());

        levelUpClient.updateLevelUp(ul);
    }

    public void updateProduct(Product product) {
        Product up = productClient.getProduct(product.getProductId());
        if (up == null)
            throw new NotFoundException(notFound("product", product.getProductId()));
        if (product.getProductName() != null && product.getProductName().equals(""))
            throw new IllegalArgumentException(emptyField("productName"));
        if (product.getProductName() != null)
            up.setProductName(product.getProductName());
        if (product.getProductDescription() != null && product.getProductDescription().equals(""))
            throw new IllegalArgumentException(emptyField("productDescription"));
        if (product.getProductDescription() != null)
            up.setProductDescription(product.getProductDescription());
        if (product.getListPrice() != null && (product.getListPrice().compareTo(min) < 0 ||
                product.getListPrice().compareTo(max) > 0))
            throw new IllegalArgumentException("listPrice must be between " + min + " and " + max);
        if (product.getListPrice() != null)
            up.setListPrice(product.getListPrice().setScale(2, BigDecimal.ROUND_FLOOR));
        if (product.getUnitCost() != null && (product.getUnitCost().compareTo(min) < 0 ||
                product.getUnitCost().compareTo(max) > 0))
            throw new IllegalArgumentException("unitCost must be between " + min + " and " + max);
        if (product.getUnitCost() != null)
            up.setUnitCost(product.getUnitCost().setScale(2, BigDecimal.ROUND_FLOOR));

        productClient.updateProduct(up);
    }

    public void updateInvoice(InvoiceView invoiceView) {
        InvoiceView ui = invoiceClient.getInvoiceById(invoiceView.getInvoiceId());

        if (ui == null)
            throw new NotFoundException(notFound("invoice", invoiceView.getInvoiceId()));
        if (invoiceView.getCustomerId() == 0)
            throw new NotFoundException(notFound("custoer", invoiceView.getCustomerId()));
        if (invoiceView.getPurchaseDate() != null)
            ui.setPurchaseDate(invoiceView.getPurchaseDate());
        if (invoiceView.getInvoiceItemList() != null && invoiceView.getInvoiceItemList().size() == 0)
            throw new IllegalArgumentException("invoiceItem list cannot be an empty list");
        if (invoiceView.getInvoiceItemList() != null)
            ui.setInvoiceItemList(invoiceView.getInvoiceItemList());

        invoiceClient.updateInvoice(ui);
    }

    public void deleteCustomer(int customer_id) {
        if (customerClient.fetchCustomer(customer_id) == null)
            throw new NotFoundException(notFound("customer", customer_id));

        customerClient.deleteCustomer(customer_id);
    }

    public void deleteInventory(int inventory_id) {
        if (inventoryClient.getInventory(inventory_id) == null)
            throw new NotFoundException(notFound("inventory", inventory_id));

        inventoryClient.deleteInventory(inventory_id);
    }

    public void deleteProduct(int product_id) {
        if (productClient.getProduct(product_id) == null)
            throw new NotFoundException(notFound("product", product_id));

        productClient.deleteProduct(product_id);
    }

    public void deleteLevelUp(int levelUp_id) {
        if (levelUpClient.getLevelUp(levelUp_id) == null)
            throw new NotFoundException(notFound("levelUp", levelUp_id));

        levelUpClient.deleteLevelUp(levelUp_id);
    }

    public void deleteInvoice(int invoiceView_id) {
        if (invoiceClient.getInvoiceById(invoiceView_id) == null)
            throw new NotFoundException(notFound("invoice", invoiceView_id));

        invoiceClient.deleteInvoice(invoiceView_id);
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
            Customer customer = customerClient.fetchCustomer(psvm.getCustomerId());
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
            clientInventory = inventoryClient.getInventory(i.getInventoryId());
            clientProduct = productClient.getProduct(clientInventory.getProductId());


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
        LevelUp clientLevelUp;
        LevelUp newLevelUp = new LevelUp();

        try {
            clientLevelUp = levelUpClient.getLevelUpByCustomerId(prvm.getCustomer().getCustomerId()).get(0);
        } catch (Exception e) {
            newLevelUp.setCustomerId(prvm.getCustomer().getCustomerId());
            newLevelUp.setMemberDate(psvm.getPurchaseDate());
            newLevelUp.setPoints(0);
            newLevelUp.setLevelUpId(levelUpClient.createLevelUp(newLevelUp).getLevelUpId());
            clientLevelUp = levelUpClient.getLevelUpByCustomerId(prvm.getCustomer().getCustomerId()).get(0);
        }

        int preLevelUp = levelUpClient.getLevelUpByCustomerId(prvm.getCustomer().getCustomerId()).get(0).getPoints();
        int levelUp = invoiceTotalPrice.divide(new BigDecimal("50")).setScale(1, BigDecimal.ROUND_FLOOR).intValue() * 10;
        int postLevelUp = preLevelUp + levelUp;

        if (postLevelUp != preLevelUp) {

            clientLevelUp.setPoints(postLevelUp);
            levelUpClient.updateLevelUp(clientLevelUp);
        }

        prvm.setLvlUpPtsBeforePurchase(preLevelUp);
        prvm.setLvlUpPtsAfterPurchase(postLevelUp);
        prvm.setMemberSince(clientLevelUp.getMemberDate());
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
