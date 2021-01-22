package com.blb.wfx_mber.config;

import com.github.wxpay.sdk.IWXPayDomain;
import com.github.wxpay.sdk.WXPayConfig;

import java.io.InputStream;

/**
 * 微信支付收款方的配置，此处用的是千峰公司的官方账号
 */
public class MyWXPayConfig extends WXPayConfig {
    //账户的APPID
    public String getAppID() {
        return "wx632c8f211f8122c6";
    }

    //商户ID
    public String getMchID() {
        return "1497984412";
    }

    //秘钥
    public String getKey() {
        return "sbNCm1JnevqI36LrEaxFwcaT0hkGxFnC";
    }

    public InputStream getCertStream() {
        return null;
    }

    public IWXPayDomain getWXPayDomain() {
        return new WXPayDomain();
    }

    class WXPayDomain implements IWXPayDomain{

        public void report(String domain, long elapsedTimeMillis, Exception ex) {

        }

        public DomainInfo getDomain(WXPayConfig config) {
            return new DomainInfo("api.mch.weixin.qq.com",true);
        }
    }
}
