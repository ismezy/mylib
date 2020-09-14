/*
 * Copyright © ${project.inceptionYear} ismezy (ismezy@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zy.mylib.mybatis.manager;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.mylib.base.exception.BusException;
import com.zy.mylib.utils.BeanUtils;
import com.zy.mylib.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ASUS
 */
public class MyBatisBaseManagerImpl<T, M extends BaseMapper<T>>
        extends ServiceImpl<M, T> implements MyBatisBaseManager<T> {

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public T add(T entity) {
        T exist = findExist(entity);
        if (exist != null) {
            throw BusException.builder().message(getEntityDescription(entity) + "已存在").build();
        }
        addProcess(entity);
        save(entity);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public T update(T entity) {
        T exist = findExist(entity);
        if (exist != null) {
            String existId = null;
            String newId = null;
            try {
                existId = (String) BeanUtils.getProperty(exist, "id");
                newId = (String) BeanUtils.getProperty(entity, "id");
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!StringUtils.equals(existId, newId) && exist != null && newId != null) {
                throw BusException.builder().message(getEntityDescription(entity) + "已存在").build();
            }
        }
        updateProcess(entity);
        updateById(entity);
        return entity;
    }

    protected String getEntityDescription(T entity) {
        String ret = "";
        ApiModel apiModel = entity.getClass().getAnnotation(ApiModel.class);
        if(apiModel != null) {
            ret = apiModel.description();
        } else {
            ret = entity.getClass().getName();
        }
        return ret;
    }

    /**
     * 新增保存前处理方法
     *
     * @param entity
     */
    protected T addProcess(T entity) {
        return entity;
    }

    /**
     * 更新保存前处理方法
     * @param entity
     * @return
     */
    protected T updateProcess(T entity) {
        return entity;
    }

    /**
     * 查找已存在数据，用于新增(add)和修改(update)时判断有无重复数据，如根据唯一编号，状态等获取唯一数据。
     *
     * @param entity
     * @return
     */
    protected T findExist(T entity) {
        return null;
    }
}
