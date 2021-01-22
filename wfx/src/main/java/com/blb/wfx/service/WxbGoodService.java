package com.blb.wfx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blb.wfx.entity.WxbGood;



public interface WxbGoodService extends IService<WxbGood> {

    boolean change(String goodId,int i);

}
