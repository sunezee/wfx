package com.blb.wfx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blb.wfx.entity.ModuleTreeNodes;
import com.blb.wfx.entity.SysModule;

import java.util.List;

public interface SysModuleService extends IService<SysModule> {

    /**
     * 通过用户名查询两级模块菜单
     * @param account
     * @return
     */
    List<ModuleTreeNodes> getModuleTreeNodesByAccount(String account);

    /**
     * 通过角色名称查询权限
     * @param roleName
     * @return
     */
    List<ModuleTreeNodes> getModuleTreeNodesByRoleName(String roleName);

    /**
     * 插入权限选着上级菜单时查询三级模块菜单
     * @return
     */
    List<ModuleTreeNodes> getThree();

    /**
     * 插入菜单权限
     * @return
     */
    int addModule(SysModule sysModule);
}
