package com.company.invoiceservice.service;

import com.company.invoiceservice.dao.InvoiceDao;
import com.company.invoiceservice.dao.InvoiceDaoJdbcTemplateImpl;
import com.company.invoiceservice.dao.InvoiceItemDao;
import com.company.invoiceservice.dao.InvoiceItemJdbcTemplateImpl;
import com.company.invoiceservice.model.Invoice;
import com.company.invoiceservice.model.InvoiceItem;
import com.company.invoiceservice.view.InvoiceViewModel;
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

    InvoiceDao invoiceDao;
    InvoiceItemDao invoiceItemDao;
    ServiceLayer service;

    @Before
    public void setUp() throws Exception {

        setUpInvoiceDaoMock();
        setUpInvoiceItemDaoMock();

        service = new ServiceLayer(invoiceDao, invoiceItemDao);
    }

    private void setUpInvoiceDaoMock() {
        invoiceDao = mock(InvoiceDaoJdbcTemplateImpl.class);

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019, 8, 8));

        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(1);
        invoice1.setPurchaseDate(LocalDate.of(2019, 8, 8));

        List<Invoice> iList = new ArrayList<>();
        iList.add(invoice);

        doReturn(invoice).when(invoiceDao).add(invoice1);
        doReturn(invoice).when(invoiceDao).get(1);
        doReturn(iList).when(invoiceDao).getAll();
        doReturn(iList).when(invoiceDao).getByCustomerId(1);
    }

    private void setUpInvoiceItemDaoMock() {
        invoiceItemDao = mock(InvoiceItemJdbcTemplateImpl.class);

        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019, 8, 8));

        InvoiceItem invoiceItem = new InvoiceItem();

        invoiceItem.setInvoiceItemId(1);
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(10);
        invoiceItem.setUnitPrice(new BigDecimal("9.99"));

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceId(1);
        invoiceItem1.setInventoryId(1);
        invoiceItem1.setQuantity(10);
        invoiceItem1.setUnitPrice(new BigDecimal("9.99"));

        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        invoiceItemList.add(invoiceItem);

        doReturn(invoiceItem).when(invoiceItemDao).add(invoiceItem1);
        doReturn(invoiceItem).when(invoiceItemDao).get(1);
        doReturn(invoiceItemList).when(invoiceItemDao).getAll();
        doReturn(invoiceItemList).when(invoiceItemDao).getByInvoiceId(1);
    }

    @Test
    public void saveInvoice() {
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(10);
        invoiceItem.setUnitPrice(new BigDecimal("9.99"));

        List<InvoiceItem> invoiceItemList = new ArrayList<>();

        invoiceItemList.add(invoiceItem);

        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setCustomerId(1);
        ivm.setPurchaseDate(LocalDate.of(2019,8,8));
        ivm.setInvoiceItemList(invoiceItemList);

        InvoiceViewModel ivm1 = ivm;
        ivm1.setInvoiceId(1);

        ivm.setInvoiceId(service.saveInvoice(ivm).getInvoiceId());

        assertEquals(ivm, ivm1);
    }

    @Test
    public void findFindByCustomerIdFindAllInvoices() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019, 8, 8));

        invoice = invoiceDao.add(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(10);
        invoiceItem.setUnitPrice(new BigDecimal("9.99"));

        invoiceItem = invoiceItemDao.add(invoiceItem);

        List<InvoiceItem> invoiceItemList = new ArrayList<>();

        invoiceItemList.add(invoiceItem);

        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setInvoiceId(invoice.getInvoiceId());
        ivm.setCustomerId(1);
        ivm.setPurchaseDate(LocalDate.of(2019,8,8));
        ivm.setInvoiceItemList(invoiceItemList);

        assertEquals(ivm, service.findInvoice(1));
        assertEquals(service.findAllInvoices().size(), 1);
        assertEquals(service.findByCustomerId(1).size(), 1);
    }

    @Test
    public void updateInvoice() {
        InvoiceItem invoiceItem = new InvoiceItem();

        invoiceItem.setInvoiceItemId(1);
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(2);
        invoiceItem.setQuantity(11);
        invoiceItem.setUnitPrice(new BigDecimal("19.99"));

        List<InvoiceItem> invoiceItemList = new ArrayList<>();

        invoiceItemList.add(invoiceItem);


        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setInvoiceId(1);
        ivm.setCustomerId(2);
        ivm.setPurchaseDate(LocalDate.of(2020,8,8));
        ivm.setInvoiceItemList(invoiceItemList);

        service.updateInvoice(ivm);

        ArgumentCaptor<Invoice> invoiceCaptor = ArgumentCaptor.forClass(Invoice.class);
        verify(invoiceDao).update(invoiceCaptor.capture());
        assertEquals(ivm.getCustomerId(), invoiceCaptor.getValue().getCustomerId());
    }

    @Test
    public void deleteInvoice() {
        InvoiceViewModel ivm = service.findInvoice(1);
        service.deleteInvoice(1);
        ArgumentCaptor<Integer> invoiceCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(invoiceDao).delete(invoiceCaptor.capture());
        assertEquals(ivm.getInvoiceId(), invoiceCaptor.getValue().intValue());
    }

}
