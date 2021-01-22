package com.blb.wfx_mber.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blb.wfx_mber.entity.User;
import com.blb.wfx_mber.entity.WxbMemeber;
import com.blb.wfx_mber.service.WxbMemeberService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("mber")
public class WxbMemeberController {

    @Autowired
    private WxbMemeberService wxbMemeberService;

    @ResponseBody
    @PostMapping("login")
    public WxbMemeber login(@RequestBody User user, HttpSession session){

        UsernamePasswordToken token=new UsernamePasswordToken();
        token.setUsername(user.getUsername());
        token.setPassword(user.getPassword().toCharArray());
        Subject subject= SecurityUtils.getSubject();
//        if(!subject.isAuthenticated()){
        try{
            subject.login(token);
            WxbMemeber user1 = wxbMemeberService.getOne(new QueryWrapper<WxbMemeber>().eq("account", user.getUsername()));
            session.setAttribute("memeber",user1);
            System.out.println(session.getAttribute("memeber"));
            return user1;
        }catch (Exception e){
            e.printStackTrace();
        }
//        }
        return null;
    }
}
