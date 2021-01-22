package com.blb.wfx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blb.wfx.dao.WxbGoodTypeMapper;
import com.blb.wfx.entity.ModuleTreeNodes;
import com.blb.wfx.entity.WxbGoodType;
import com.blb.wfx.service.WxbGoodTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxbGoodTypeServiceImpl extends ServiceImpl<WxbGoodTypeMapper, WxbGoodType> implements WxbGoodTypeService {
    @Autowired
    private WxbGoodTypeMapper wxbGoodTypeMapper;

    @Override
    public List<ModuleTreeNodes> getGoodType() {
        return wxbGoodTypeMapper.selectGoodType();
    }
}
