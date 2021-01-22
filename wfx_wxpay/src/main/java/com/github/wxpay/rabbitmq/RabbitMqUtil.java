package com.github.wxpay.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMq消息队列工具类
 */
public class RabbitMqUtil {

    public static final String QUEUE1="queue01";
    public static final String QUEUE2="queue02";
    public static final String QUEUE3="queue03";
    public static final String QUEUE4="queue04";
    public static final String QUEUE5="queue05";

    public static final String EXCHANGE1 = "exchange01";
    public static final String EXCHANGE2 = "exchange02";
    public static final String EXCHANGE3 = "exchange03";

    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("admin");
        factory.setPassword("123456");
        factory.setPort(5672);
        factory.setVirtualHost("host");
        return  factory.newConnection();
    }

}
