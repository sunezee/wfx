package com.blb.wfx_mber.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blb.wfx_mber.dao.*;
import com.blb.wfx_mber.entity.*;
import com.blb.wfx_mber.service.WxbGoodService;
import com.blb.wfx_mber.utils.PublicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class WxbGoodServiceImpl extends ServiceImpl<WxbGoodMapper, WxbGood> implements WxbGoodService {

    @Autowired
    private WxbGoodMapper wxbGoodMapper;
    @Autowired
    private WxbCustomerMapper wxbCustomerMapper;
    @Autowired
    private WxbGoodSku2Mapper wxbGoodSku2Mapper;
    @Autowired
    private WxbGoodTypeMapper wxbGoodTypeMapper;
    @Autowired
    private WxbChannelMapper wxbChannelMapper;

    @Transactional
    @Override
    public boolean goodList(Model model, int pageNo,String type) {
        //商户
        List<WxbCustomer> wxbCustomers = wxbCustomerMapper.selectList(new QueryWrapper<>());
        model.addAttribute("custs",wxbCustomers);
        //商品类型
        List<WxbGoodType> wxbGoodTypes = wxbGoodTypeMapper.selectList(new QueryWrapper<>());
        model.addAttribute("goodtypes",wxbGoodTypes);
        //商品
        IPage<WxbGood> goodIPage=new Page<>();
        goodIPage.setCurrent(pageNo);
        goodIPage.setSize(10);
        IPage<WxbGood> goodList=null;
        //按条件查询
        if(!StringUtils.isEmpty(type)){
            String[] strings = PublicUtil.stringToArray(type, ",");
            if(strings[1].equals("A")){
                //按商户
                goodList = wxbGoodMapper.selectPage(goodIPage, new QueryWrapper<WxbGood>()
                        .eq("state", "1")
                        .eq("customer_id",strings[0]));
            }else if(strings[1].equals("B")){
                //按商品类型
                goodList = wxbGoodMapper.selectPage(goodIPage, new QueryWrapper<WxbGood>()
                        .eq("state", "1")
                        .eq("type_id",strings[0]));
            }else{
                //排序
                if(strings[0].equals("toped")){
                    //按置顶排序
                    goodList = wxbGoodMapper.selectPage(goodIPage, new QueryWrapper<WxbGood>()
                            .eq("state", "1").orderByAsc("toped"));
                }else if(strings[0].equals("recommed")){
                    //按推荐排序
                    goodList = wxbGoodMapper.selectPage(goodIPage, new QueryWrapper<WxbGood>()
                            .eq("state", "1").orderByAsc("recomed"));
                }else{
                    //按商户登记排序，还没完成,此处是假的
                    goodList = wxbGoodMapper.selectPage(goodIPage, new QueryWrapper<WxbGood>()
                            .eq("state", "1").orderByAsc("customer_id"));
                }
            }
        }else{
            //不按任条件
            goodList = wxbGoodMapper.selectPage(goodIPage, new QueryWrapper<WxbGood>()
                    .eq("state", "1"));
        }
        //查空直接返回
        if(goodList==null){
            model.addAttribute("goods",goodList);
            return true;
        }
        //不为空设置标签和套餐
        List<WxbGood> records = goodList.getRecords();
        for (WxbGood w :records
                ) {
            //设置商品标签集合
            List<String> tagList=new ArrayList<>();
            String[] tag = PublicUtil.stringToArray(w.getTags(), ",");
            for (int i = 0; i < tag.length; i++) {
                if(StringUtils.isEmpty(tag[i])){
                    continue;
                }
                tagList.add(tag[i]);
            }
            w.setTagList(tagList);
            //设置商品套餐集合
            List<WxbGoodSku2> sku2List = wxbGoodSku2Mapper.selectList(new QueryWrapper<WxbGoodSku2>()
                    .eq("good_id", w.getGoodId()));
            w.setSku2List(sku2List);
        }
        goodList.setRecords(records);
        model.addAttribute("goods",goodList);
        return true;
    }

    @Transactional
    @Override
    public boolean getOne(Model model,HttpSession session, String goodId) {
        //商品信息
        WxbGood wxbGood = wxbGoodMapper.selectById(goodId);
        //套餐信息
        List<WxbGoodSku2> sku2List = wxbGoodSku2Mapper.selectList(new QueryWrapper<WxbGoodSku2>()
                .eq("good_id",goodId));
        wxbGood.setSku2List(sku2List);
        model.addAttribute("good",wxbGood);
        //商户信息
        WxbCustomer wxbCustomer = wxbCustomerMapper.selectById(wxbGood.getCustomerId());
        model.addAttribute("cust",wxbCustomer);
        //推广渠道
        String memeberId = (String) session.getAttribute("memeberId");
        List<WxbChannel> channelList = wxbChannelMapper.selectList(new QueryWrapper<WxbChannel>()
                .eq("channel_uid", memeberId));
        model.addAttribute("channelList",channelList);
        return true;
    }

    @Transactional
    @Override
    public boolean getOrder(Model model, String goodId) {
        //商品信息
        WxbGood wxbGood = wxbGoodMapper.selectById(goodId);
        //套餐信息
        List<WxbGoodSku2> sku2List = wxbGoodSku2Mapper.selectList(new QueryWrapper<WxbGoodSku2>()
                .eq("good_id",goodId));
        wxbGood.setSku2List(sku2List);
        model.addAttribute("good",wxbGood);
        return true;
    }
}
