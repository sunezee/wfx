package com.blb.wfx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blb.wfx.entity.WxbCustomer;

public interface WxbCustomerService extends IService<WxbCustomer> {

    /**
     * 添加新商户
     * @param wxbCustomer
     * @return
     */
    boolean addCust(WxbCustomer wxbCustomer);

    /**
     * 更新商户信息
     * @param wxbCustomer
     * @return
     */
    boolean updateCust(WxbCustomer wxbCustomer);


}
