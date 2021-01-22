package com.blb.wfx_goods.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blb.wfx_goods.entity.ModuleTreeNodes;
import com.blb.wfx_goods.entity.WxbGoodType;


import java.util.List;

public interface WxbGoodTypeMapper extends BaseMapper<WxbGoodType> {
    /**
     *
     * @return
     */
    List<ModuleTreeNodes> selectGoodType();
}
