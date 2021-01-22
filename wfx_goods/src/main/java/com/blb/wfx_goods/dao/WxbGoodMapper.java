package com.blb.wfx_goods.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blb.wfx_goods.entity.WxbGood;


public interface WxbGoodMapper extends BaseMapper<WxbGood> {
    //置顶
    boolean goodTop(String goodId);
    //取消置顶
    boolean goodNotTop(String goodId);
    //推荐
    boolean goodRecomed(String goodId);
    //取消推荐
    boolean goodNotRecomed(String goodId);
    //上架
    boolean goodState(String goodId);
    //下架
    boolean goodNotState(String goodId);

}
