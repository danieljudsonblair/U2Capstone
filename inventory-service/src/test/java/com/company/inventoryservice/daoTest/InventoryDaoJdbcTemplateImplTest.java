package com.company.inventoryservice.daoTest;

import com.company.inventoryservice.dao.InventoryDao;
import com.company.inventoryservice.model.Inventory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InventoryDaoJdbcTemplateImplTest {

    @Autowired
    InventoryDao inventoryDao;

    @Before
    public void setUp() throws Exception {
        inventoryDao.getAllInventories().stream().forEach(i -> inventoryDao.deleteInventory(i.getInventoryId()));
    }

    @Test
    public void addGetDeleteInventory(){
        Inventory inventory = new Inventory();
        inventory.setProductId(3);
        inventory.setQuantity(20);

        inventoryDao.createInventory(inventory);

        Inventory inventory1 = inventoryDao.getInventory(inventory.getInventoryId());
        assertEquals(inventory1, inventory);

        inventoryDao.deleteInventory(inventory.getInventoryId());
        inventory1 = inventoryDao.getInventory(inventory.getInventoryId());
        assertNull(inventory1);
    }

    @Test
    public void updateInventory(){
        Inventory inventory = new Inventory();
        inventory.setProductId(3);
        inventory.setQuantity(20);

        inventoryDao.createInventory(inventory);

        inventory.setProductId(3);
        inventory.setQuantity(5);

        inventoryDao.updateInventory(inventory);

        Inventory inventory1 = inventoryDao.getInventory(inventory.getInventoryId());
        assertEquals(inventory1, inventory);
    }

    @Test
    public void getAllInventories(){
        Inventory inventory = new Inventory();
        inventory.setProductId(3);
        inventory.setQuantity(20);

        inventoryDao.createInventory(inventory);

        Inventory inventory1 = new Inventory();
        inventory1.setProductId(4);
        inventory1.setQuantity(25);

        inventoryDao.createInventory(inventory1);

        List<Inventory> inventoryList = inventoryDao.getAllInventories();
        assertEquals(2, inventoryList.size());
    }
}
