package com.company.levelupservice.dao;

import com.company.levelupservice.model.LevelUp;

import java.util.List;

public interface LevelUpDao {
    public LevelUp add(LevelUp levelUp);
    public LevelUp get(int id);
    public List<LevelUp> getAll();
    public List<LevelUp> getByCustomerId(int customer_id);
    public void update(LevelUp levelUp);
    public void delete(int id);
}
