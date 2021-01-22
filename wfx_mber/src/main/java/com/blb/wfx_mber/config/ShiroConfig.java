package com.blb.wfx_mber.config;



import com.blb.wfx_mber.realm.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置类
 * @Configuratiuon 用于定义配置类，该类的各个方法返回各种对象实现系统的配置
 */
@Configuration
public class ShiroConfig {

    //返回密码的匹配器
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        //创建匹配器
        HashedCredentialsMatcher matcher=new HashedCredentialsMatcher();
        //设置加密算法
        matcher.setHashAlgorithmName("md5");
        //设置加密次数
        matcher.setHashIterations(5);
        //设置密文是16进制编码，false是base64编码
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }

    //返回自定义Realm
    @Bean
    public UserRealm userRealm(){
        UserRealm realm=new UserRealm();
        //设置密码匹配器
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        //取消缓存
        realm.setCachingEnabled(false);
        return realm;
    }

    //返回安全管理器
    @Bean
    public SecurityManager securityManager(){
        //创建web项目的安全管理器
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //设置Realm数据
        securityManager.setRealm(userRealm());
        return securityManager;
    }

    //配置过滤器实现URL登录验证
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean factoryBean=new ShiroFilterFactoryBean();
        //配置安全管理器
        factoryBean.setSecurityManager(securityManager);
        //配置登录的URl,登录失败会自动跳转
        factoryBean.setLoginUrl("login.html");
        //配置授权失败的URL
        factoryBean.setUnauthorizedUrl("failed.html");
        //配置拦截的规则,不拦截的放在前面,拦截的放在后面
        Map<String,String> map=new LinkedHashMap<>();
        //anon 不拦截; authc 拦截
        map.put("/static/**","anon");
        map.put("/login.html","anon");
//        map.put("/index.html","anon");
        map.put("/register.html","anon");
        map.put("/failed.html","anon");
        map.put("/mber/login","anon");
        map.put("/user/register","anon");
        map.put("/**","authc");
        //配置拦截规则
        factoryBean.setFilterChainDefinitionMap(map);
        //配置过滤器
        factoryBean.getFilters().put("authc",new ShiroPermsFilter());
        return factoryBean;
    }

    //------下面的配置用于启动两个注解@RequiresRoles  @RequiresPermissions  ,基于Spring AOP实现

    //配置一个生命周期的后置处理器
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return  new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator=new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return  creator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }
}
