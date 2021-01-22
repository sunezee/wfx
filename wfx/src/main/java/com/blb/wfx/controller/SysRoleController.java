package com.blb.wfx.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blb.wfx.entity.SysRole;
import com.blb.wfx.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("role")
public class SysRoleController {
    @Autowired
    private SysRoleService roleService;

    /**
     *分页展示角色
     * @param pageNo
     * @return
     */
    @ResponseBody
    @RequestMapping("role-page")
    public IPage<SysRole> pageRoles(int pageNo,String searchName){
        IPage<SysRole> roleIPage=new Page<>();
        roleIPage.setCurrent(pageNo);
        IPage<SysRole> role=null;
        if(searchName.equals("1")){
            roleIPage.setSize(15);
            role = roleService.page(roleIPage, new QueryWrapper<SysRole>().orderByAsc("role_order"));
        }else{
            roleIPage.setSize(100);
            role = roleService.page(roleIPage, new QueryWrapper<SysRole>()
                    .like("role_name",searchName).or()
                    .like("role_desc",searchName)
                    .orderByAsc("role_order"));
        }
        List<SysRole> records = role.getRecords();
        for (SysRole s:records
             ) {
            String roleType = s.getRoleType();
            if(roleType.equals("1")){
                s.setRoleType("管理角色");
            }else if(roleType.equals("2")){
                s.setRoleType("业务角色");
            }else{
                s.setRoleType("其他角色");
            }
        }
        return role;

    }

    /**
     * 用于用户选择角色时，查询所欲角色列表
     * @return
     */
    @ResponseBody
    @RequestMapping("allRole")
    public List<SysRole> getAll(){
        List<SysRole> list = roleService.list();
        return  list;
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("saveRole")
    public String saveRole(SysRole role){
//        System.out.println(role);
        SysRole r = roleService.getById(role.getRoleCode());
        SysRole r2 = roleService.getOne(new QueryWrapper<SysRole>().eq("role_name", role.getRoleName()));
        if(r!=null&&r2!=null){
            return "exist";
        }
        boolean save = roleService.save(role);
        if(save){
            return "success";
        }
        return "faild";
    }

    /**
     * 根据角色编号查询角色的信息，用于更新
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping("getOneRole")
    public SysRole getOneRole(String roleId){
        SysRole role = roleService.getById(roleId);
        return role;
    }

    /**
     * 更新角色
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("updateRole")
    public String updateRole(SysRole role){
        boolean flag = roleService.update(role,new QueryWrapper<SysRole>().eq("role_code", role.getRoleCode()));
        if(flag){
            return "success";
        }
        return "failed";
    }


    /**
     *删除角色
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping("removeRole")
    public String removeRole(String roleId){
        boolean b = roleService.removeById(roleId);
        if(b){
            return "success";
        }
        return "failed";
    }


}
