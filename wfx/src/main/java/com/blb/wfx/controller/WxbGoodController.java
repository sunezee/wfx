package com.blb.wfx.controller;


import com.blb.wfx.entity.JsonResult;
import com.blb.wfx.feign.GoodsFeignClients;
import com.blb.wfx.service.WxbGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;


@Controller
@RequestMapping("good")
public class WxbGoodController {

    @Autowired
    private WxbGoodService wxbGoodService;
    @Autowired
    private GoodsFeignClients goodsFeignClients;

    @RequestMapping("goodList")
    public String getGoodList(int pageNo, Model model,@RequestParam(required = false) String typeName){
//        IPage<WxbGood> goodIPage=new Page<>();
//        goodIPage.setCurrent(pageNo);
//        goodIPage.setSize(15);
//        IPage<WxbGood> page=null;
//        if(typeName==""||typeName==null){
//            page = wxbGoodService.page(goodIPage);
//        }else{
//            page = wxbGoodService.page(goodIPage,new QueryWrapper<WxbGood>()
//                    .eq("type_id",typeName));
//        }
        if(typeName==""||typeName==null){
            typeName="A";
        }
        //feign远程调用
        JsonResult goodList = goodsFeignClients.getGoodList(pageNo, typeName);
        //使用LinkedHashMap接受page对象
        LinkedHashMap goods= (LinkedHashMap) goodList.getData();
        model.addAttribute("goods",goods);
        return "goodList";
    }

    //置顶，推荐，上下架
    @ResponseBody
    @RequestMapping("change")
    public JsonResult change(String goodId, int i){
        try{
//            wxbGoodService.change(goodId,i);
//            return new JsonResult(200,"success","http://localhost:8484/good/goodList?pageNo=1");
            JsonResult result = goodsFeignClients.change(goodId, i);
            return  result;
        }catch (Exception e){
            return new JsonResult(500,"failed",e.getMessage());
        }
    }
}
