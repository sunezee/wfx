package com.blb.wfx.feign;

import com.blb.wfx.entity.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("wfx-goods")
public interface GoodsFeignClients {


    /**
     * 管理员商品列表调用
     * @param pageNo
     * @param typeName
     * @return
     */
    @RequestMapping("/good/goodList/{pageNo}/{typeName}")
    JsonResult getGoodList(@PathVariable("pageNo") int pageNo, @PathVariable("typeName") String typeName);

    /**
     * 管理员商品置顶，推荐，上下架调用
     * @param goodId
     * @param i
     * @return
     */
    @RequestMapping("/good/change/{goodId}/{i}")
    JsonResult change(@PathVariable("goodId") String goodId,@PathVariable("i") int i);

    /**
     * 商品管理之商品分类
     * @return
     */
    @RequestMapping("/goodType/typeList")
    JsonResult getGoodType();
}
