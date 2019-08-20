package com.company.invoiceservice.dao;

import com.company.invoiceservice.model.Invoice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceJdbcTemplateImplTests {

    @Autowired
    InvoiceDao dao;

    @Autowired
    InvoiceItemDao iidao;

    @Before
    public void setUp() throws Exception {
        iidao.getAll().stream().forEach(ii -> iidao.delete(ii.getInvoiceItemId()));
        dao.getAll().stream().forEach(i -> dao.delete(i.getInvoiceId()));
    }

    @Test
    public void addGetGetAllGetByCustomerDelete() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019, 8, 2));

        assertEquals(dao.getAll().size(), 0);
        assertEquals(dao.getByCustomerId(1).size(), 0);
        assertNull(dao.get(1));

        dao.add(invoice);

        assertEquals(dao.get(invoice.getInvoiceId()), invoice);

        assertEquals(dao.getAll().size(), 1);

        assertEquals(dao.getByCustomerId(1).size(), 1);

        dao.delete(invoice.getInvoiceId());

        assertNull(dao.get(invoice.getInvoiceId()));
    }

    @Test
    public void updateInvoice() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019,2,2));

        dao.add(invoice);

        invoice.setCustomerId(2);
        invoice.setPurchaseDate(LocalDate.of(2018,2,2));

        dao.update(invoice);

        assertEquals(dao.get(invoice.getInvoiceId()), invoice);
    }
}
