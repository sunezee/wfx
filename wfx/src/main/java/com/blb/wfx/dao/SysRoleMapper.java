package com.blb.wfx.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blb.wfx.entity.SysRole;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据账号查询用户所有角色
     * @param account
     * @return
     */
    List<SysRole> selectRolesByAccount(String account);
}
