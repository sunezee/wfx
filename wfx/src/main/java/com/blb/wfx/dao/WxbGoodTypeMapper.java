package com.blb.wfx.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blb.wfx.entity.ModuleTreeNodes;
import com.blb.wfx.entity.WxbGoodType;

import java.util.List;

public interface WxbGoodTypeMapper extends BaseMapper<WxbGoodType> {
    /**
     *
     * @return
     */
    List<ModuleTreeNodes> selectGoodType();
}
