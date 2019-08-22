package com.company.adminapiservice.utils.feign;

import com.company.adminapiservice.model.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "level-up-service")
public interface LevelUpClient {

    @PostMapping(value = "/levelup")
    public LevelUp createLevelUp(@RequestBody @Valid LevelUp levelUp);

    @GetMapping(value = "/levelup/{id}")
    public LevelUp getLevelUp(@PathVariable int id);

    @GetMapping(value = "/levelup")
    public List<LevelUp> getAllLevelUps();

    @PutMapping(value = "/levelup/{levelup_id}")
    public void updateLevelUp(@RequestBody LevelUp levelUp);

    @DeleteMapping(value = "/levelup/{levelup_id}")
    public void deleteLevelUp(int levelup_id);
}
