package com.company.levelupqueueconsumer.util.feign;

import com.company.levelupqueueconsumer.messages.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "level-up-service")
@RequestMapping("/levelups")
public interface LevelUpClient {

    @PostMapping
    public void createLevelUp(LevelUp levelup);
    @PutMapping
    public void updateLevelUp(@RequestBody LevelUp levelup);
}
