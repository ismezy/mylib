package com.zy.mylib.security.casbin;

import org.casbin.jcasbin.main.Enforcer;

import java.io.Serializable;

/**
 * 用户权限服务接口
 */
public interface UserAuthzService<T extends Serializable> {
    /**
     * 获取用户enforcer
     *
     * @param user
     * @return
     */
    Enforcer getEnforcer(T user);
}
