package com.github.wxpay.rabbitmq.work;

import com.github.wxpay.rabbitmq.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * work多对多模式生产者
 */
public class WorkProducer {
    public static void main(String[] args) {
        try {
            //获取连接
            Connection connection = RabbitMqUtil.getConnection();
            //创建隧道
            Channel channel = connection.createChannel();
            //定义消息队列
            channel.queueDeclare(RabbitMqUtil.QUEUE2,false,false,false,null);
            //发布消息到队列中  参数2 消息队列名称 参数4 消息内容
            for (int i = 0; i < 10; i++) {
                String msg="消息"+i+"--->hello rabbbitmq!!!";
                channel.basicPublish("",RabbitMqUtil.QUEUE2,null,msg.getBytes());
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
