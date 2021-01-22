package com.blb.wfx_mber.config;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 让shiro通过ajax请求的过滤器
 */
public class ShiroPermsFilter extends PermissionsAuthorizationFilter {
    /**
     * shiro认证perms资源失败后回调方法
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws IOException
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        System.out.println("ShiroPermsFilter--onAccessDenied");

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        //获取当前用户
        Subject subject = this.getSubject(httpServletRequest, httpServletResponse);
        //判断是否已经认证
        if (subject.getPrincipal() == null) {
            //没有认证则重定向到登录页面
            this.saveRequestAndRedirectToLogin(httpServletRequest, httpServletResponse);
        }else {
            //认证过了
            String requestedWith = httpServletRequest.getHeader("X-Requested-With");
            if (requestedWith!=null && requestedWith.length() >0 &&
                    requestedWith.equals("XMLHttpRequest")) {
                //如果是ajax返回指定格式数据
                httpServletResponse.setContentType("text/json;charset=UTF-8");//设置响应头
                //返回json 数据，告知无权限
                httpServletResponse.getWriter().write("{\"code\":500,\"msg\":\"failed\",\"data\":\"没有权限\"}");
            } else {
                //如果是普通请求进行重定向
                //不是ajax请求
                String unauthorizedUrl = this.getUnauthorizedUrl();
                if (unauthorizedUrl!=null && unauthorizedUrl.length() >0) {
                    WebUtils.issueRedirect(httpServletRequest, httpServletResponse, unauthorizedUrl);
                } else {
                    WebUtils.toHttp(httpServletResponse).sendError(401);
                }
            }

        }
        return false;
    }

}
