package org.kettle.scheduler.system.biz.component;

import org.kettle.scheduler.system.api.entity.TaskLog;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-31 08:04
 */
@Component
public class DirectSender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public boolean sendDirectMessage(TaskLog taskLog) {
        //将消息携带绑定键值：kettleDirectRouting kettleDirectExchange
        rabbitTemplate.convertAndSend("kettleDirectExchange", "kettleDirectRouting", taskLog);
        return true;
    }
}
