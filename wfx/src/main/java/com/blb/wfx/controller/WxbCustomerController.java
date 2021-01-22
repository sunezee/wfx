package com.blb.wfx.controller;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blb.wfx.entity.JsonResult;
import com.blb.wfx.entity.WxbCustomer;
import com.blb.wfx.service.WxbCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("cust")
public class WxbCustomerController {
    @Autowired
    private WxbCustomerService wxbCustomerService;

    /**
     *分页展示角色
     * @return
     */
    @RequestMapping("cust-page")
    public String pageCust(int pageNo, Model model){
        IPage<WxbCustomer> custIPage=new Page<>();
        custIPage.setCurrent(pageNo);
        custIPage.setSize(15);
        IPage<WxbCustomer> page = wxbCustomerService.page(custIPage);
        model.addAttribute("custs",page);
        return "custList";
    }

    //添加或修改商户
    @ResponseBody
    @RequestMapping("addOrUpdateCust")
    public JsonResult addOrUpdateCust(WxbCustomer wxbCustomer){
        try{
            if(StringUtils.isEmpty(wxbCustomer.getCustomerId())){
                wxbCustomerService.addCust(wxbCustomer);
                return new JsonResult(200,"success",null);
            }else{
                wxbCustomerService.updateCust(wxbCustomer);
                return new JsonResult(200,"success",null);
            }
        }catch (Exception e){
            return new JsonResult(500,"failed",e.getMessage());
        }
    }

    //删除商户
    @ResponseBody
    @RequestMapping("delete")
    public JsonResult deleteCust(String customerId){
        try{
            boolean b = wxbCustomerService.removeById(customerId);
            if(b){
                return new JsonResult(200,"success",null);
            }else{
                return new JsonResult(400,"failed",null);
            }
        }catch (Exception e){
            return new JsonResult(500,"failed",e.getMessage());
        }
    }

    //查询商户
    @ResponseBody
    @RequestMapping("getOne")
    public JsonResult getOneCust(String customerId){
        try{
            WxbCustomer wxbCustomer = wxbCustomerService.getById(customerId);
            return new JsonResult(200,"success",wxbCustomer);
        }catch (Exception e){
            return new JsonResult(500,"failed",e.getMessage());
        }
    }



}
