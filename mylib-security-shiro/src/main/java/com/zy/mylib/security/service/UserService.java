package com.zy.mylib.security.service;

import java.util.Optional;

/**
 * @author 扬
 * @date 2017/5/12
 */
public interface UserService<T> {
  /**
   * 根据登录名查找用户
   *
   * @param loginName
   * @return
   */
  T findByLoginName(String loginName, String type, String cloudId);

  /**
   * 按ID查找
   *
   * @param id
   * @return
   */
  Optional<T> findById(String id);
}
