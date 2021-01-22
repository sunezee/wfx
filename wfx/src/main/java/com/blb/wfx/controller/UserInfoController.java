package com.blb.wfx.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blb.wfx.entity.SysRole;
import com.blb.wfx.entity.User;
import com.blb.wfx.entity.UserInfo;
import com.blb.wfx.service.SysRoleService;
import com.blb.wfx.service.UserInfoService;
import com.blb.wfx.utils.PublicUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SysRoleService roleService;

    @PostMapping("login")
    public String login(String username, String password, HttpSession session){

        UsernamePasswordToken token=new UsernamePasswordToken();
        token.setUsername(username);
        token.setPassword(password.toCharArray());
        Subject subject= SecurityUtils.getSubject();
//        if(!subject.isAuthenticated()){
        try{
            subject.login(token);
            session.setAttribute("uname",username);
            System.out.println(session.getAttribute("uname"));
            UserInfo user = userInfoService.getOne(new QueryWrapper<UserInfo>().eq("account", username));
            //查询上次登录时间
            Timestamp loginTime = user.getLoginTime();
            String stringTime = PublicUtil.timestamp2String(loginTime, true);
            session.setAttribute("loginTime",stringTime);
            //设置本次登录时间
            Date date=new Date();
            long time = date.getTime();
            Timestamp ts=new Timestamp(time);
            user.setLoginTime(ts);
            userInfoService.update(user,new QueryWrapper<UserInfo>().eq("account",username));

//                subject.logout();
            return "redirect:/index.html";
        }catch (Exception e){
            e.printStackTrace();
        }
//        }
        return "redirect:/login.html";
    }

    /**
     *分页展示用户
     * @param pageNo
     * @return
     */
    @ResponseBody
    @RequestMapping("user-page")
    public IPage<UserInfo> pageUser(int pageNo,String searchName){
        IPage<UserInfo> userIPage=new Page<>();
        userIPage.setCurrent(pageNo);
        IPage<UserInfo> userPage=null;
        if(searchName.equals("1")){
            userIPage.setSize(15);
            userPage = userInfoService.page(userIPage);
        }else{
            userIPage.setSize(100);
            userPage = userInfoService.page(userIPage,new QueryWrapper<UserInfo>()
                    .like("user_name",searchName).or()
                    .like("account",searchName).or()
                    .like("remark",searchName));
        }

        List<UserInfo> records = userPage.getRecords();
        for (UserInfo user:records
             ) {

            //设置账号类型
            String userType = user.getUserType();
            switch (userType){
                case "1":
                    user.setUserType("客服账号");
                    break;
                case "2":
                    user.setUserType("管理账号");
                    break;
                case "3":
                    user.setUserType("内置账号");
                    break;
                case "4":
                    user.setUserType("财务账号");
                    break;
                case "5":
                    user.setUserType("物流账号");
                    break;
                default:
                    user.setUserType("其他账号");
            }
            //设置账号是否有效
            String enabled = user.getEnabled();
            if(enabled.equals("1")){
                user.setEnabled("是");
            }else{
                user.setEnabled("否");
            }

            //设置所属角色
            SysRole role = roleService.getOne(new QueryWrapper<SysRole>().eq("role_code", user.getRoleId()));
            if(role!=null){
                user.setRoleId(role.getRoleName());
            }else{
                user.setRoleId("暂无");
            }

            //设置登录时间
            Timestamp loginTime = user.getLoginTime();
            String tsStr = "";
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            tsStr=dateFormat.format(loginTime);
            user.setSelf(tsStr);
        }
        return userPage;
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("addUser")
    public String  addUser(User user){
        int i = userInfoService.addUser(user);
        if(i>0){
            return "success";
        }
        return "faild";
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping("removeUser")
    public String removeUser(String userId){
        boolean b = userInfoService.removeUser(userId);
        if(b){
            return "success";
        }
        return "faild";
    }

    /**
     * 查询一个用户
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping("getOneUser")
    public User getOne(String userId){
        User one = userInfoService.getOne(userId);
        return  one;
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("updateUser")
    public String  updateUser(User user){
        boolean b = userInfoService.updateUser(user);
        if(b){
            return "success";
        }
        return "faild";
    }
}
