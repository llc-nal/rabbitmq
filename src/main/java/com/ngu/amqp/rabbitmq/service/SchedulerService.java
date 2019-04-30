package com.ngu.amqp.rabbitmq.service;

import com.ngu.amqp.rabbitmq.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: 李良超
 * @Date: Created in 2019/4/29 15:31
 * @Description:
 */
@Component
@EnableScheduling
public class SchedulerService {

    @Autowired
    private Producer producer;
    private static int count = 0;

    @Scheduled(cron = "0/5 * * * * ?")
    public void printMsg() {
        producer.sendMsg("队列消息：" + count);
        count++;
    }
}
