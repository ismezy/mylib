package com.zy.mylib.security.casbin;

import org.casbin.jcasbin.main.Enforcer;

/**
 * enforcer管理
 */
public interface EnforcerManager {
   /**
    * 获取enforcer
    * @return
    */
   Enforcer getEnforcer(String user);
}