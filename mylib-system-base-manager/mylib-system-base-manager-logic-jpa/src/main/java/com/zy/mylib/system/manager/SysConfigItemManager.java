package com.zy.mylib.system.manager;


import com.zy.mylib.system.entity.SysConfigItem;
import com.zy.mylib.data.jpa.JpaManager;

/**
 * 系统配置管理类
 * @author ASUS
 */
public interface SysConfigItemManager extends JpaManager<SysConfigItem, String> {
    /**
     * 根据code获取配置
     * @param code
     * @return
     */
    SysConfigItem findByCode(String code);

    /**
     * 获取配置
     * @param code
     * @return
     */
    String getConfigValue(String code, String defaultValue);

    /**
     * 改变配置值
     * @param code
     * @param value
     * @return
     */
    String changeConfigValue(String code, String value);
}
