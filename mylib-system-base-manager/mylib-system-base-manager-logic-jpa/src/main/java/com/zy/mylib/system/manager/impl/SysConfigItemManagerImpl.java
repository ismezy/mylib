package com.zy.mylib.system.manager.impl;

import com.zy.mylib.system.dao.SysConfigItemDao;
import com.zy.mylib.system.entity.SysConfigItem;
import com.zy.mylib.system.manager.SysConfigItemManager;
import com.zy.mylib.base.exception.BusException;
import com.zy.mylib.data.jpa.BaseJpaManager;
import com.zy.mylib.data.jpa.JpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 系统配置管理类实现
 *
 * @author ASUS
 */
@Service
@CacheConfig(cacheNames = "systemConfigCache")
public class SysConfigItemManagerImpl extends BaseJpaManager<SysConfigItem, String> implements SysConfigItemManager {
    @Autowired
    private SysConfigItemDao sysConfigItemDao;

    @Override
    protected JpaRepository<SysConfigItem, String> getRepository() {
        return sysConfigItemDao;
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = RuntimeException.class)
    public SysConfigItem save(SysConfigItem entity) {
        return super.save(entity);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = RuntimeException.class)
    public void remove(String id) {
        super.remove(id);
    }

    @Override
    public SysConfigItem findByCode(String code) {
        return sysConfigItemDao.findByCode(code).get();
    }

    @Override
    @Cacheable(key = "#{code}")
    public String getConfigValue(String code, String defaultValue) {
        try {
            return this.findByCode(code).getValue();
        } catch (Exception e) {
        }
        SysConfigItem item = new SysConfigItem();
        item.setCode(code);
        item.setValue(defaultValue);
        return save(item).getValue();
    }

    @Override
    @CacheEvict(allEntries = true)
    public String changeConfigValue(String code, String value) {
        SysConfigItem item = this.findByCode(code);
        item.setValue(value);
        return save(item).getValue();
    }
}
