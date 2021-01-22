package com.blb.wfx_cust.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blb.wfx_cust.entity.WxbOrder;
import com.blb.wfx_cust.service.WxbOrderService;
import com.github.wxpay.sdk.WXPayUtil;
import io.goeasy.GoEasy;
import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("order")
public class WxbOrderController {

    @Autowired
    private WxbOrderService wxbOrderService;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    //微信支付成功后返回更改订单状态
    @RequestMapping("updateOrder")
    public void updateOrder(HttpServletResponse response, HttpServletRequest request) throws Exception {
        //从微信读取数据
        String info = Streams.asString(request.getInputStream());
        System.out.println(info);
        //转换成map集合
        Map<String, String> map = WXPayUtil.xmlToMap(info);
        System.out.println(map);
        //取出订单号
        String no = map.get("out_trade_no");
        System.out.println("订单号："+no);
        WxbOrder order = wxbOrderService.getOne(new QueryWrapper<WxbOrder>().eq("order_id", no));
        order.setState(1L);
        wxbOrderService.update(order,new QueryWrapper<WxbOrder>().eq("order_id", no));
        System.out.println("更新订单状态成功");
        //返回结果xml
        String xml= "<xml>"+
                       "<return_code><![CDATA[SUCCESS]]></return_code>"+
                       "<return_msg><![CDATA[OK]]></return_msg>"+
                       "<appid><![CDATA["+map.get("appid")+"]]></appid>"+
                       "<mch_id><![CDATA["+map.get("mch_id")+"]]></mch_id>"+
                       "<nonce_str><![CDATA["+map.get("nonce_str")+"]]></nonce_str>"+
                       "<openid><![CDATA["+map.get("openid")+"]]></openid>"+
                       "<sign><![CDATA["+map.get("sign")+"]]></sign>"+
                       "<result_code><![CDATA[SUCCESS]]></result_code>"+
                       "<prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id>"+
                       "<trade_type><![CDATA[NATIVE]]></trade_type>"+
                       "</xml>";
        response.getWriter().print(xml);
        //使用RabbitMQ推送消息到自媒体系统
        //参数1是队列名称，在配置类中定义了；参数2是发送的内容
        rabbitTemplate.convertAndSend("wfx","ok");
        //使用第三方推送平台geeasy推送消息到自媒体系统
//        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io/publish", "BC-35b2f3f55f644e178eb49c6eda5b5c8b");
//                goEasy.publish("wfx", "OK");


    }


}
