package com.company.customerservice.controller;

import com.company.customerservice.dao.CustomerDao;
import com.company.customerservice.dao.CustomerDaoJdbcTemplateImpl;
import com.company.customerservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/customers")
@CacheConfig(cacheNames = {"customers"})
public class CustomerController {
    @Autowired
    CustomerDao customerDao;

    @PostMapping
    @CachePut(key = "#result.getCustomerId()")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody @Valid Customer customer){
        return customerDao.addCustomer(customer);
    }

    @GetMapping("/{customerId}")
    @Cacheable
    public Customer getCustomer(@PathVariable int customerId){
        return customerDao.getCustomer(customerId);
    }

    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerDao.getAllCustomers();
    }

    @PutMapping
    @CacheEvict(key = "#customers.getCustomerId()")
    public void updateCustomer(@RequestBody Customer customer){
        customerDao.updateCustomer(customer);
    }

    @DeleteMapping("/{customerId}")
    @CacheEvict(key = "#customers.getCustomerId()")
    public void deleteCustomer(@PathVariable int customerId){
        customerDao.deleteCustomer(customerId);
    }
}
