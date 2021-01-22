package com.blb.wfx_login.controller;

import com.blb.wfx_login.entity.WxbCustomer;
import com.blb.wfx_login.entity.WxbMemeber;
import com.blb.wfx_login.mapper.JsonResult;
import com.blb.wfx_login.mapper.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@Controller
@RequestMapping("page")
public class PageController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("login.html")
    public  String login(){
        return "login";
    }

    @ResponseBody
    @RequestMapping("login")
    public JsonResult loginTo(User user){
        if(user.getLoginType()==0){
            String url="http://wfx-mber/mber/login";
            WxbMemeber mber = restTemplate.postForObject(url, user, WxbMemeber.class);
            System.out.println(mber);
            return  new JsonResult(200,"success",mber.getMemeberId());
        }else{
            String url="http://wfx-cust/cust/login";
            WxbCustomer cust = restTemplate.postForObject(url, user, WxbCustomer.class);
//            System.out.println("page--->"+cust);
            return  new JsonResult(200,"success",cust.getCustomerId());
        }
    }
}
