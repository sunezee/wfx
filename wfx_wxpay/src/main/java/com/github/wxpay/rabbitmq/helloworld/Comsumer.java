package com.github.wxpay.rabbitmq.helloworld;

import com.github.wxpay.rabbitmq.RabbitMqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 */
public class Comsumer {
    public static void main(String[] args) {
        try {
            //创建通道
            Connection connection = RabbitMqUtil.getConnection();
            Channel channel = connection.createChannel();
            //定义队列
            channel.queueDeclare(RabbitMqUtil.QUEUE1,false,false,false,null);
            //创建消费者
            QueueingConsumer consumer=new QueueingConsumer(channel);
            //从通道获取消息
            channel.basicConsume(RabbitMqUtil.QUEUE1,true,consumer);
            //读取消息
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            System.out.println("收到消息"+new String(delivery.getBody()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
