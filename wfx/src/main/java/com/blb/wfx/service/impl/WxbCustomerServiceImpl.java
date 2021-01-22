package com.blb.wfx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blb.wfx.dao.*;
import com.blb.wfx.entity.*;
import com.blb.wfx.service.WxbCustomerService;
import com.blb.wfx.utils.PublicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class WxbCustomerServiceImpl extends ServiceImpl<WxbCustomerMapper, WxbCustomer> implements WxbCustomerService {

    @Autowired
    private WxbCustomerMapper wxbCustomerMapper;

    @Override
    public boolean addCust(WxbCustomer wxbCustomer) {
        //设置商户id
        wxbCustomer.setCustomerId(getCustomerId());
        //设置创建时间
        Date date=new Date();
        Timestamp timestamp=new Timestamp(date.getTime());
        wxbCustomer.setCreatetime(timestamp);
        //设置密码
        String md5 = PublicUtil.encryption("md5", wxbCustomer.getLoginPwd(), wxbCustomer.getLoginName(), 5);
        wxbCustomer.setLoginPwd(md5);
        //设置更新时间
        wxbCustomer.setUpdateTime(null);
        wxbCustomer.setAccBalance(0);
        wxbCustomer.setWebsite(null);
        int insert = wxbCustomerMapper.insert(wxbCustomer);
        if(insert>0){
            return  true;
        }
        return false;
    }

    @Override
    public boolean updateCust(WxbCustomer wxbCustomer) {
        //设置更新时间
        Date date=new Date();
        Timestamp timestamp=new Timestamp(date.getTime());
        wxbCustomer.setUpdateTime(timestamp);
        //设置密码
        String md5 = PublicUtil.encryption("md5", wxbCustomer.getLoginPwd(), wxbCustomer.getLoginName(), 5);
        wxbCustomer.setLoginPwd(md5);
        wxbCustomer.setAccBalance(0);
        wxbCustomer.setWebsite(null);
        int i = wxbCustomerMapper.updateById(wxbCustomer);
        if(i>0){
            return true;
        }
        return false;
    }

    /**
     * 随机生成8位数字长度的商户id
     * @return
     */
    public String getCustomerId(){
        String custId="";
        int count=0;
        do{
            custId= PublicUtil.getRondomChangeFirst(8);
            count = wxbCustomerMapper.selectCount(new QueryWrapper<WxbCustomer>()
                    .eq("customer_id", custId));
        }while (count>0);
        return  custId;
    }
}
