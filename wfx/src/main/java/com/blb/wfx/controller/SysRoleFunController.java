package com.blb.wfx.controller;

import com.blb.wfx.entity.JsonResult;
import com.blb.wfx.entity.SysRoleFun;
import com.blb.wfx.service.SysRoleFunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("roleModule")
public class SysRoleFunController {
    @Autowired
    private SysRoleFunService roleModuleService;

    //角色授权
    @RequestMapping("addRoleModule")
    public JsonResult addRoleModule(String roleId, String moduleId){
        try{
            SysRoleFun roleModule=new SysRoleFun();
            roleModule.setRoleId(roleId);
            roleModule.setModuleId(moduleId);
            roleModuleService.grantRoleModule(roleModule);
            return  new JsonResult(200,"角色授权成功",null);

        }catch (Exception e){
            return  new JsonResult(500,"角色授权失败",e.getMessage());
        }
    }

    //取消授权
    @RequestMapping("removeRoleModule")
    public JsonResult removeRoleModule(String roleId, String moduleId){
        try{
            SysRoleFun roleModule=new SysRoleFun();
            roleModule.setRoleId(roleId);
            roleModule.setModuleId(moduleId);
            roleModuleService.removeRoleModule(roleModule);
            return  new JsonResult(200,"注销权限成功",null);

        }catch (Exception e){
            return  new JsonResult(500,"注销权限失败",e.getMessage());
        }
    }

}
