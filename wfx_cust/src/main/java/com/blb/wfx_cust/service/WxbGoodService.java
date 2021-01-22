package com.blb.wfx_cust.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blb.wfx_cust.entity.WxbGood;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public interface WxbGoodService extends IService<WxbGood> {
    /**
     * 添加商品的页面时需要提供的可供选择的一些数据
     */
    void addGood(Model model, HttpSession session);

    /**
     * 保存商品
     * @param wxbGood
     * @return
     */
    boolean saveGood(WxbGood wxbGood);

    /**
     * 删除商品
     * @param goodId
     * @return
     */
    boolean removeGood(String goodId);

    /**
     * 查询一个商品
     * @param goodId
     */
    void getOneGood(String goodId,Model model,HttpSession session);

    /**
     * 更新商品
     * @param wxbGood
     * @return
     */
    boolean updateGood(WxbGood wxbGood);

}
