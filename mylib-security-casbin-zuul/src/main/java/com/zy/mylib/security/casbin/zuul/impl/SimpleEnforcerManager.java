package com.zy.mylib.security.casbin.zuul.impl;

import com.zy.mylib.cache.guava.GuavaCacheManager;
import com.zy.mylib.security.casbin.StringAdapter;
import com.zy.mylib.security.casbin.StringModel;
import com.zy.mylib.security.casbin.zuul.EnforcerManager;
import com.zy.mylib.security.casbin.ModelAndPolicy;
import com.zy.mylib.security.casbin.zuul.UserAuthzService;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * enforcer 管理基础实现
 */
public class SimpleEnforcerManager implements EnforcerManager, InitializingBean {
    private Cache cache;
    /**
     * 缓存管理
     */
    private CacheManager cacheManager;
    /**
     * 用户权限服务
     */
    private UserAuthzService userAuthzService;

    @Override
    public Enforcer getEnforcer(String user) {
        Enforcer enforcer = cache.get(user, Enforcer.class);
        if(enforcer == null) {
            ModelAndPolicy modelAndPolicy = userAuthzService.getEnforcer(user);
            enforcer = new Enforcer(new StringModel(modelAndPolicy.getModel()), new StringAdapter(modelAndPolicy.getPolicy()));
            cache.put(user, enforcer);
        }
        return enforcer;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(cacheManager == null) {
            cacheManager = new GuavaCacheManager();
        }
        cache = cacheManager.getCache("user_enforcer");
    }

    /**
     * Sets new 缓存管理.
     *
     * @param cacheManager New value of 缓存管理.
     */
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * Gets 用户权限服务.
     *
     * @return Value of 用户权限服务.
     */
    public UserAuthzService getUserAuthzService() {
        return userAuthzService;
    }

    /**
     * Gets 缓存管理.
     *
     * @return Value of 缓存管理.
     */
    public CacheManager getCacheManager() {
        return cacheManager;
    }

    /**
     * Sets new 用户权限服务.
     *
     * @param userAuthzService New value of 用户权限服务.
     */
    public void setUserAuthzService(UserAuthzService userAuthzService) {
        this.userAuthzService = userAuthzService;
    }
}
