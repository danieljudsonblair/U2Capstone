package com.company.levelupservice.daoTest;

import com.company.levelupservice.dao.LevelUpDao;
import com.company.levelupservice.model.LevelUp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class levelUpJdbcTemplateImplTest {

    @Autowired
    LevelUpDao levelUpDao;

    @Before
    public void setUp() throws Exception{
        List<LevelUp> levelUpList = levelUpDao.getAll();
        for (LevelUp levelUp: levelUpList)
            levelUpDao.delete(levelUp.getLevelUpId());
    }

    @Test
    public void addGetDeleteLevelUp(){
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(2019, 1, 3));

        levelUp = levelUpDao.add(levelUp);

        LevelUp levelUp1 = levelUpDao.get(levelUp.getLevelUpId());
        assertEquals(levelUp, levelUp1);

        levelUp1 = levelUpDao.getByCustomerId(1);
        assertEquals(levelUp, levelUp1);

        levelUpDao.delete(levelUp.getLevelUpId());
        levelUp1 = levelUpDao.get(levelUp.getLevelUpId());
        assertNull(levelUp1);
    }

    @Test
    public void updateLevelUp(){
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(2019, 1, 3));

        levelUp = levelUpDao.add(levelUp);

        levelUp.setCustomerId(1);
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(2019,2,13));

        levelUpDao.update(levelUp);

        LevelUp levelUp1 = levelUpDao.get(levelUp.getLevelUpId());
        assertEquals(levelUp1, levelUp);
    }

    @Test
    public void getAllLevelUps(){
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(2019, 1, 3));

        levelUp = levelUpDao.add(levelUp);

        LevelUp levelUp1 = new LevelUp();
        levelUp1.setCustomerId(2);
        levelUp1.setPoints(20);
        levelUp1.setMemberDate(LocalDate.of(2019,2,13));

        levelUp1 = levelUpDao.add(levelUp1);

        List<LevelUp> levelUpList = levelUpDao.getAll();
        assertEquals(2,levelUpList.size());
    }

}
