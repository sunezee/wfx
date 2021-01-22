package com.blb.wfx.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    //首页
    @RequestMapping("index.html")
    public String index(){
        return "index";
    }

    //登录
    @RequestMapping("login.html")
    public String login(){

        return "login";
    }

    //菜单信息管理
    @RequestMapping("module-list")
    public String moduleList(){

        return "moduleList";
    }

    //用户信息管理
    @RequestMapping("user-list")
    public String userList(){
        return "userList";
    }

    //角色信息管理
    @RequestMapping("role-list")
    public String roleList(){
        return "roleList";
    }

    //商户信息管理
    @RequestMapping("cust-list")
    public String custList(){
        return "custList";
    }
}
