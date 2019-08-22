package com.company.adminapiservice.service;

import com.company.adminapiservice.exception.NotFoundException;
import com.company.adminapiservice.model.Customer;
import com.company.adminapiservice.model.Inventory;
import com.company.adminapiservice.model.LevelUp;
import com.company.adminapiservice.model.Product;
import com.company.adminapiservice.utils.feign.*;
import com.company.adminapiservice.viewModel.PurchaseReturnViewModel;
import com.company.adminapiservice.viewModel.PurchaseSendViewModel;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
        if (productClient.getProduct(inventory.getProductId()) == null)
            throw new NotFoundException(notFound("product", inventory.getProductId()));
        if (inventory.getQuantity() < 0)
            throw new IllegalArgumentException("Quantity cannot be updated to a value less than 0");

        inventoryClient.updateInventory(inventory);
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

    public  void deleteProduct(int product_id) {
        if (productClient.getProduct(product_id) == null)
            throw new NotFoundException(notFound("product", product_id));

        productClient.deleteProduct(product_id);
    }

    public void deleteLevelUp(int levelUp_id) {
        if (levelUpClient.getLevelUp(levelUp_id) == null)
            throw new NotFoundException(notFound("levelUp", levelUp_id));

        levelUpClient.deleteLevelUp(levelUp_id);
    }

    private PurchaseReturnViewModel buildPurchaseReturnViewModel(PurchaseSendViewModel psvm) {
        PurchaseReturnViewModel prvm = new PurchaseReturnViewModel();
        if (psvm.getCustomer() == null) {
            Customer customer = customerClient.fetchCustomer(psvm.getCustomerId());
            if (customer == null)
                throw new NotFoundException(notFound("customer", psvm.getCustomerId()));
            prvm.setCustomer(customer);
        } else if (psvm.getCustomer() != null && psvm.getCustomerId() == 0){
            prvm.setCustomer(customerClient.createCustomer(psvm.getCustomer()));
        }
        psvm.getInventoryList().stream().forEach(i -> {
            Inventory clientInventory = inventoryClient.getInventory(i.getInventoryId());
            Inventory updatedInventory = new Inventory();
            if (clientInventory == null)
                throw new NotFoundException(notFound("inventory", i.getInventoryId()));
            if (clientInventory.getQuantity() >= i.getQuantity())
                updatedInventory.setInventoryId(i.getInventoryId());
                updatedInventory.setQuantity(clientInventory.getQuantity() - i.getQuantity());
                inventoryClient.updateInventory(updatedInventory);
        });
        // TODO set product list


        return prvm;
    }
}
