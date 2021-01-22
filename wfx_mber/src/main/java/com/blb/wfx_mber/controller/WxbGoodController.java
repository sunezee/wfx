package com.blb.wfx_mber.controller;



import com.blb.wfx_mber.entity.WxbGood;
import com.blb.wfx_mber.service.WxbGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("good")
public class WxbGoodController {

    @Autowired
    private WxbGoodService wxbGoodService;


    //商品列表
    @RequestMapping("goodList")
    public String goodList(Model model, int pageNo,@RequestParam(required = false) String type) {
        wxbGoodService.goodList(model,pageNo,type);
        return "goodList";
    }

    //商品详情
    @RequestMapping("getOne")
    public String getOne(Model model, HttpSession session, String goodId) {
        wxbGoodService.getOne(model,session,goodId);
        return "goodInfo";
    }
    //录单
    @RequestMapping("getOrder")
    public String getOrder(Model model,String goodId) {
        wxbGoodService.getOrder(model,goodId);
        return "order-details";
    }
}
