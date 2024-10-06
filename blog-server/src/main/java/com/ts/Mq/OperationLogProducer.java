package com.ts.Mq;


import cn.hutool.extra.spring.SpringUtil;
import com.ts.Config.RabbitMQConfig;
import com.ts.Model.Entity.TaskLog;
import com.ts.Model.Entity.Visitor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;

public class OperationLogProducer {
    private static  RabbitTemplate myRabbitTemplate = null;

    static {
        myRabbitTemplate= SpringUtil.getBean(RabbitTemplate.class);
    }
    public static void sendMessage(TaskLog message) {
        myRabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,RabbitMQConfig.ROUTING_KEY, message);
    }
}
