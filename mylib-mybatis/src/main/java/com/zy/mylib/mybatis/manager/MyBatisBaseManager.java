package com.zy.mylib.mybatis.manager;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author ASUS
 */
public interface MyBatisBaseManager<T> extends IService<T> {
    /**
     * 新增
     * @param entity
     * @return
     */
    T add(T entity);

    /**
     * 更新
     * @param entity
     * @return
     */
    T update(T entity);
}
