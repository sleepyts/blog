package com.ts.Mq;


import cn.hutool.json.JSONUtil;
import com.ts.Config.RabbitMQConfig;
import com.ts.Mapper.TaskLogMapper;
import com.ts.Model.Entity.TaskLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class OperationLogConsumer {

    private final TaskLogMapper taskLogMapper;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
        TaskLog bean = JSONUtil.toBean(message, TaskLog.class);
        taskLogMapper.insert(bean);
    }
}
