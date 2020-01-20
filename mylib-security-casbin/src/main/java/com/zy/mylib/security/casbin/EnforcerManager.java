package com.zy.mylib.security.casbin;

import org.casbin.jcasbin.main.Enforcer;

import java.io.Serializable;

/**
 * enforcer管理
 *
 * @author ASUS
 */
public interface EnforcerManager<T extends Serializable> {
    /**
     * 获取enforcer
     *
     * @return
     */
    Enforcer getEnforcer(T user);
}
