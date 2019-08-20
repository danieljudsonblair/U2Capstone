package com.company.invoiceservice.dao;

import com.company.invoiceservice.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceDaoJdbcTemplateImpl implements InvoiceDao {

    private JdbcTemplate jdbcTemplate;

    public static final String INSERT_INVOICE =
            "insert into invoice (customer_id, purchase_date) values (?, ?)";

    public static final String SELECT_INVOICE_BY_ID =
            "select * from invoice where invoice_id = ?";

    public static final String SELECT_ALL_INVOICES =
            "select * from invoice";

    public static final String SELECT_INVOICES_BY_CUSTOMER_ID =
            "select * from invoice where customer_id = ?";

    public static final String UPDATE_INVOICE =
            "update invoice set customer_id = ?, purchase_date = ? where invoice_id = ?";

    public static final String DELETE_INVOICE =
            "delete from invoice where invoice_id = ?";

    @Autowired
    public InvoiceDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public Invoice add(Invoice invoice) {
        jdbcTemplate.update(
                INSERT_INVOICE,
                invoice.getCustomerId(),
                invoice.getPurchaseDate());

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        invoice.setInvoiceId(id);

        return invoice;
    }

    @Override
    public Invoice get(int invoice_id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_INVOICE_BY_ID, this::mapRowToInvoice, invoice_id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Invoice> getAll() {
        return jdbcTemplate.query(SELECT_ALL_INVOICES, this::mapRowToInvoice);
    }

    @Override
    public List<Invoice> getByCustomerId(int customer_id) {
        return jdbcTemplate.query(SELECT_INVOICES_BY_CUSTOMER_ID, this::mapRowToInvoice, customer_id);
    }

    @Transactional
    @Override
    public void update(Invoice invoice) {
        jdbcTemplate.update(
                UPDATE_INVOICE,
                invoice.getCustomerId(),
                invoice.getPurchaseDate(),
                invoice.getInvoiceId()
        );
    }

    @Transactional
    @Override
    public void delete(int invoice_id) { jdbcTemplate.update(DELETE_INVOICE, invoice_id); }

    private Invoice mapRowToInvoice(ResultSet rs, int rowNum) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(rs.getInt("invoice_id"));
        invoice.setCustomerId(rs.getInt("customer_id"));
        invoice.setPurchaseDate(rs.getDate("purchase_date").toLocalDate());

        return invoice;
    }
}
