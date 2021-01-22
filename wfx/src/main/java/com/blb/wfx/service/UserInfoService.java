package com.blb.wfx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blb.wfx.entity.User;
import com.blb.wfx.entity.UserInfo;

public interface UserInfoService extends IService<UserInfo> {

    /**
     * 添加角色
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 删除角色
     */
    boolean removeUser(String userId);

    /**
     * 查询一个角色
     * @param userId
     * @return
     */
    User getOne(String userId);

    /**
     * 更新角色
     * @param user
     * @return
     */
    boolean updateUser(User user);
}
