package com.company.levelupqueueconsumer;

import com.company.levelupqueueconsumer.messages.LevelUp;
import com.company.levelupqueueconsumer.util.feign.LevelUpClient;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    @Autowired
    LevelUpClient client;

    @RabbitListener(queues = LevelupQueueConsumerApplication.QUEUE_NAME)
    public void receiveMessage(LevelUp levelup) {
        if (levelup.getLevelUpId() == 0) {
            client.createLevelUp(levelup);
        } else {
            client.updateLevelUp(levelup);
        }
    }
}
