package com.company.levelupservice.controller;

import com.company.levelupservice.dao.LevelUpDao;
import com.company.levelupservice.model.LevelUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/levelups")
public class LevelUpController {

    @Autowired
    LevelUpDao dao;

    @PostMapping
    public LevelUp submitLevelUp(@RequestBody @Valid LevelUp levelUp) {
        return dao.add(levelUp);
    }

    @GetMapping("/{id}")
    public LevelUp getLevelUpById(@PathVariable int id) {
        try {
            return dao.get(id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @GetMapping
    public List<LevelUp> getAllLevelUps() {
        return dao.getAll();
    }

    @GetMapping("/customer/{id}")
    public List<LevelUp> getLevelUpsByCustomerId(@PathVariable int id) {
        return dao.getByCustomerId(id);
    }

    @PutMapping("/{id}")
    public void updateLevelUp(@PathVariable int id, @RequestBody LevelUp levelUp) {
        levelUp.setLevelUpId(id);
        dao.update(levelUp);
    }

    @DeleteMapping("/{id}")
    public void deleteLevelUp(@PathVariable int id) {
        dao.delete(id);
    }
}
