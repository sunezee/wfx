package com.github.wxpay.rabbitmq.pulibsh_subscribe;

import com.github.wxpay.rabbitmq.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布订阅模式生产者
 */
public class PublishProducer {
    public static void main(String[] args) {
        try {
            //获取连接
            Connection connection = RabbitMqUtil.getConnection();
            //创建隧道
            Channel channel = connection.createChannel();
            //声明交换机 参数1是交换机名称；参数2是交换机类型，fanout是发布订阅模式
            channel.exchangeDeclare(RabbitMqUtil.EXCHANGE1,"fanout");
            //发布消息到队列中  参数2 消息队列名称 参数4 消息内容
            for (int i = 0; i < 10; i++) {
                String msg="消息"+i+"--->hello rabbbitmq!!!";
                //发布消息到交换机中
                channel.basicPublish(RabbitMqUtil.EXCHANGE1,"",null,msg.getBytes());
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
