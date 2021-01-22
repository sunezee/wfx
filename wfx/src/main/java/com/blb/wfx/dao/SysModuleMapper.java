package com.blb.wfx.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blb.wfx.entity.ModuleTreeNodes;
import com.blb.wfx.entity.SysModule;

import java.util.List;

public interface SysModuleMapper extends BaseMapper<SysModule> {
    /**
     * 根据用户名查询用户所有权限
     * @param account
     * @return
     */
    List<SysModule> selectModulesByAccount(String account);

    /**
     * 通过用户名查询两级模块菜单
     * @param account
     * @return
     */
    List<ModuleTreeNodes> selectModuleTreeNodesByAccount(String account);

    /**
     * 通过角色名称查询权限
     * @param roleName
     * @return
     */
    List<ModuleTreeNodes> selectModuleTreeNodesByRoleName(String roleName);

    /**
     * 插入权限选择上级菜单时查询三级模块菜单
     * @return
     */
    List<ModuleTreeNodes> selectThree();

    /**
     * 通过权限名称查询子权限
     * @param moduleName
     * @return
     */
    List<ModuleTreeNodes> selectModuleTreeNodesByModuleName(String moduleName);
}
