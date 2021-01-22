package com.blb.wfx.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blb.wfx.entity.JsonResult;
import com.blb.wfx.entity.ModuleTreeNodes;
import com.blb.wfx.entity.SysModule;
import com.blb.wfx.entity.SysRole;
import com.blb.wfx.service.SysModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("module")
public class SysModuleController {

    @Autowired
    private SysModuleService sysModuleService;

    //通过用户名查询模块菜单
    @GetMapping("getModuleTreeNodes")
    public JsonResult getModuleTreeNodesByAccount(String account){
        try{
            List<ModuleTreeNodes> nodes = sysModuleService.getModuleTreeNodesByAccount(account);
            return new JsonResult(200,"success",nodes);
        }catch (Exception e){
            return new JsonResult(500,"failed",e.getMessage());
        }
    }

    //选择上级菜单时查询三级模块菜单
    @GetMapping("getThree")
    public JsonResult getModuleThree(){
        try{
            List<ModuleTreeNodes> nodes = sysModuleService.getThree();
            return new JsonResult(200,"success",nodes);
        }catch (Exception e){
            return new JsonResult(500,"failed",e.getMessage());
        }
    }
    //通过角色名称查询权限
    @GetMapping("getRoleModuleTreeNodes")
    public JsonResult  getModuleTreeNodesByRoleName(String roleName){
        try{
            List<ModuleTreeNodes> nodes = sysModuleService.getModuleTreeNodesByRoleName(roleName);
            for(ModuleTreeNodes node:nodes){
                boolean isChecked=false;
                //判断当前子节点是否被选中
                for(ModuleTreeNodes childNode:node.getNodes()){
                    if(childNode.isChecked()){
                        childNode.getState().setChecked(true);
                        isChecked=true;
                    }
                }
                //子节点选中父节点也应该选中
                if(isChecked){
                    node.getState().setChecked(true);
                }
            }
            return new JsonResult(200,"success",nodes);
        }catch (Exception e){
            return new JsonResult(500,"failed",e.getMessage());
        }
    }
    /**
     *分页展示权限菜单
     * @param pageNo
     * @return
     */
    @RequestMapping("module-page")
    public IPage<SysModule> pageModules(int pageNo,String searchName){
        IPage<SysModule> moduleIPage=new Page<>();
        moduleIPage.setCurrent(pageNo);
        IPage<SysModule> moduleList=null;
        if(searchName.equals("1")){
            moduleIPage.setSize(15);
            moduleList= sysModuleService.page(moduleIPage, new QueryWrapper<SysModule>().orderByAsc("module_code"));
        }else{
            moduleIPage.setSize(100);
            moduleList= sysModuleService.page(moduleIPage, new QueryWrapper<SysModule>()
                    .like("module_name",searchName).or()
                    .like("module_code",searchName)
                    .orderByAsc("module_code"));
        }
        List<SysModule> records = moduleList.getRecords();
        for (SysModule record:records
             ) {
            //设置所属上级
            String parentModule = record.getParentModule();
            if(parentModule.equals("01")){
                record.setParentModule(" ");
            }else{
                SysModule parent_module = sysModuleService.getOne(new QueryWrapper<SysModule>()
                        .eq("module_code",parentModule));
                record.setParentModule(parent_module.getModuleName());
            }
            //设置节点类型
            if(record.getLeaf().equals("1")){
                record.setLeaf("叶子节点");
            }else{
                record.setLeaf("树枝节点");
            }
            //设置是否展开
            if(record.getExpanded().equals("1")){
                record.setExpanded("展开");
            }else{
                record.setExpanded("收缩");
            }

        }
        return moduleList;
    }

    /**
     * 添加角色
     * @param sysModule
     * @return
     */
    @RequestMapping("addModule")
    public String addModule(SysModule sysModule){
        int i = sysModuleService.addModule(sysModule);
        if(i==0){
            return  "faild";
        }else {
            return "success";
        }
    }

    /**
     * 删除菜单
     * @param moduleId
     * @return
     */
    @RequestMapping("removeModule")
    public String removeModule(String moduleId){
        boolean flag = sysModuleService.removeById(moduleId);
        if(flag){
            return "success";
        }else{
            return  "faild";
        }
    }

    /**
     * 查询单个权限
     * @param moduleId
     * @return
     */
    @RequestMapping("getOneModule")
    public SysModule getOneModule(String moduleId){
        SysModule sysModule= sysModuleService.getById(moduleId);
        if(sysModule.getParentModule().equals("01")){
            sysModule.setParentModule(" ");
        }else{
            SysModule module= sysModuleService.getOne(new QueryWrapper<SysModule>().eq("module_code", sysModule.getParentModule()));
            sysModule.setParentModule(module.getModuleName());
        }
        return sysModule;
    }

    /**
     * 更新菜单
     * @param sysModule
     * @return
     */
    @RequestMapping("updateModule")
    public String updateModule(SysModule sysModule){
        SysModule module = sysModuleService.getOne(new QueryWrapper<SysModule>().eq("module_name", sysModule.getParentModule()));
        sysModule.setParentModule(module.getModuleCode());
        boolean b = sysModuleService.updateById(sysModule);
        if(b){
            return "success";
        }else{
            return  "faild";
        }
    }
}
