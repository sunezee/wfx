package com.blb.wfx_mber.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blb.wfx_mber.config.MyWXPayConfig;
import com.blb.wfx_mber.entity.WxbOrder;
import com.blb.wfx_mber.service.WxbOrderService;
import com.blb.wfx_mber.utils.PublicUtil;
import com.blb.wfx_mber.utils.WebSocketUtil;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.zxing.PayCode;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("order")
public class WxbOrderController {

    @Autowired
    private WxbOrderService wxbOrderService;


    //提交订单
    @RequestMapping("saveOrder")
    public String saveOrder(WxbOrder wxbOrder, HttpSession session){
        //生成订单编号
        wxbOrder.setOrderId(getOrderId());
        //订单生成时间
        Date date=new Date();
        Timestamp timestamp=new Timestamp(date.getTime());
        wxbOrder.setOrderTime(timestamp);
        //自媒体用户id
        String memeberId = (String) session.getAttribute("memeberId");
        wxbOrder.setUserId(memeberId);
        //插入订单表
        wxbOrderService.save(wxbOrder);
        //微信下单
        try {
            WXPay wxPay=new WXPay(new MyWXPayConfig());
            Map<String, String> data = new HashMap<String, String>();
            data.put("body", "微分销系统购物商品");
            data.put("out_trade_no", wxbOrder.getOrderId());//订单号
            data.put("device_info", "PC");
            data.put("fee_type", "CNY");//人民币
            data.put("total_fee", "1");//1分钱
            data.put("spbill_create_ip", "123.12.12.123");
            data.put("notify_url", "http://bm6p5c.natappfree.cc/order/updateOrder");
            data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
            data.put("product_id", wxbOrder.getGoodId());
            Map<String, String> resp = wxPay.unifiedOrder(data);
            System.out.println(resp);
            //用session保存支付连接
            String code_url=resp.get("code_url");
            session.setAttribute("code_url",code_url);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "pay";
    }

    //后台接受队列发送的消息
    @RabbitListener(queues = "wfx")
    public void receiveMQ(String msg){
        System.out.println("收到消息"+msg);
        //发送消息到前台
        try {
            WebSocketUtil.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //订单列表页面
    @RequestMapping("orderList")
    public String orderList(Model model, HttpSession session, int pageNo){
        wxbOrderService.orderList(model,session,pageNo);
        return "orderList";
    }

    /**
     * 生成支付二维码并以流的形式返回页面
     * @param session
     * @param response
     */
    @RequestMapping("payCode")
    public void payCode(HttpSession session, HttpServletResponse response){
        //生成支付二维码
        PayCode payCode=new PayCode();
        payCode.createQrcode(response, (String) session.getAttribute("code_url"));
    }

    /**
     * 随机生成8位数字长度的订单编号
     * @return
     */
    public String getOrderId(){
        String custId="";
        int count=0;
        do{
            custId= PublicUtil.getRondomChangeFirst(8);
            count = wxbOrderService.count(new QueryWrapper<WxbOrder>()
                    .eq("order_id", custId));
        }while (count>0);
        return  custId;
    }


}
