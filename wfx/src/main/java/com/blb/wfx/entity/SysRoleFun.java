package com.blb.wfx.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_role_fun")
public class SysRoleFun {

  @TableId(type= IdType.INPUT)
  private long funId;
  private String moduleId;
  private String roleId;


  public long getFunId() {
    return funId;
  }

  public void setFunId(long funId) {
    this.funId = funId;
  }


  public String getModuleId() {
    return moduleId;
  }

  public void setModuleId(String moduleId) {
    this.moduleId = moduleId;
  }


  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

}
