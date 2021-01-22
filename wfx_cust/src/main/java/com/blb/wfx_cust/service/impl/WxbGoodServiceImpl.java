package com.blb.wfx_cust.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blb.wfx_cust.dao.*;
import com.blb.wfx_cust.entity.*;
import com.blb.wfx_cust.service.WxbGoodService;
import com.blb.wfx_cust.utils.PublicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class WxbGoodServiceImpl extends ServiceImpl<WxbGoodMapper, WxbGood> implements WxbGoodService {

    @Autowired
    private WxbGoodTypeMapper wxbGoodTypeMapper;

    @Autowired
    private SysCodeMapper sysCodeMapper;

    @Autowired
    private WxbGoodCopyMapper wxbGoodCopyMapper;

    @Autowired
    private WxbGoodMapper wxbGoodMapper;

    @Autowired
    private WxbGoodSku2Mapper wxbGoodSku2Mapper;


    @Override
    @Transactional//事物管理，出错全部回调
    public void addGood(Model model,HttpSession session) {

        //获取登录时保存的用户信息
        String customerId = (String) session.getAttribute("customerId");
        //获取商品标签
        List<SysCode> sysCodes = sysCodeMapper.selectList(new QueryWrapper<SysCode>()
                .eq("field_code", "GOOD_TAG"));
        model.addAttribute("tags",sysCodes);
        //获取商品类型
        List<WxbGoodType> wxbGoodTypes = wxbGoodTypeMapper.selectList(new QueryWrapper<>());
        model.addAttribute("goodTypes",wxbGoodTypes);
        //获取文案
        List<WxbGoodCopy> goodCopys = wxbGoodCopyMapper.selectList(new QueryWrapper<WxbGoodCopy>()
                .eq("customer_id", customerId));
        model.addAttribute("goodCopys",goodCopys);


    }

    @Override
    @Transactional//事物管理，出错全部回调
    public boolean saveGood(WxbGood wxbGood) {
        //设置商品id
        String goodId=getGoodId();

        //插入套餐
        String[] skutitle = PublicUtil.stringToArray(wxbGood.getSkuTitle(), "\\|");
        String[] skucb = PublicUtil.stringToArray(wxbGood.getSkuCost(), "\\|");
        String[] skujg = PublicUtil.stringToArray(wxbGood.getSkuPrice(), "\\|");
        String[] skufc = PublicUtil.stringToArray(wxbGood.getSkuPmoney(), "\\|");
        String[] skukffc = PublicUtil.stringToArray(wxbGood.getKfqq(), "\\|");
        for (int i = 0; i < skutitle.length; i++) {
            if(StringUtils.isEmpty(skutitle[i])){
                continue;
            }
            WxbGoodSku2 sku2=new WxbGoodSku2(getSkuId(),skutitle[i],skucb[i],skujg[i],
                    skufc[i],goodId,wxbGood.getOrderNo(),skukffc[i]);
            wxbGoodSku2Mapper.insert(sku2);

        }

        //插入商品
        wxbGood.setGoodId(goodId);
        wxbGood.setState(0);
        //设置创建时间
        Timestamp timestamp=new Timestamp(new Date().getTime());
        wxbGood.setCreateTime(timestamp);
        //商品插入商品表
        wxbGoodMapper.insert(wxbGood);
        return true;
    }
    @Transactional//事物管理，出错全部回调
    @Override
    public boolean removeGood(String goodId) {
        wxbGoodMapper.deleteById(goodId);
        wxbGoodSku2Mapper.delete(new QueryWrapper<WxbGoodSku2>()
                .eq("good_id",goodId));
        return true;
    }

    @Transactional//事物管理，出错全部回调
    @Override
    public void getOneGood(String goodId,Model model,HttpSession session) {
        //获取登录时保存的用户信息
        String customerId = (String) session.getAttribute("customerId");
        //获取商品标签
        List<SysCode> sysCodes = sysCodeMapper.selectList(new QueryWrapper<SysCode>()
                .eq("field_code", "GOOD_TAG"));
        model.addAttribute("tags",sysCodes);
        //获取商品类型
        List<WxbGoodType> wxbGoodTypes = wxbGoodTypeMapper.selectList(new QueryWrapper<>());
        model.addAttribute("goodTypes",wxbGoodTypes);
        //获取文案
        List<WxbGoodCopy> goodCopys = wxbGoodCopyMapper.selectList(new QueryWrapper<WxbGoodCopy>()
                .eq("customer_id", customerId));
        model.addAttribute("goodCopys",goodCopys);
        //获取商品信息
        WxbGood wxbGood = wxbGoodMapper.selectById(goodId);
        model.addAttribute("good",wxbGood);
        //获取套餐集合
        List<WxbGoodSku2> skus = wxbGoodSku2Mapper.selectList(new QueryWrapper<WxbGoodSku2>()
                .eq("good_id", goodId));
        model.addAttribute("skus",skus);

    }

    @Transactional//事物管理，出错全部回调
    @Override
    public boolean updateGood(WxbGood wxbGood) {
        //删除原来的套餐
        wxbGoodSku2Mapper.delete(new QueryWrapper<WxbGoodSku2>()
                .eq("good_id",wxbGood.getGoodId()));
        //插入新选择的套餐
        String[] skutitle = PublicUtil.stringToArray(wxbGood.getSkuTitle(), "\\|");
        String[] skucb = PublicUtil.stringToArray(wxbGood.getSkuCost(), "\\|");
        String[] skujg = PublicUtil.stringToArray(wxbGood.getSkuPrice(), "\\|");
        String[] skufc = PublicUtil.stringToArray(wxbGood.getSkuPmoney(), "\\|");
        String[] skukffc = PublicUtil.stringToArray(wxbGood.getKfqq(), "\\|");
        for (int i = 0; i < skutitle.length; i++) {
            if(StringUtils.isEmpty(skutitle[i])){
                continue;
            }
            WxbGoodSku2 sku2=new WxbGoodSku2(getSkuId(),skutitle[i],skucb[i],skujg[i],
                    skufc[i],wxbGood.getGoodId(),wxbGood.getOrderNo(),skukffc[i]);
            wxbGoodSku2Mapper.insert(sku2);
        }
        //设置更新时间
        Timestamp timestamp=new Timestamp(new Date().getTime());
        wxbGood.setRecomedTime(timestamp);
        //设置创建时间
        WxbGood wxbGood1 = wxbGoodMapper.selectById(wxbGood.getGoodId());
        wxbGood.setCreateTime(wxbGood1.getCreateTime());
        //更新
        wxbGoodMapper.updateById(wxbGood);
        return true;
    }

    /**
     * 随机生成8位数字长度的商品id
     * @return
     */
    public String getGoodId(){
        String custId="";
        int count=0;
        do{
            custId= PublicUtil.getRondomChangeFirst(8);
            count = wxbGoodMapper.selectCount(new QueryWrapper<WxbGood>()
                    .eq("good_id", custId));
        }while (count>0);
        return  custId;
    }
    /**
     * 随机生成8位数字长度的套餐id
     * @return
     */
    public String getSkuId(){
        String custId="";
        int count=0;
        do{
            custId= PublicUtil.getRondomChangeFirst(8);
            count = wxbGoodSku2Mapper.selectCount(new QueryWrapper<WxbGoodSku2>()
                    .eq("good_id", custId));
        }while (count>0);
        return  custId;
    }
}
