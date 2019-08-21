package com.company.invoiceservice.dao;

import com.company.invoiceservice.model.Invoice;
import com.company.invoiceservice.model.InvoiceItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceItemJdbcTemplateImplTests {

    @Autowired
    InvoiceItemDao iiDao;

    @Autowired
    InvoiceDao iDao;

    @Before
    public void setUp() throws Exception {
        iiDao.getAll().stream().forEach(ii -> iiDao.delete(ii.getInvoiceItemId()));
        iDao.getAll().stream().forEach(i -> iDao.delete(i.getInvoiceId()));
    }

    @Test
    public void addGetGetAllDelete() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019, 8,8));

        invoice.setInvoiceId(iDao.add(invoice).getInvoiceId());

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(1);
        invoiceItem.setUnitPrice(new BigDecimal("9.99"));

        assertEquals(iiDao.getAll().size(), 0);
        assertNull(iiDao.get(1));

        iiDao.add(invoiceItem);

        assertEquals(iiDao.get(invoiceItem.getInvoiceItemId()), invoiceItem);

        assertEquals(iiDao.getAll().size(), 1);

        iiDao.delete(invoiceItem.getInvoiceItemId());

        assertNull(iiDao.get(invoiceItem.getInvoiceItemId()));
    }

    @Test
    public void updateInvoiceItem() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019, 8,8));

        invoice.setInvoiceId(iDao.add(invoice).getInvoiceId());

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(1);
        invoiceItem.setUnitPrice(new BigDecimal("9.99"));

        invoiceItem.setInvoiceItemId(iiDao.add(invoiceItem).getInvoiceItemId());

        invoiceItem.setInventoryId(2);
        invoiceItem.setQuantity(2);
        invoiceItem.setUnitPrice(new BigDecimal("19.99"));

        iiDao.update(invoiceItem);

        assertEquals(iiDao.get(invoiceItem.getInvoiceItemId()), invoiceItem);
    }

    @Test
    public void getByInvoiceId() {

        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019, 8,8));

        invoice.setInvoiceId(iDao.add(invoice).getInvoiceId());

        assertEquals(iiDao.getByInvoiceId(invoice.getInvoiceId()).size(), 0);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(1);
        invoiceItem.setUnitPrice(new BigDecimal("9.99"));

        iiDao.add(invoiceItem);

        assertEquals(iiDao.getByInvoiceId(invoice.getInvoiceId()).size(), 1);
    }
}
