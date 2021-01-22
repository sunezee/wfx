package com.blb.wfx_mber.utils;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 *  WxbSocket工具类
 */
@ServerEndpoint(value = "/wfx_ws")
@Component
public class WebSocketUtil {

    private static Session session;

    //和前台页面建立连接之后的回调方法
    @OnOpen
    public void onOpen(Session session){
        System.out.println("建立连接："+session);
        //给连接赋值
        this.session=session;
    }

    @OnMessage
    public void onMessage(String message,Session session){
        System.out.println("收到前台消息:"+message);
    }

    //发生错误时回调
    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println("连接关闭");
        session.close();
    }

    /**
     * 向前台发送消息
     * @param message
     */
    public static void sendMessage(String message) throws IOException {
        System.out.println("发送消息："+message);
        if(WebSocketUtil.session!=null){
            WebSocketUtil.session.getBasicRemote().sendText(message);
        }

    }

}
