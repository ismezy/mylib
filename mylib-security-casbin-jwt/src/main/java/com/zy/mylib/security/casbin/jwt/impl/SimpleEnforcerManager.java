package com.zy.mylib.security.casbin.jwt.impl;

import com.zy.mylib.security.LoginUser;
import com.zy.mylib.security.casbin.StringAdapter;
import com.zy.mylib.security.casbin.StringModel;
import com.zy.mylib.security.casbin.EnforcerManager;
import com.zy.mylib.security.casbin.ModelAndPolicy;
import com.zy.mylib.security.casbin.UserAuthzService;
import org.casbin.jcasbin.main.Enforcer;

/**
 * enforcer 管理基础实现
 */
public class SimpleEnforcerManager implements EnforcerManager<LoginUser> {
  /**
   * 用户权限服务
   */
  private UserAuthzService<LoginUser> userAuthzService;

  @Override
  public Enforcer getEnforcer(LoginUser user) {
    return userAuthzService.getEnforcer(user);
  }

  /**
   * Sets new 用户权限服务.
   *
   * @param userAuthzService New value of 用户权限服务.
   */
  public void setUserAuthzService(UserAuthzService userAuthzService) {
    this.userAuthzService = userAuthzService;
  }

  /**
   * Gets 用户权限服务.
   *
   * @return Value of 用户权限服务.
   */
  public UserAuthzService getUserAuthzService() {
    return userAuthzService;
  }
}
