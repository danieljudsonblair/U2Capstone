package com.company.customerservice.controller;

import com.company.customerservice.dao.CustomerDao;
import com.company.customerservice.dao.CustomerDaoJdbcTemplateImpl;
import com.company.customerservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    CustomerDao customerDao;

    @PostMapping
    public Customer createCustomer(@RequestBody @Valid Customer customer){
        return customerDao.addCustomer(customer);
    }

    @GetMapping("/{customerId}")
    public Customer getCustomer(@PathVariable int customerId){
        return customerDao.getCustomer(customerId);
    }

    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerDao.getAllCustomers();
    }

    @PutMapping("/{customerId}")
    public void updateCustomer(@PathVariable int customerId, @RequestBody Customer customer){
        customer.setCustomerId(customerId);
        customerDao.updateCustomer(customer);
    }

    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable int customerId){
        customerDao.deleteCustomer(customerId);
    }
}
