package com.tensquare_rq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "kim")
public class Consumer3 {

    @RabbitHandler
    public void getMsg(String msg){
        System.out.println("kim取到消息"+msg);
    }
}
