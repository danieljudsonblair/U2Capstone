package com.company.levelupservice.controller;

import com.company.levelupservice.dao.LevelUpDao;
import com.company.levelupservice.model.LevelUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/levelups")
@CacheConfig(cacheNames = "levelups")
public class LevelUpController {

    @Autowired
    LevelUpDao dao;

    @PostMapping
    @CachePut(key = "#result.getLevelUpId()")
    public LevelUp submitLevelUp(@RequestBody @Valid LevelUp levelUp) {
        return dao.add(levelUp);
    }

    @Cacheable
    @GetMapping("/{id}")
    public LevelUp getLevelUpById(@PathVariable int id) {
        return dao.get(id);
    }

    @GetMapping
    public List<LevelUp> getAllLevelUps() {
        return dao.getAll();
    }

    @GetMapping("/customer/{id}")
    public List<LevelUp> getLevelUpsByCustomerId(@PathVariable int id) {
        return dao.getByCustomerId(id);
    }

    @PutMapping
    @CacheEvict(key = "levelups.getLevelUpId()")
    public void updateLevelUp(@RequestBody LevelUp levelUp) {
        dao.update(levelUp);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(key = "levelups.getLevelUpId()")
    public void deleteLevelUp(@PathVariable int id) {
        dao.delete(id);
    }
}
