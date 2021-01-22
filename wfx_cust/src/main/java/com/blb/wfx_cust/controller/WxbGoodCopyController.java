package com.blb.wfx_cust.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blb.wfx_cust.entity.WxbCustomer;
import com.blb.wfx_cust.entity.WxbGoodCopy;
import com.blb.wfx_cust.service.WxbGoodCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("copy")
public class WxbGoodCopyController {

    @Autowired
    private WxbGoodCopyService wxbGoodCopyService;

    @RequestMapping("copyList")
    public String copyList(int pageNo, Model model, HttpSession session){
        IPage<WxbGoodCopy> custIPage=new Page<>();
        custIPage.setCurrent(pageNo);
        custIPage.setSize(15);
        String customerId = (String) session.getAttribute("customerId");
        IPage<WxbGoodCopy> page = wxbGoodCopyService.page(custIPage,new QueryWrapper<WxbGoodCopy>()
                .eq("customer_id",customerId));
        model.addAttribute("copys",page);
        return "goodCopy";
    }


}
