package com.company.adminapiservice.utils.feign;

import com.company.adminapiservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "customer-service")
public interface CustomerClient {

    @PostMapping(value = "/customers")
    public Customer createCustomer(@RequestBody @Valid Customer customer);

    @GetMapping(value = "/customers/{customer_id}")
    public Customer fetchCustomer(@PathVariable int customer_id);

    @GetMapping(value = "/customers")
    public List<Customer> fetchAllCustomers();

    @PutMapping(value = "/customers/{customer_id}")
    public void updateCustomer(@RequestBody Customer customer);

    @DeleteMapping(value = "/customers/{customer_id}")
    public void deleteCustomer(@PathVariable int customer_id);
}
