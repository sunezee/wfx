package com.blb.wfx_cust.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blb.wfx_cust.dao.WxbCustomerMapper;
import com.blb.wfx_cust.entity.WxbCustomer;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 自定义Realm
 */
//@Repository
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private WxbCustomerMapper wxbCustomerMapper;

    //身份授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        return null;
    }

    //身份验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获得账号和密码
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
        String username=token.getUsername();
        String password=new String(token.getPassword());
        //通过用户名查询用户
        WxbCustomer user = wxbCustomerMapper.selectOne(new QueryWrapper<WxbCustomer>().eq("login_name", username));
        //用户名不存在抛出异常
        if(user==null){
            throw new UnknownAccountException("用户名不存在");
        }
        //返回验证信息
        return new SimpleAuthenticationInfo(username,user.getLoginPwd(), ByteSource.Util.bytes(user.getLoginName()),getName());
    }
}
