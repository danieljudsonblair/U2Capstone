package com.company.invoiceservice.dao;

import com.company.invoiceservice.model.InvoiceItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceItemJdbcTemplateImplTests {

    @Autowired
    InvoiceItemDao dao;

    @Before
    public void setUp() throws Exception {
        dao.getAll().stream().forEach(i -> dao.delete(i.getInvoiceId()));
    }

    @Test
    public void addGetGetAllDelete() {
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(1);
        invoiceItem.setUnitPrice(new BigDecimal("9.99"));

        assertEquals(dao.getAll().size(), 0);
        assertNull(dao.get(1));

        dao.add(invoiceItem);

        assertEquals(dao.get(invoiceItem.getInvoiceItemId()), invoiceItem);

        assertEquals(dao.getAll().size(), 1);

        dao.delete(invoiceItem.getInvoiceItemId());

        assertNull(dao.get(invoiceItem.getInvoiceItemId()));
    }

    @Test
    public void updateInvoiceItem() {
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(1);
        invoiceItem.setUnitPrice(new BigDecimal("9.99"));

        dao.add(invoiceItem);

        invoiceItem.setInvoiceId(2);
        invoiceItem.setInventoryId(2);
        invoiceItem.setQuantity(2);
        invoiceItem.setUnitPrice(new BigDecimal("19.99"));

        dao.update(invoiceItem);

        assertEquals(dao.get(invoiceItem.getInvoiceItemId()), invoiceItem);
    }
}
