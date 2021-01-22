package com.blb.wfx.entity;


import java.sql.Timestamp;
import java.util.List;

/**
 * 将UserInfo和UserExpInfo整合起来用来添加用户时传参
 */

public class User {

  private String userId;
  private String userName;
  private String account;
  private String password;
  private String remark;
  private String userType;
  private String enabled;
  private java.sql.Timestamp loginTime;
  private String roleId;
  private String self;
  private String sex;
  private String nickName;
  private String email;
  private String qqNum;
  private String telephone;
  private long loginNum;

  public User() {
  }

  public User(String userId, String userName, String account, String password, String remark, String userType, String enabled, Timestamp loginTime, String roleId, String self, String sex, String nickName, String email, String qqNum, String telephone, long loginNum) {
    this.userId = userId;
    this.userName = userName;
    this.account = account;
    this.password = password;
    this.remark = remark;
    this.userType = userType;
    this.enabled = enabled;
    this.loginTime = loginTime;
    this.roleId = roleId;
    this.self = self;
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


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }


  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }


  public String getEnabled() {
    return enabled;
  }

  public void setEnabled(String enabled) {
    this.enabled = enabled;
  }


  public java.sql.Timestamp getLoginTime() {
    return loginTime;
  }

  public void setLoginTime(java.sql.Timestamp loginTime) {
    this.loginTime = loginTime;
  }


  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getSelf() {
    return self;
  }

  public void setSelf(String self) {
    this.self = self;
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
