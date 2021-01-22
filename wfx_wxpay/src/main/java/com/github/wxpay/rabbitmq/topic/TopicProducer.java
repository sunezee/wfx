package com.github.wxpay.rabbitmq.topic;

import com.github.wxpay.rabbitmq.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 主题模式生产者
 * 可以对消息设置某些特定的key,消费者只对某些key的消息进行处理
 */
public class TopicProducer {
    public static void main(String[] args) {
        try {
            //获取连接
            Connection connection = RabbitMqUtil.getConnection();
            //创建隧道
            Channel channel = connection.createChannel();
            //声明交换机 参数1是交换机名称；参数2是交换机类型，direct支持路由模式
            channel.exchangeDeclare(RabbitMqUtil.EXCHANGE3,"topic");
            //发布消息到队列中  参数2 消息队列名称 参数4 消息内容
            for (int i = 0; i < 10; i++) {
                String msg="消息"+i+"--->hello rabbbitmq!!!";
                String key="hello.com.cn";
                //发布消息到交换机中，参数1，交换机名称；参数2，key
                channel.basicPublish(RabbitMqUtil.EXCHANGE3,key,null,msg.getBytes());
            }
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
