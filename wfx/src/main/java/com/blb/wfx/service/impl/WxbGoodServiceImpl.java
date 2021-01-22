package com.blb.wfx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blb.wfx.dao.WxbGoodMapper;
import com.blb.wfx.entity.WxbGood;
import com.blb.wfx.service.WxbGoodService;
import com.blb.wfx.utils.PublicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service
public class WxbGoodServiceImpl extends ServiceImpl<WxbGoodMapper, WxbGood> implements WxbGoodService {


    @Autowired
    private WxbGoodMapper wxbGoodMapper;

    @Transactional
    @Override
    public boolean change(String goodId,int i) {
        String[] strings = PublicUtil.stringToArray(goodId, ",");
        for (int j = 0; j <strings.length ; j++) {
            if(StringUtils.isEmpty(strings[j])){
                continue;
            }
            switch (i){
                case 1:
                    wxbGoodMapper.goodTop(strings[j]);
                    break;
                case 2:
                    wxbGoodMapper.goodNotTop(strings[j]);
                    break;
                case 3:
                    wxbGoodMapper.goodRecomed(strings[j]);
                    break;
                case 4:
                    wxbGoodMapper.goodNotRecomed(strings[j]);
                    break;
                case 5:
                    wxbGoodMapper.goodState(strings[j]);
                    break;
                case 6:
                    wxbGoodMapper.goodNotState(strings[j]);
                    break;
                default:
                    break;
            }
        }
        return false;
    }
}
