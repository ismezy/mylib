package com.zy.mylib.security.utils;

import com.zy.mylib.security.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 用户工具类
 *
 * @author ASUS
 */
@Component("securityUserUtils")
public class UserUtils implements ApplicationContextAware {
    protected static ApplicationContext context;

    /**
     * 获取当前用户
     *
     * @return
     */
    public static <T> T getCurrentUser() {
        if (SecurityUtils.getSubject().getPrincipal() instanceof String) {
            UserService userManager = context.getBean(UserService.class);
            Optional<T> user = userManager.findById((String) SecurityUtils.getSubject().getPrincipal());
            return user.get();
        }
        return (T) SecurityUtils.getSubject().getPrincipal();
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
