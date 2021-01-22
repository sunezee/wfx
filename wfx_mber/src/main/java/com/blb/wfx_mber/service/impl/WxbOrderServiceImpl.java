package com.blb.wfx_mber.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blb.wfx_mber.dao.WxbGoodMapper;
import com.blb.wfx_mber.dao.WxbGoodSku2Mapper;
import com.blb.wfx_mber.dao.WxbOrderMapper;
import com.blb.wfx_mber.entity.WxbGood;
import com.blb.wfx_mber.entity.WxbGoodSku2;
import com.blb.wfx_mber.entity.WxbMemeber;
import com.blb.wfx_mber.entity.WxbOrder;
import com.blb.wfx_mber.service.WxbOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class WxbOrderServiceImpl extends ServiceImpl<WxbOrderMapper, WxbOrder> implements WxbOrderService {

    @Autowired
    private WxbOrderMapper wxbOrderMapper;
    @Autowired
    private WxbGoodMapper wxbGoodMapper;
    @Autowired
    private WxbGoodSku2Mapper wxbGoodSku2Mapper;

    @Transactional
    @Override
    public void orderList(Model model, HttpSession session, int pageNo) {
        String memeberId = (String) session.getAttribute("memeberId");
        IPage<WxbOrder> orderIPage=new Page<>();
        orderIPage.setCurrent(pageNo);
        orderIPage.setSize(10);
        IPage<WxbOrder> orderIPage1 = wxbOrderMapper.selectPage(orderIPage, new QueryWrapper<WxbOrder>()
                .eq("user_id",memeberId));
        List<WxbOrder> records = orderIPage1.getRecords();
        for (WxbOrder w:records
             ) {
            //查询订单商品套餐
            WxbGoodSku2 sku = wxbGoodSku2Mapper.selectOne(new QueryWrapper<WxbGoodSku2>()
                    .eq("sku_id", w.getSkuId()));
            w.setWxbGoodSku2(sku);
            //查询订单商品
            WxbGood good1 = wxbGoodMapper.selectOne(new QueryWrapper<WxbGood>()
                    .eq("good_id", w.getGoodId()));
           w.setWxbGood(good1);
        }
        orderIPage1.setRecords(records);
        model.addAttribute("orderList",orderIPage1);
    }
}
