package com.zy.mylib.security.casbin;

import org.casbin.jcasbin.main.Enforcer;

/**
 * 用户权限服务接口
 */
public interface UserAuthzService {
    /**
     * 获取用户enforcer
     * @param user
     * @return
     */
    Enforcer getEnforcer(String user);
}
