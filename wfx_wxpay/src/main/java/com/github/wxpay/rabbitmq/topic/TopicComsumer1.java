package com.github.wxpay.rabbitmq.topic;

import com.github.wxpay.rabbitmq.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 主题模式的消费者，
 * 选择读取特定key的消息
 */
public class TopicComsumer1 {
    public static void main(String[] args) {
        try {
            //获取连接
            Connection connection = RabbitMqUtil.getConnection();
            //创建通道
            Channel channel = connection.createChannel();
            //定义队列
            channel.queueDeclare(RabbitMqUtil.QUEUE1,false,false,false,null);
            //声明交换机
            channel.exchangeDeclare(RabbitMqUtil.EXCHANGE3,"topic");
            //绑定队列到交换机中 参数1，队列名；参数2，交换机名称,参数3，key
            channel.queueBind(RabbitMqUtil.QUEUE1,RabbitMqUtil.EXCHANGE3,"*.com.cn");
            //1）让通道一次只发送一个任务
            channel.basicQos(1);
            //创建消费者
            QueueingConsumer consumer=new QueueingConsumer(channel);
            //从通道获取消息 true是自动确认模式，2)参数2更改为false,是手动确认模式（消费者主动通知通道）
            channel.basicConsume(RabbitMqUtil.QUEUE1,false,consumer);
            //读取消息
            while (true){
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                System.out.println("消费者1，收到消息："+new String(delivery.getBody()));
                //给予不同的线程睡眠时间，模拟不同的效率
                Thread.sleep(10);
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
