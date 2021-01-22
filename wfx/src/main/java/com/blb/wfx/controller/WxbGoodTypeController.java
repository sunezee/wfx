package com.blb.wfx.controller;

import com.blb.wfx.entity.JsonResult;
import com.blb.wfx.entity.ModuleTreeNodes;
import com.blb.wfx.feign.GoodsFeignClients;
import com.blb.wfx.service.WxbGoodTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("goodType")
public class WxbGoodTypeController {

    @Autowired
    private WxbGoodTypeService wxbGoodTypeService;
    @Autowired
    private GoodsFeignClients goodsFeignClients;

    @ResponseBody
    @RequestMapping("typeList")
    public JsonResult getGoodType(){
        try{
//            List<ModuleTreeNodes> goodTypes = wxbGoodTypeService.getGoodType();
//            for (ModuleTreeNodes goodType:goodTypes
//                 ) {
//                goodType.setText("商品分类");
//                goodType.setHref("/good/goodList?pageNo=1");
//            }
//            List<ModuleTreeNodes> goodTypes1=new ArrayList<>();
//            goodTypes1.add(goodTypes.get(0));
//            return new JsonResult(200,"success",goodTypes1);
            JsonResult result = goodsFeignClients.getGoodType();
            return  result;
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(500,"failed",e.getMessage());
        }

    }
}
