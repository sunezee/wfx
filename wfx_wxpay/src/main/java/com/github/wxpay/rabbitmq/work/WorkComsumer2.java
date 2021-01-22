package com.github.wxpay.rabbitmq.work;

import com.github.wxpay.rabbitmq.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * work模式的消费者
 * 默认情况下消费者以轮询的方式消费信息，速度快的消费者和速度慢的消费者，处理任务数一样，效率不高
 * 但下面的有标号的三步将更改为主动确认方式，效率高的将可以处理更多的消息
 */
public class WorkComsumer2 {
    public static void main(String[] args) {
        try {
            //获取连接
            Connection connection = RabbitMqUtil.getConnection();
            //创建通道
            Channel channel = connection.createChannel();
            //定义队列
            channel.queueDeclare(RabbitMqUtil.QUEUE2,false,false,false,null);
            //1）让通道一次只发送一个任务
            channel.basicQos(1);
            //创建消费者
            QueueingConsumer consumer=new QueueingConsumer(channel);
            //从通道获取消息 true是自动确认模式，2）false是手动确认模式（消费者主动通知通道）
            channel.basicConsume(RabbitMqUtil.QUEUE2,false,consumer);
            //读取消息
            while (true){
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                System.out.println("消费者2，收到消息："+new String(delivery.getBody()));
                //给予不同的线程睡眠时间，模拟不同的效率
                Thread.sleep(100);
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
