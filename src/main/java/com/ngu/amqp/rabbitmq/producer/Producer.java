package com.ngu.amqp.rabbitmq.producer;

import com.ngu.amqp.rabbitmq.config.RabbitmqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author: 李良超
 * @Date: Created in 2019/4/29 14:50
 * @Description: message producer
 */
@Service
public class Producer implements RabbitTemplate.ConfirmCallback {
    private final Logger logger = LoggerFactory.getLogger(Producer.class);

    /**
     * 由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
      */
    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入rabbitTemplate
     * @param rabbitTemplate
     */
    @Autowired
    public Producer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        // rabbitTemplate如果为单例的话，那回调就是最后设置的内容
        rabbitTemplate.setConfirmCallback(this);
    }

    public void sendMsg(String content) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        // 把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_A, RabbitmqConfig.ROUTINGKEY_A, content, correlationId);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("回调ID：" + correlationData);
        if (ack) {
            logger.info("消息成功消费");
        } else {
            logger.info("消息消费失败：" + cause);
        }
    }
}
