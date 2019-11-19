package com.zy.mylib.mvc.logger;

import com.zy.mylib.data.jpa.UUIDBaseEntity;
import com.zy.mylib.security.LoginUser;

/**
 * 实体操作历史服务
 *
 * @author 扬
 * @date 2017/5/15
 */
public interface OperateHistoryService<UT extends LoginUser> {
  /**
   * 新增历史记录
   *
   * @param json   实体JSON
   * @param type   操作类型
   * @param user   操作用户
   * @param target 目标实体
   */
  void addHistory(String json, String type, UT user, Object target);
}
