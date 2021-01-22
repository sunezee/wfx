package com.blb.wfx_cust.controller;

import com.blb.wfx_cust.entity.WxbCustomer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("custpage")
public class PageController {

    //首页
    @RequestMapping("index.html")
    public String index(String customerId,HttpSession session) {
        System.out.println("page-->"+customerId);
        session.setAttribute("customerId",customerId);
        return "index";
    }

    //登录
    @RequestMapping("login.html")
    public String login(){

        return "login";
    }
    //添加商品
    @RequestMapping("addGood.html")
    public String addGood(){

        return "addGood";
    }

    //商品文案管理
    @RequestMapping("goodCopy.html")
    public String goodCopy(){

        return "goodCopy";
    }



}
