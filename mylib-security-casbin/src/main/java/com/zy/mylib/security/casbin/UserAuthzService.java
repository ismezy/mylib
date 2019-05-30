package com.zy.mylib.security.casbin;

import com.zy.mylib.security.casbin.ModelAndPolicy;

/**
 * 用户权限服务接口
 */
public interface UserAuthzService {
    /**
     * 获取用户enforcer
     * @param user
     * @return
     */
    ModelAndPolicy getEnforcer(String user);
}
