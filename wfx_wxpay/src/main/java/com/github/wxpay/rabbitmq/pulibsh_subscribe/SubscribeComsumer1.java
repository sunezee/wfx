package com.github.wxpay.rabbitmq.pulibsh_subscribe;

import com.github.wxpay.rabbitmq.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * work模式的消费者，
 * 消费者中定义不同的队列，将队列绑定到生产者声明的交换机上，
 * 用于让交换机发布消息到队列，再消费自己的队列
 */
public class SubscribeComsumer1 {
    public static void main(String[] args) {
        try {
            //获取连接
            Connection connection = RabbitMqUtil.getConnection();
            //创建通道
            Channel channel = connection.createChannel();
            //定义队列
            channel.queueDeclare(RabbitMqUtil.QUEUE3,false,false,false,null);
            //声明交换机
            channel.exchangeDeclare(RabbitMqUtil.EXCHANGE1,"fanout");
            //绑定队列到交换机中 参数1，队列名；参数2，交换机名称
            channel.queueBind(RabbitMqUtil.QUEUE3,RabbitMqUtil.EXCHANGE1,"");
            //1）让通道一次只发送一个任务
            channel.basicQos(1);
            //创建消费者
            QueueingConsumer consumer=new QueueingConsumer(channel);
            //从通道获取消息 true是自动确认模式，2)参数2更改为false,是手动确认模式（消费者主动通知通道）
            channel.basicConsume(RabbitMqUtil.QUEUE3,false,consumer);
            //读取消息
            while (true){
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                System.out.println("消费者1，收到消息："+new String(delivery.getBody()));
                //给予不同的线程睡眠时间，模拟不同的效率
                Thread.sleep(1000);
                //3)处理完后手动通知通道 参数1是消息编号；参数2 是否延迟通知
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
