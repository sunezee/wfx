package com.blb.wfx_cust.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("wxb_good_sku2")
public class WxbGoodSku2 {

  @TableId(type= IdType.INPUT)
  private String skuId;
  private String skuName;
  private String skuCost;
  private String skuPrice;
  private String skuPmoney;
  private String goodId;
  private long orderNo;
  private String serviceMoney;

  public WxbGoodSku2() {
  }

  public WxbGoodSku2(String skuId, String skuName, String skuCost, String skuPrice, String skuPmoney, String goodId, long orderNo, String serviceMoney) {
    this.skuId = skuId;
    this.skuName = skuName;
    this.skuCost = skuCost;
    this.skuPrice = skuPrice;
    this.skuPmoney = skuPmoney;
    this.goodId = goodId;
    this.orderNo = orderNo;
    this.serviceMoney = serviceMoney;
  }

  public String getSkuId() {
    return skuId;
  }

  public void setSkuId(String skuId) {
    this.skuId = skuId;
  }


  public String getSkuName() {
    return skuName;
  }

  public void setSkuName(String skuName) {
    this.skuName = skuName;
  }


  public String getSkuCost() {
    return skuCost;
  }

  public void setSkuCost(String skuCost) {
    this.skuCost = skuCost;
  }


  public String getSkuPrice() {
    return skuPrice;
  }

  public void setSkuPrice(String skuPrice) {
    this.skuPrice = skuPrice;
  }


  public String getSkuPmoney() {
    return skuPmoney;
  }

  public void setSkuPmoney(String skuPmoney) {
    this.skuPmoney = skuPmoney;
  }


  public String getGoodId() {
    return goodId;
  }

  public void setGoodId(String goodId) {
    this.goodId = goodId;
  }


  public long getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(long orderNo) {
    this.orderNo = orderNo;
  }


  public String getServiceMoney() {
    return serviceMoney;
  }

  public void setServiceMoney(String serviceMoney) {
    this.serviceMoney = serviceMoney;
  }

}
