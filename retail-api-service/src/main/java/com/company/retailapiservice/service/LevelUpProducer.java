package com.company.retailapiservice.service;

import com.company.retailapiservice.model.LevelUp;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Component
public class LevelUpProducer {

    public static final String EXCHANGE = "levelup-exchange";
    public static final String ROUTING_KEY = "levelup.create.#";
    public static final String SAVE_QUEUED_MSG = "LevelUp(s) queued for save";
    public static final String UPDATE_QUEUED_MSG = "LevelUp(s) queued for update";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public LevelUpProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/levelups")
    public String createLevelUp(@RequestBody @Valid LevelUp levelup) {
        LevelUp msg = new LevelUp(0, levelup.getCustomerId(), levelup.getPoints(), levelup.getMemberDate());
        System.out.println("Sending levelup...");
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, msg);
        System.out.println("LevelUp Sent");

        return SAVE_QUEUED_MSG;
    }
}
