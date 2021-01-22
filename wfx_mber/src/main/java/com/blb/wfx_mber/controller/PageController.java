package com.blb.wfx_mber.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("mberpage")
public class PageController {

    //首页
    @RequestMapping("index.html")
    public String index(String memeberId, HttpSession session) {
        System.out.println(memeberId);
        session.setAttribute("memeberId",memeberId);
        return "index";
    }

    //登录
    @RequestMapping("login.html")
    public String login(){

        return "login";
    }
    //我的订单
    //登录
    @RequestMapping("orderList.html")
    public String orderList(){
        return "orderList";
    }
}
