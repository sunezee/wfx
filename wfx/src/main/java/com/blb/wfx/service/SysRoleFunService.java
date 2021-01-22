package com.blb.wfx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blb.wfx.entity.SysRoleFun;


public interface SysRoleFunService extends IService<SysRoleFun> {

    /**
     * 角色授权
     * @param roleModule
     */
    public void grantRoleModule(SysRoleFun roleModule);

    /**
     * 取消授权
     * @param roleModule
     */
    public void removeRoleModule(SysRoleFun roleModule);
}
