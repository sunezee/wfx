package com.blb.wfx_goods.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blb.wfx_goods.entity.JsonResult;
import com.blb.wfx_goods.entity.WxbGood;
import com.blb.wfx_goods.service.WxbGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("good")
public class WxbGoodController {

    @Autowired
    private WxbGoodService wxbGoodService;

    @RequestMapping("goodList/{pageNo}/{typeName}")
    public JsonResult getGoodList(@PathVariable("pageNo") int pageNo,@PathVariable("typeName") String typeName){
        try{
            IPage<WxbGood> goodIPage=new Page<>();
            goodIPage.setCurrent(pageNo);
            goodIPage.setSize(15);
            IPage<WxbGood> page=null;
            if("A".equals(typeName)){
                page = wxbGoodService.page(goodIPage,new QueryWrapper<>());
            }else{
                page = wxbGoodService.page(goodIPage,new QueryWrapper<WxbGood>()
                        .eq("type_id",typeName));
            }
            return new JsonResult(200,"success",page);
        }catch (Exception e){
            return new JsonResult(500,"failed",e.getMessage());
        }
    }

    //置顶，推荐，上下架
    @RequestMapping("change/{goodId}/{i}")
    public JsonResult change(@PathVariable("goodId") String goodId,@PathVariable("i") int i){
        try{
            wxbGoodService.change(goodId, i);
            return new JsonResult(200,"success","http://localhost:8484/good/goodList?pageNo=1");
        }catch (Exception e){
            return new JsonResult(500,"failed",e.getMessage());
        }
    }
}
