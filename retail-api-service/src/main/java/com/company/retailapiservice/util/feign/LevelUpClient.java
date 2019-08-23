package com.company.retailapiservice.util.feign;

import com.company.retailapiservice.model.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "level-up-service")
public interface LevelUpClient {
    @GetMapping("/levelups/{id}")
    public LevelUp getLevelUpById(@PathVariable int id);

    @GetMapping("/levelups")
    public List<LevelUp> getAllLevelUps();

    @GetMapping("/levelups/customer/{id}")
    public List<LevelUp> getLevelUpsByCustomerId(@PathVariable int id);

    @DeleteMapping("/levelups/{id}")
    public void deleteLevelUp(@PathVariable int id);




}
