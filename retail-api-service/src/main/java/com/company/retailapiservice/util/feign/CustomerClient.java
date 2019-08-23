package com.company.retailapiservice.util.feign;

import com.company.retailapiservice.model.Customer;
import com.company.retailapiservice.model.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "customer-service")
public interface CustomerClient {
    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody @Valid Customer customer);

    @GetMapping("/customers/{customerId}")
    public Customer getCustomer(@PathVariable int customerId);

    @GetMapping("/customers")
    public List<Customer> getAllCustomers();

    @PutMapping("/customers")
    public void updateCustomer(@RequestBody Customer customer);

    @DeleteMapping("/customers/{customerId}")
    public void deleteCustomer(@PathVariable int customerId);
}