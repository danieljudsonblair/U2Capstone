package com.company.levelupservice.daoTest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class levelUpJdbcTemplateImplTest {

    @Autowired
    LevelUpDao levelUpDao;

    @Before
    public void setUp() throws Exception{
        List<LevelUp> levelUpList = levelUpDao.getAllLevelUps();
        for (LevelUp levelUp: levelUpList)
            levelUpDao.deleteLevelUp(levelUp.getId());
    }

    @Test
    public void addGetDeleteLevelUp(){
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(10);
        levelUp.setMemberDate(new LocalDate(2019, 1, 3);

        levelUp = levelUpDao.createLevelUp(levelUp);

        LevelUp levelUp1 = levelUpDao.getLevelUp(levelUp.getLevelUpId());
        assertEquals(levelUp, levelUp1);

        levelUpDao.deleteLevelUp(levelUp.getLevelUpId());
        levelUp1 = levelUpDao.getLevelUp(levelUp.getLevelUpId());
        assertNull(levelUp1);
    }

    @Test
    public void updateLevelUp(){
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(10);
        levelUp.setMemberDate(new LocalDate(2019, 1, 3));

        levelUp = levelUpDao.createLevelUp(levelUp);

        levelUp.setCustomerId(1);
        levelUp.setPoints(10);
        levelUp.setMemberDate(new LocalDate(2019,2,13));

        levelUpDao.updateLevelUp(levelUp);

        LevelUp levelUp1 = levelUpDao.getLevelUp(levelUp.getLevelUpId());
        assertEquals(levelUp1, levelUp);
    }

    @Test
    public void getAllLevelUps(){
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(10);
        levelUp.setMemberDate(new LocalDate(2019, 1, 3));

        levelUp = levelUpDao.createLevelUp(levelUp);

        LevelUp levelUp1 = new LevelUp();
        levelUp1.setCustomerId(2);
        levelUp1.setPoints(20);
        levelUp1.setMemberDate(new LocalDate(2019,2,13));

        levelUp1 = levelUpDao.createLevelUp(levelUp1);

        List<LevelUp> levelUpList = levelUpDao.getAllLevelUps();
        assertEquals(2,levelUpList.size());
    }

}
