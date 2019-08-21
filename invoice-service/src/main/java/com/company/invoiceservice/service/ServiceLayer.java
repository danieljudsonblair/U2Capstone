package com.company.invoiceservice.service;

import com.company.invoiceservice.dao.InvoiceDao;
import com.company.invoiceservice.dao.InvoiceItemDao;
import com.company.invoiceservice.exception.NotFoundException;
import com.company.invoiceservice.model.Invoice;
import com.company.invoiceservice.view.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceLayer {

    InvoiceDao invoiceDao;
    InvoiceItemDao invoiceItemDao;

    @Autowired
    public ServiceLayer(InvoiceDao invoiceDao, InvoiceItemDao invoiceItemDao) {
        this.invoiceDao = invoiceDao;
        this.invoiceItemDao = invoiceItemDao;
    }

    public InvoiceViewModel saveInvoice(InvoiceViewModel ivm) {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(ivm.getCustomerId());
        invoice.setPurchaseDate(ivm.getPurchaseDate());
        invoice.setInvoiceId(invoiceDao.add(invoice).getInvoiceId());
        ivm.setInvoiceId(invoice.getInvoiceId());
        ivm.getInvoiceItemList().stream().forEach(ii -> {
            ii.setInvoiceId(invoice.getInvoiceId());
            ii.setInvoiceItemId(invoiceItemDao.add(ii).getInvoiceItemId());
        });

        return buildInvoiceViewModel(invoice);
    }

    public InvoiceViewModel findInvoice(int invoice_id) {
        Invoice invoice = invoiceDao.get(invoice_id);
        if (invoice == null)
            throw new NotFoundException("No invoice exists @ ID " + invoice_id);
        return buildInvoiceViewModel(invoice);
    }

    public List<InvoiceViewModel> findAllInvoices() {
        List<InvoiceViewModel> ivmList = new ArrayList<>();
        invoiceDao.getAll().stream().forEach(invoice -> ivmList.add(buildInvoiceViewModel(invoice)));

        return ivmList;
    }

    public List<InvoiceViewModel> findByCustomerId(int customer_id) {
        List<InvoiceViewModel> ivmList = new ArrayList<>();
        invoiceDao.getByCustomerId(customer_id).stream().forEach(invoice -> ivmList.add(buildInvoiceViewModel(invoice)));

        return ivmList;
    }

    public void updateInvoice(InvoiceViewModel ivm) {
        Invoice ig = invoiceDao.get(ivm.getInvoiceId());

        if (ig == null)
            throw new NotFoundException(("No invoice found @ ID " + ivm.getInvoiceId()));
        if (ivm.getCustomerId() != 0)
            ig.setCustomerId(ivm.getCustomerId());
        if (ivm.getPurchaseDate() != null)
            ig.setPurchaseDate(ivm.getPurchaseDate());
        if (ivm.getInvoiceItemList() != null)
            ivm.getInvoiceItemList().stream().forEach(ii -> invoiceItemDao.update(ii));

        invoiceDao.update(ig);
    }

    public void deleteInvoice(int invoice_id) {
        if (invoiceDao.get(invoice_id) == null)
            throw new NotFoundException("No invoice found @ ID " + invoice_id);
        invoiceItemDao.getByInvoiceId(invoice_id).stream().forEach(ii -> invoiceItemDao.delete(ii.getInvoiceItemId()));

        invoiceDao.delete(invoice_id);
    }

    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice) {
        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setInvoiceId(invoice.getInvoiceId());
        ivm.setCustomerId(invoice.getCustomerId());
        ivm.setPurchaseDate(invoice.getPurchaseDate());
        ivm.setInvoiceItemList(invoiceItemDao.getByInvoiceId(invoice.getInvoiceId()));

        return ivm;
    }
}
