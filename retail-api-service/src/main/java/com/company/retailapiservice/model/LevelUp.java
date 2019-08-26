package com.company.retailapiservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class LevelUp {
    @Min(0)
    private int levelUpId;
    @Min(1)
    private int customerId;
    @Min(0)
    private int points;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NotNull(message = "LevelUp object must contain property memberDate")
    private LocalDate memberDate;

    public LevelUp() {
    }

    public LevelUp(@Min(0) int levelUpId, @Min(1) int customerId, @Min(0) int points, @NotNull(message = "LevelUp object must contain property memberDate") LocalDate memberDate) {
        this.levelUpId = levelUpId;
        this.customerId = customerId;
        this.points = points;
        this.memberDate = memberDate;
    }

    public int getLevelUpId() {
        return levelUpId;
    }

    public void setLevelUpId(int levelUpId) {
        this.levelUpId = levelUpId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public LocalDate getMemberDate() {
        return memberDate;
    }

    public void setMemberDate(LocalDate memberDate) {
        this.memberDate = memberDate;
    }

    @Override
    public String toString() {
        return "LevelUp{" +
                "levelUpId=" + levelUpId +
                ", customerId=" + customerId +
                ", points=" + points +
                ", memberDate=" + memberDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LevelUp levelUp = (LevelUp) o;
        return getLevelUpId() == levelUp.getLevelUpId() &&
                getCustomerId() == levelUp.getCustomerId() &&
                getPoints() == levelUp.getPoints() &&
                getMemberDate().equals(levelUp.getMemberDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLevelUpId(), getCustomerId(), getPoints(), getMemberDate());
    }
}