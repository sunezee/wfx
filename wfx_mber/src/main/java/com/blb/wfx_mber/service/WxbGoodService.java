package com.blb.wfx_mber.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blb.wfx_mber.entity.WxbGood;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public interface WxbGoodService extends IService<WxbGood> {

    /**
     * 商品列表
     * @param model
     * @param pageNo
     * @param type
     * @return
     */
    boolean goodList(Model model,int pageNo,String type);

    /**
     * 商品详情
     * @param goodId
     * @return
     */
    boolean getOne(Model model,HttpSession session,String goodId);

    /**
     * 录单
     * @param model
     * @param goodId
     * @return
     */
    boolean getOrder(Model model,String goodId);
}
