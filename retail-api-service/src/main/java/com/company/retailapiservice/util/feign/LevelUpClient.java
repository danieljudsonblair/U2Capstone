package com.company.retailapiservice.util.feign;

import com.company.retailapiservice.model.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "level-up-service")
public interface LevelUpClient {
    @GetMapping(value = "/levelup/customer/{customerId}")
    public List<LevelUp> fetchLevelUpPointByCustomerId(@PathVariable int customerId);



}
