package com.blb.wfx.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_role")
public class SysRole {

  @TableId(type = IdType.INPUT)
  private String roleCode;
  private String roleName;
  private String roleDesc;
  private long roleOrder;
  private String roleType;


  public String getRoleCode() {
    return roleCode;
  }

  public void setRoleCode(String roleCode) {
    this.roleCode = roleCode;
  }


  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }


  public String getRoleDesc() {
    return roleDesc;
  }

  public void setRoleDesc(String roleDesc) {
    this.roleDesc = roleDesc;
  }


  public long getRoleOrder() {
    return roleOrder;
  }

  public void setRoleOrder(long roleOrder) {
    this.roleOrder = roleOrder;
  }


  public String getRoleType() {
    return roleType;
  }

  public void setRoleType(String roleType) {
    this.roleType = roleType;
  }

  @Override
  public String toString() {
    return "SysRole{" +
            "roleCode='" + roleCode + '\'' +
            ", roleName='" + roleName + '\'' +
            ", roleDesc='" + roleDesc + '\'' +
            ", roleOrder=" + roleOrder +
            ", roleType=" + roleType +
            '}';
  }
}
