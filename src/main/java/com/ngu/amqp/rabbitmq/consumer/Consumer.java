package com.ngu.amqp.rabbitmq.consumer;

import com.ngu.amqp.rabbitmq.config.RabbitmqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: 李良超
 * @Date: Created in 2019/4/29 15:11
 * @Description:
 */
@Component
@RabbitListener(queues = RabbitmqConfig.QUEUE_A)
public class Consumer {
    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @RabbitHandler
    public void process(String content) {
        logger.info("接收处理队列A当中的消息：" + content);
    }
}
