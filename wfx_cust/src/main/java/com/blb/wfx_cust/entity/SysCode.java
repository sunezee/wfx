package com.blb.wfx_cust.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_code")
public class SysCode {

  @TableId(type= IdType.INPUT)
  private String codeId;
  private String fieldCode;
  private String fieldName;
  private String codeValue;
  private String codeDesc;
  private String enabled;
  private String editMode;
  private long sortNo;
  private String remark;


  public String getCodeId() {
    return codeId;
  }

  public void setCodeId(String codeId) {
    this.codeId = codeId;
  }


  public String getFieldCode() {
    return fieldCode;
  }

  public void setFieldCode(String fieldCode) {
    this.fieldCode = fieldCode;
  }


  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }


  public String getCodeValue() {
    return codeValue;
  }

  public void setCodeValue(String codeValue) {
    this.codeValue = codeValue;
  }


  public String getCodeDesc() {
    return codeDesc;
  }

  public void setCodeDesc(String codeDesc) {
    this.codeDesc = codeDesc;
  }


  public String getEnabled() {
    return enabled;
  }

  public void setEnabled(String enabled) {
    this.enabled = enabled;
  }


  public String getEditMode() {
    return editMode;
  }

  public void setEditMode(String editMode) {
    this.editMode = editMode;
  }


  public long getSortNo() {
    return sortNo;
  }

  public void setSortNo(long sortNo) {
    this.sortNo = sortNo;
  }


  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

}
