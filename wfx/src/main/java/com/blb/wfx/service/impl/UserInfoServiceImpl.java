package com.blb.wfx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blb.wfx.dao.SysRoleMapper;
import com.blb.wfx.dao.SysUserRoleMapper;
import com.blb.wfx.dao.UserExpInfoMapper;
import com.blb.wfx.dao.UserInfoMapper;
import com.blb.wfx.entity.*;
import com.blb.wfx.service.UserInfoService;
import com.blb.wfx.utils.PublicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserExpInfoMapper userExpInfoMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Transactional//事物管理，出错全部回调
    @Override
    public int addUser(User user) {
        //随机生成8位的userId,重复就继续生成，直到不重复
        boolean flag=true;
        String userId = "";
        do{
            userId=PublicUtil.getRondomChangeFirst(8);
            UserInfo userInfo = userInfoMapper.selectById(userId);
            if(userInfo==null){
                flag=false;
            }
        }while (flag);

        List<String> roleCodes=new ArrayList<>();
        //查询选择的角色,并插入中间表
        if(!StringUtils.isEmpty(user.getRoleId())&&user.getRoleId()!=null) {
            String[] strings = PublicUtil.stringToArray(user.getRoleId(), "-");
            for (String roleName : strings
            ) {
                if(roleName==null||roleName==""){
                    continue;
                }
                SysRole role = sysRoleMapper.selectOne(new QueryWrapper<SysRole>().eq("role_name", roleName));
                roleCodes.add(role.getRoleCode());
                SysUserRole sysUserRole=new SysUserRole();
                sysUserRole.setUserId(userId);
                sysUserRole.setRoleId(role.getRoleCode());
                userRoleMapper.insert(sysUserRole);
            }
        }else{
            roleCodes.add(" ");
        }

        //给密码加密
        String password = PublicUtil.encryption("md5", user.getPassword(), user.getAccount(), 5);
        //设置登录时间
        Date date=new Date();
        Timestamp loginTime = new Timestamp(date.getTime());
        //userInfo插入
        UserInfo userInfo=new UserInfo(userId,user.getUserName(),user.getAccount(),password,
                user.getRemark(),user.getUserType(),user.getEnabled(),loginTime,roleCodes.get(0),"0");
        userInfoMapper.insert(userInfo);

        //userExpInfo表插入
        UserExpInfo userExpInfo=new UserExpInfo(userId,user.getSex(),user.getNickName(),user.getEmail(),
                user.getQqNum(),user.getTelephone(),0);
        userExpInfoMapper.insert(userExpInfo);
        return 1;
    }

    @Transactional//事物管理，出错全部回调
    @Override
    public boolean removeUser(String userId) {
        userInfoMapper.deleteById(userId);
        userExpInfoMapper.deleteById(userId);
        userRoleMapper.delete(new QueryWrapper<SysUserRole>().eq("user_id",userId));
        return true;
    }

    @Transactional//事物管理，出错全部回调
    @Override
    public User getOne(String userId){
        UserInfo userInfo = userInfoMapper.selectById(userId);
        UserExpInfo userExpInfo = userExpInfoMapper.selectById(userId);
        List<SysUserRole> userRoles = userRoleMapper.selectList(new QueryWrapper<SysUserRole>().eq("user_id", userId));
        User user=null;
        String roleId="";
        if(userRoles!=null&&userRoles.size()>0){
            for (SysUserRole s:userRoles
                 ) {
                SysRole role = sysRoleMapper.selectOne(new QueryWrapper<SysRole>().eq("role_code", s.getRoleId()));
                roleId+=role.getRoleName()+'-';
            }
        }
        user=new User(userId,userInfo.getUserName(),userInfo.getAccount(),userInfo.getPassword(),userInfo.getRemark(),
                userInfo.getUserType(),userInfo.getEnabled(),userInfo.getLoginTime(),roleId,userInfo.getSelf(),
                userExpInfo.getSex(),userExpInfo.getNickName(),userExpInfo.getEmail(),userExpInfo.getQqNum(),userExpInfo.getTelephone(),userExpInfo.getLoginNum());
        return user;
    }

    @Transactional//事物管理，出错全部回调
    @Override
    public boolean updateUser(User user) {
        List<String> roleCodes=new ArrayList<>();
        //查询新选择的角色,并插入中间表
        if(!StringUtils.isEmpty(user.getRoleId())&&user.getRoleId()!=null) {
            String[] strings = PublicUtil.stringToArray(user.getRoleId(), "-");
            for (String roleName : strings
            ) {
                if(roleName==null||roleName==""){
                    continue;
                }
                SysRole role = sysRoleMapper.selectOne(new QueryWrapper<SysRole>().eq("role_name", roleName));
                roleCodes.add(role.getRoleCode());
                SysUserRole sysUserRole1 = userRoleMapper.selectOne(new QueryWrapper<SysUserRole>()
                        .eq("user_id", user.getUserId())
                        .eq("role_id", role.getRoleCode()));
                if(sysUserRole1==null){
                    SysUserRole sysUserRole=new SysUserRole();
                    sysUserRole.setUserId(user.getUserId());
                    sysUserRole.setRoleId(role.getRoleCode());
                    userRoleMapper.insert(sysUserRole);
                }
            }
        }else{
            UserInfo userInfo = userInfoMapper.selectById(user.getUserId());
            roleCodes.add(userInfo.getRoleId());
        }
        //给密码加密
        String password = PublicUtil.encryption("md5", user.getPassword(), user.getAccount(), 5);
        //设置登录时间
        Date date=new Date();
        Timestamp loginTime = new Timestamp(date.getTime());
        //userInfo更新
        UserInfo userInfo=new UserInfo(user.getUserId(),user.getUserName(),user.getAccount(),password,
                user.getRemark(),user.getUserType(),user.getEnabled(),loginTime,roleCodes.get(0),"0");
        userInfoMapper.updateById(userInfo);
        //userExpInfo表更新
        UserExpInfo userExpInfo=new UserExpInfo(user.getUserId(),user.getSex(),user.getNickName(),user.getEmail(),
                user.getQqNum(),user.getTelephone(),0);
        userExpInfoMapper.updateById(userExpInfo);
        return true;
    }
}
