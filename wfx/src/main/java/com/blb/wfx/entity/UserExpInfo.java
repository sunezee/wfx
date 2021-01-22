package com.blb.wfx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_exp_info")
public class UserExpInfo {

  @TableId(type= IdType.INPUT)
  private String userId;
  private String sex;
  private String nickName;
  private String email;
  private String qqNum;
  private String telephone;
  private long loginNum;

  public UserExpInfo() {
  }

  public UserExpInfo(String userId, String sex, String nickName, String email, String qqNum, String telephone, long loginNum) {
    this.userId = userId;
    this.sex = sex;
    this.nickName = nickName;
    this.email = email;
    this.qqNum = qqNum;
    this.telephone = telephone;
    this.loginNum = loginNum;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }


  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }


  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getQqNum() {
    return qqNum;
  }

  public void setQqNum(String qqNum) {
    this.qqNum = qqNum;
  }


  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }


  public long getLoginNum() {
    return loginNum;
  }

  public void setLoginNum(long loginNum) {
    this.loginNum = loginNum;
  }

}
