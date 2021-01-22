package com.blb.wfx_cust.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blb.wfx_cust.entity.User;
import com.blb.wfx_cust.entity.WxbCustomer;
import com.blb.wfx_cust.service.WxbCustomerService;
import com.blb.wfx_cust.utils.CookieUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("cust")
public class WxbCustomerController {

    @Autowired
    private WxbCustomerService wxbCustomerService;

    @ResponseBody
    @PostMapping("login")
    public WxbCustomer login(@RequestBody User user, HttpSession session){
        System.out.println(user);
        UsernamePasswordToken token=new UsernamePasswordToken();
        token.setUsername(user.getUsername());
        token.setPassword(user.getPassword().toCharArray());
        Subject subject= SecurityUtils.getSubject();
//        if(!subject.isAuthenticated()){
        try{
            subject.login(token);
            WxbCustomer user1 = wxbCustomerService.getOne(new QueryWrapper<WxbCustomer>().eq("login_name",user.getUsername()));
            //保存到session
            session.setAttribute("customer",user1);
//            session.setAttribute("customerId",user1.getCustomerId());
//            System.out.println(session.getAttribute("customer"));
            //保存到cookie
//            CookieUtils.save(resp,"customerId",user1.getCustomerId(),3600);
            return user1;
        }catch (Exception e){
            e.printStackTrace();
        }
//        }
        return null;
    }
}
