package com.blb.wfx_mber.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blb.wfx_mber.entity.WxbOrder;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public interface WxbOrderService extends IService<WxbOrder> {

    /**
     * 订单列表
     */
    void orderList(Model model, HttpSession session,int pageNo);
}
