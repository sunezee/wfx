package com.blb.wfx_goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blb.wfx_goods.entity.WxbGood;


public interface WxbGoodService extends IService<WxbGood> {

    boolean change(String goodId, int i);

}
