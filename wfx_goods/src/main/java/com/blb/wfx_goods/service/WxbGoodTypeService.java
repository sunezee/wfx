package com.blb.wfx_goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blb.wfx_goods.entity.ModuleTreeNodes;
import com.blb.wfx_goods.entity.WxbGoodType;


import java.util.List;

public interface WxbGoodTypeService extends IService<WxbGoodType> {

    /**
     *
     * @return
     */
    List<ModuleTreeNodes> getGoodType();
}
