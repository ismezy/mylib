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


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.mylib.base.exception.BusException;
import com.zy.mylib.base.model.ConditionGroup;
import com.zy.mylib.base.model.PageRequest;
import com.zy.mylib.base.model.PageResponse;
import com.zy.mylib.base.model.SortRequest;
import com.zy.mylib.mybatis.utils.QueryWrapperUtils;
import com.zy.mylib.utils.BeanUtils;
import com.zy.mylib.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author ASUS
 */
public abstract class MyBatisBaseManagerImpl<M extends BaseMapper<T>, T> implements MyBatisBaseManager<T> {

    protected M mapper;

    @Inject
    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

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
        mapper.insert(entity);
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
        mapper.updateById(entity);
        return entity;
    }

    @Override
    public void delete(Serializable id) {
        mapper.deleteById(id);
    }

    @Override
    public List<T> all() {
        return mapper.selectList(Wrappers.emptyWrapper());
    }

    @Override
    public PageResponse<T> pageQuery(PageRequest request, ConditionGroup conditionGroup) {
        Page<T> page = new Page<>(request.getPage() + 1, request.getSize());
        QueryWrapper queryWrapper =  QueryWrapperUtils.build(conditionGroup, request.getSortRequests());
        page = mapper.selectPage(page, queryWrapper);
        return PageResponse.fromRequest(request, page.getTotal(), page.getRecords());
    }

    @Override
    public T findById(Serializable id) {
        return mapper.selectById(id);
    }

    @Override
    public T findOne(String property, Object value) {
        return mapper.selectOne(Wrappers.<T>query().eq(property, value));
    }

    @Override
    public T findOne(ConditionGroup conditionGroup) {
        QueryWrapper<T> queryWrapper =  QueryWrapperUtils.build(conditionGroup, null);
        return mapper.selectOne(queryWrapper);
    }

    @Override
    public T findOne(Map<String, Object> params) {
        List<T> list = mapper.selectByMap(params);
        if(list.size() > 0) return list.get(0);
        return null;
    }

    @Override
    public List<T> findList(String property, Object value, List<SortRequest> sortRequest) {
        QueryWrapper<T> queryWrapper = Wrappers.<T>query().eq(property, value);
        return mapper.selectList(queryWrapper);
    }

    @Override
    public List<T> findList(ConditionGroup conditionGroup, List<SortRequest> sortRequest) {
        QueryWrapper<T> queryWrapper =  QueryWrapperUtils.build(conditionGroup, sortRequest);
        return mapper.selectList(queryWrapper);
    }

    @Override
    public List<T> findList(Map<String, Object> params, List<SortRequest> sortRequests) {
        QueryWrapper<T> queryWrapper =  QueryWrapperUtils.build(params, sortRequests);
        return mapper.selectList(queryWrapper);
    }

    protected String getEntityDescription(T entity) {
        return "数据";
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
