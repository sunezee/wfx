package com.blb.wfx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blb.wfx.entity.ModuleTreeNodes;
import com.blb.wfx.entity.WxbGoodType;

import java.util.List;

public interface WxbGoodTypeService extends IService<WxbGoodType> {

    /**
     *
     * @return
     */
    List<ModuleTreeNodes> getGoodType();
}
