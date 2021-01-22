package com.blb.wfx_cust.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blb.wfx_cust.dao.WxbOrderMapper;
import com.blb.wfx_cust.entity.WxbOrder;
import com.blb.wfx_cust.service.WxbOrderService;
import org.springframework.stereotype.Service;

@Service
public class WxbOrderServiceImpl extends ServiceImpl<WxbOrderMapper, WxbOrder> implements WxbOrderService {
}
