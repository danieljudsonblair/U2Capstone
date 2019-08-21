package com.company.invoiceservice.dao;

import com.company.invoiceservice.model.InvoiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceItemJdbcTemplateImpl implements InvoiceItemDao{

    private JdbcTemplate jdbcTemplate;

    public static final String INSERT_INVOICE_ITEM =
            "insert into invoice_item (invoice_id, inventory_id, quantity, unit_price) values (?, ?, ?, ?)";

    public static final String SELECT_INVOICE_ITEM_BY_ID =
            "select * from invoice_item where invoice_item_id = ?";

    public static final String SELECT_INVOICE_ITEMS_BY_INVOICE_ID =
            "select * from invoice_item where invoice_id = ?";

    public static final String SELECT_ALL_INVOICE_ITEMS =
            "select * from invoice_item";

    public static final String UPDATE_INVOICE_ITEM =
            "update invoice_item set invoice_id = ?, inventory_id = ?, quantity = ?, unit_price = ? where invoice_item_id = ?";

    public static final String DELETE_INVOICE_ITEM =
            "delete from invoice_item where invoice_item_id = ?";

    @Autowired
    public InvoiceItemJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public InvoiceItem add(InvoiceItem invoiceItem) {
        jdbcTemplate.update(
                INSERT_INVOICE_ITEM,
                invoiceItem.getInvoiceId(),
                invoiceItem.getInventoryId(),
                invoiceItem.getQuantity(),
                invoiceItem.getUnitPrice()
        );

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        invoiceItem.setInvoiceItemId(id);

        return invoiceItem;
    }

    @Override
    public InvoiceItem get(int invoiceItem_id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_INVOICE_ITEM_BY_ID, this::mapRowToInvoiceItem, invoiceItem_id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<InvoiceItem> getAll() {
        return jdbcTemplate.query(SELECT_ALL_INVOICE_ITEMS, this::mapRowToInvoiceItem);
    }

    @Override
    public List<InvoiceItem> getByInvoiceId(int invoice_id) {
        return jdbcTemplate.query(SELECT_INVOICE_ITEMS_BY_INVOICE_ID, this::mapRowToInvoiceItem, invoice_id);
    }


    public void update(InvoiceItem invoiceItem) {
        jdbcTemplate.update(
                UPDATE_INVOICE_ITEM,
                invoiceItem.getInvoiceId(),
                invoiceItem.getInventoryId(),
                invoiceItem.getQuantity(),
                invoiceItem.getUnitPrice(),
                invoiceItem.getInvoiceItemId()
        );
    }


    public void delete(int invoiceItem_id) {
        jdbcTemplate.update(DELETE_INVOICE_ITEM, invoiceItem_id);
    }

    private InvoiceItem mapRowToInvoiceItem(ResultSet rs, int rowNum) throws SQLException {
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(rs.getInt("invoice_item_id"));
        invoiceItem.setInvoiceId(rs.getInt("invoice_id"));
        invoiceItem.setInventoryId(rs.getInt("inventory_id"));
        invoiceItem.setQuantity(rs.getInt("quantity"));
        invoiceItem.setUnitPrice(rs.getBigDecimal("unit_price"));

        return invoiceItem;
    }
}
