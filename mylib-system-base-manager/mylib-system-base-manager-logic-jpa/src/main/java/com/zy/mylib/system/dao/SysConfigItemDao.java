package com.zy.mylib.system.dao;


import com.zy.mylib.system.entity.SysConfigItem;
import com.zy.mylib.data.jpa.JpaRepository;

import java.util.Optional;

/**
 * 系统配置JPA接口
 *
 * @author ASUS
 */
public interface SysConfigItemDao extends JpaRepository<SysConfigItem, String> {
  /**
   * 按code查找
   *
   * @param s
   * @return
   */
  Optional<SysConfigItem> findByCode(String s);
}
