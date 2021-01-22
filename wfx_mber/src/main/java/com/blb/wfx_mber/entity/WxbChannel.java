package com.blb.wfx_mber.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("wxb_channel")
public class WxbChannel {

  @TableId(type = IdType.INPUT)
  private long channelId;
  private String channelName;
  private String channelUid;
  private String channelTxt;


  public long getChannelId() {
    return channelId;
  }

  public void setChannelId(long channelId) {
    this.channelId = channelId;
  }


  public String getChannelName() {
    return channelName;
  }

  public void setChannelName(String channelName) {
    this.channelName = channelName;
  }


  public String getChannelUid() {
    return channelUid;
  }

  public void setChannelUid(String channelUid) {
    this.channelUid = channelUid;
  }


  public String getChannelTxt() {
    return channelTxt;
  }

  public void setChannelTxt(String channelTxt) {
    this.channelTxt = channelTxt;
  }

}
