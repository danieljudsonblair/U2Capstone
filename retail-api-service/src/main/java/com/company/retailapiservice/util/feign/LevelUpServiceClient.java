package com.company.retailapiservice.util.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "level-up-service")
public interface LevelUpServiceClient {
    @GetMapping(value = "/levelup/customer/{customerId}")
    public int fetchLevelUpPointByCustomerId(@PathVariable int customerId);



}
