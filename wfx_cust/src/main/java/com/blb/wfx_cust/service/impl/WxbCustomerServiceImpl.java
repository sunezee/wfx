package com.blb.wfx_cust.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blb.wfx_cust.dao.WxbCustomerMapper;
import com.blb.wfx_cust.entity.WxbCustomer;
import com.blb.wfx_cust.service.WxbCustomerService;
import org.springframework.stereotype.Service;

@Service
public class WxbCustomerServiceImpl extends ServiceImpl<WxbCustomerMapper, WxbCustomer> implements WxbCustomerService {
}
