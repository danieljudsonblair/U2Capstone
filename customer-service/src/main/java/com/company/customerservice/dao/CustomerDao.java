package com.company.customerservice.dao;

import com.company.customerservice.model.Customer;

import java.util.List;

public interface CustomerDao {

    public Customer addCustomer(Customer customer);

    public Customer getCustomer(int customerId);

    public List<Customer> getAllCustomers();

    public void updateCustomer(Customer customer);

    public void deleteCustomer(int customerId);
}
