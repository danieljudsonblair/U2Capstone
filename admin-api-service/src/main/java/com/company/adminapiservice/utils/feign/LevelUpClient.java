package com.company.adminapiservice.utils.feign;

import com.company.adminapiservice.model.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "level-up-service")
public interface LevelUpClient {

    @PostMapping(value = "/levelups")
    public LevelUp createLevelUp(@RequestBody @Valid LevelUp levelUp);

    @GetMapping(value = "/levelups/{id}")
    public LevelUp getLevelUp(@PathVariable int id);

    @GetMapping(value = "/levelups")
    public List<LevelUp> getAllLevelUps();

    @GetMapping(value = "/levelups/customer/{id}")
    public List<LevelUp> getLevelUpsByCustomerId(@PathVariable int id);

    @PutMapping(value = "/levelups")
    public void updateLevelUp(@RequestBody LevelUp levelUp);

    @DeleteMapping(value = "/levelups/{levelup_id}")
    public void deleteLevelUp(int levelup_id);
}
