package com.blb.wfx_cust.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blb.wfx_cust.entity.JsonResult;
import com.blb.wfx_cust.entity.WxbCustomer;
import com.blb.wfx_cust.entity.WxbGood;
import com.blb.wfx_cust.service.WxbGoodService;
import com.blb.wfx_cust.utils.CookieUtils;
import com.blb.wfx_cust.utils.FtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;

@Controller
@RequestMapping("wxbgood")
public class WxbGoodController {

    @Value("${FTP.ADDRESS}")
    private String host;
    // 端口
    @Value("${FTP.PORT}")
    private int port;
    // ftp用户名
    @Value("${FTP.USERNAME}")
    private String userName;
    // ftp用户密码
    @Value("${FTP.PASSWORD}")
    private String passWord;
    // 文件在服务器端保存的主目录
    @Value("${FTP.BASEPATH}")
    private String basePath;
    // 访问图片时的基础url
    @Value("${IMAGE.BASE.URL}")
    private String baseUrl;

    @Autowired
    private WxbGoodService wxbGoodService;

    //展示所有商品
    @RequestMapping("good-list")
    public String goodList(int pageNo, Model model, HttpSession session){
        IPage<WxbGood> custIPage=new Page<>();
        custIPage.setCurrent(pageNo);
        custIPage.setSize(15);
        //从seeeion读取
        String customerId = (String) session.getAttribute("customerId");
//        System.out.println("good-->"+customerId);
        //从cookie读取
//        String customerId = CookieUtils.read(req, "customerId");
//        System.out.println(customerId);
        IPage<WxbGood> page = wxbGoodService.page(custIPage,new QueryWrapper<WxbGood>()
                .eq("customer_id",customerId));
        model.addAttribute("goods",page);
        return "goodList";
    }

    //跳转到添加商品页面
    @RequestMapping("addGood")
    public String addGood(Model model,HttpSession session){
        wxbGoodService.addGood(model,session);
        return "addGood";
    }

    //添加商品页面上传图片到nginx
    @ResponseBody
    @RequestMapping("uploadImage")
    public JsonResult uploadImage(@RequestParam(value = "uploadGood",required = false) MultipartFile file){
        try{
            String filename="";
            if(file!=null){
                //获取原文件名
                filename=file.getOriginalFilename();
                //获取原文件名后缀
                String suffix=filename.substring(filename.lastIndexOf("."));
                //以系统毫秒数创建新文件名
                filename=System.currentTimeMillis()+suffix;
                //获取输入文件流
                InputStream input=file.getInputStream();
                //生成文件在服务器存储的子目录
                String filePath ="xx";
                //调用ftpUtils工具类进行上传
                boolean result = FtpUtil.uploadFile(host, port, userName, passWord, basePath, filePath, filename, input);
                if(result){
                    System.out.println("上传成功");
//                    System.out.println(proDisplay);
                }else {
                    System.out.println("上传失败");
                }
            }
            return new JsonResult(200,"success",baseUrl+"/"+filename);
        }catch (Exception e){
            return  new JsonResult(500,"failed",e.getMessage());
        }
    }

    //添加保存商品
    @ResponseBody
    @RequestMapping("saveGood")
    public JsonResult saveGood(WxbGood wxbGood){
        try{
            boolean b = wxbGoodService.saveGood(wxbGood);
            if(b){
                return new JsonResult(200,"success","http://localhost:8081/wxbgood/good-list?pageNo=1");
            }
            return new JsonResult(200,"failed",null);
        }catch (Exception e){
            return  new JsonResult(500,"failed",e.getMessage());
        }

    }

    //删除商品
    @RequestMapping("deleteGood")
    public String deleteGood(String goodId){
        wxbGoodService.removeGood(goodId);
        return "redirect:/wxbgood/good-list?pageNo=1";
    }

    //查询一个商品
    @RequestMapping("getOneGood")
    public String getOneGood(String goodId,Model model,HttpSession session){
        wxbGoodService.getOneGood(goodId,model,session);
        return "updateGood";
    }

    //添加保存商品
    @ResponseBody
    @RequestMapping("updateGood")
    public JsonResult updateGood(WxbGood wxbGood){
        try{
            boolean b = wxbGoodService.updateGood(wxbGood);
            if(b){
                return new JsonResult(200,"success","http://localhost:8081/wxbgood/good-list?pageNo=1");
            }
            return new JsonResult(200,"failed",null);
        }catch (Exception e){
            return  new JsonResult(500,"failed",e.getMessage());
        }

    }
}
