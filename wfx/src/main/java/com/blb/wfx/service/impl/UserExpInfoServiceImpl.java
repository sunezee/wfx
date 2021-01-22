package com.blb.wfx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blb.wfx.dao.UserExpInfoMapper;
import com.blb.wfx.entity.UserExpInfo;
import com.blb.wfx.service.UserExpInfoService;
import org.springframework.stereotype.Service;

@Service
public class UserExpInfoServiceImpl extends ServiceImpl<UserExpInfoMapper, UserExpInfo> implements UserExpInfoService {

}
