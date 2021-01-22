package com.blb.wfx.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.blb.wfx.dao.SysModuleMapper;
import com.blb.wfx.dao.SysRoleMapper;
import com.blb.wfx.dao.UserInfoMapper;
import com.blb.wfx.entity.SysModule;
import com.blb.wfx.entity.SysRole;
import com.blb.wfx.entity.UserInfo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 自定义Realm
 */
//@Repository
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysModuleMapper sysModuleMapper;

    //身份授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获得登录的账号
        String  username = (String) principalCollection.getPrimaryPrincipal();
        //创建授权信息对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //通过账号查询角色，添加角色
        List<SysRole> roles = sysRoleMapper.selectRolesByAccount(username);
        roles.forEach(role -> info.addRole(role.getRoleName()));
        //通过账号查询权限，添加权限
        List<SysModule> modules = sysModuleMapper.selectModulesByAccount(username);
        modules.forEach(module -> info.addRole(module.getModuleName()));
        return info;
    }

    //身份验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获得账号和密码
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
        String username=token.getUsername();
        String password=new String(token.getPassword());
        //通过用户名查询用户
        UserInfo user = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("account", username));
        //用户名不存在抛出异常
        if(user==null){
            throw new UnknownAccountException("用户名不存在");
        }
        //返回验证信息
        return new SimpleAuthenticationInfo(username,user.getPassword(), ByteSource.Util.bytes(user.getAccount()),getName());
    }
}
