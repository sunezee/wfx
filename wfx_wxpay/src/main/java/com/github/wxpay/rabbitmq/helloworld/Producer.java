package com.github.wxpay.rabbitmq.helloworld;

import com.github.wxpay.rabbitmq.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 */
public class Producer {
    public static void main(String[] args) {
        try {
            //获取连接
            Connection connection = RabbitMqUtil.getConnection();
            //创建隧道
            Channel channel = connection.createChannel();
            //定义消息队列
            channel.queueDeclare(RabbitMqUtil.QUEUE1,false,false,false,null);
            //发布消息到队列中  参数2 消息队列名称 参数4 消息内容
            String msg="hello rabbbitmq!!!";
            channel.basicPublish("",RabbitMqUtil.QUEUE1,null,msg.getBytes());
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
