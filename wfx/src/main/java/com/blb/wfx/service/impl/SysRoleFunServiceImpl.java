package com.blb.wfx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blb.wfx.dao.SysRoleFunMapper;
import com.blb.wfx.entity.SysRoleFun;
import com.blb.wfx.service.SysRoleFunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysRoleFunServiceImpl extends ServiceImpl<SysRoleFunMapper, SysRoleFun> implements SysRoleFunService {

    @Autowired
    private SysRoleFunMapper roleModuleMapper;

    @Override
    @Transactional
    public void grantRoleModule(SysRoleFun roleModule) {
        int len=roleModule.getModuleId().length();
        System.out.println(len);
        if(len==8){
            roleModuleMapper.insert(roleModule);
            String mid=roleModule.getModuleId().substring(0,6);
            System.out.println("父级权限为"+mid);
            int moduleId = roleModuleMapper.selectCount(new QueryWrapper<SysRoleFun>()
                    .eq("module_id", mid)
                    .eq("role_id",roleModule.getRoleId()));
            System.out.println("数据条数"+moduleId);
            if(moduleId>0){
                System.out.println("该权限以存在");
            }else{
                roleModule.setModuleId(mid);
                roleModuleMapper.insert(roleModule);
                System.out.println("插入父级权限成功");
            }

        }else{
            System.out.println("插入所有权限");
            List<SysRoleFun> modules = roleModuleMapper.selectList(new QueryWrapper<SysRoleFun>()
                    .likeLeft("module_id", roleModule.getModuleId()+'_'+'_')
                    .eq("role_id","57414897"));
            int count = roleModuleMapper.selectCount(new QueryWrapper<SysRoleFun>()
                    .eq("module_id", roleModule.getModuleId())
                    .eq("role_id",roleModule.getRoleId()));
            if(count==0){
                SysRoleFun roleModule2=new SysRoleFun();
                roleModule.setFunId(0);
                roleModule2.setRoleId(roleModule.getRoleId());
                roleModule2.setModuleId(roleModule.getModuleId());
                System.out.println(roleModule2);
                roleModuleMapper.insert(roleModule2);
            }
            for (SysRoleFun rm:modules
            ) {
                rm.setFunId(0);
                rm.setRoleId(roleModule.getRoleId());
            }
            modules.forEach(roleModule1 -> System.out.println(roleModule1));
            modules.forEach(roleModule1 -> roleModuleMapper.insert(roleModule1));
        }
    }

    @Override
    @Transactional
    public void removeRoleModule(SysRoleFun roleModule) {
        int len=roleModule.getModuleId().length();
        System.out.println(len);
        if(len==8){
            roleModuleMapper.delete(new QueryWrapper<SysRoleFun>()
                    .eq("role_id",roleModule.getRoleId())
                    .eq("module_id",roleModule.getModuleId()));
            String s = roleModule.getModuleId().substring(0, 6);
            Integer count = roleModuleMapper.selectCount(new QueryWrapper<SysRoleFun>()
                    .likeLeft("module_id", s + '_' + '_')
                    .eq("role_id", roleModule.getRoleId()));
            if(count==0){
                roleModuleMapper.delete(new QueryWrapper<SysRoleFun>()
                        .eq("role_id",roleModule.getRoleId())
                        .eq("module_id",s));
            }

        }else{
            roleModuleMapper.delete(new QueryWrapper<SysRoleFun>()
                    .eq("role_id",roleModule.getRoleId())
                    .eq("module_id",roleModule.getModuleId()));
            List<SysRoleFun> modules = roleModuleMapper.selectList(new QueryWrapper<SysRoleFun>()
                    .likeLeft("module_id", roleModule.getModuleId()+'_'+'_')
                    .eq("role_id",roleModule.getRoleId()));
            modules.forEach(roleModule1 -> System.out.println(roleModule1));
            modules.forEach(roleModule1 ->  roleModuleMapper.delete(new QueryWrapper<SysRoleFun>()
                    .eq("role_id",roleModule1.getRoleId())
                    .eq("module_id",roleModule1.getModuleId())));
        }
    }
}
