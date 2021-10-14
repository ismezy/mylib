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
package com.zy.mylib.mongo.manager.impl;


import com.zy.mylib.base.exception.BusException;
import com.zy.mylib.base.i18n.I18n;
import com.zy.mylib.base.model.*;
import com.zy.mylib.base.service.Manager;
import com.zy.mylib.mongo.manager.BaseMongoManager;
import com.zy.mylib.mongo.repos.BaseMongoRepository;
import com.zy.mylib.mongo.utils.PageUtils;
import com.zy.mylib.utils.BeanUtils;
import com.zy.mylib.utils.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * mongodb基础管理实现类
 *
 * @param <T>
 * @param <PK>
 * @author 周扬
 */
public abstract class BaseMongoManagerImpl<T extends BaseModel, PK extends Serializable> extends I18n implements Manager<T, PK> {
    protected MongoTemplate mongoTemplate;

    @Inject
    public BaseMongoManagerImpl<T, PK> setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        return this;
    }

    @Override
    public T findById(PK id) {
        return getRepository().findById(id).orElse(null);
    }

    @Override
    public List<T> all() {
        return getRepository().findAll();
    }

    @Override
    public T add(T entity) {
        T exist = findExist(entity);
        if (exist != null) {
            throw BusException.builder().message(entity.description() + "已存在").build();
        }
        addProcess(entity);
        return save(entity);
    }

    @Override
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
                throw BusException.builder().message(entity.description() + "已存在").build();
            }
        }
        updateProcess(entity);
        return save(entity);
    }

    @Override
    public void delete(PK id) {
        getRepository().deleteById(id);
    }

    @Override
    public T findOne(String property, Object value) {
        Criteria criteria = Criteria.where(property).is(value);
        Query query = Query.query(criteria);
        return mongoTemplate.findOne(query, getTClass());
    }

    @Override
    public T findOne(List<Condition> conditions) {
        Criteria criteria = createCriteria(conditions);
        Query query = Query.query(criteria);
        return mongoTemplate.findOne(query, getTClass());
    }

    @Override
    public T findOne(Map<String, Object> params) {
        Criteria criteria = new Criteria();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            criteria.and(entry.getKey()).is(entry.getValue());
        }
        Query query = Query.query(criteria);
        return mongoTemplate.findOne(query, getTClass());
    }

    /**
     * 分页查询
     */
    @Override
    public PageResponse<T> pageQuery(PageRequest request, List<Condition> conditionGroup) {
        Criteria criteria = createCriteria(conditionGroup);
        Query query = Query.query(criteria);
        long count = mongoTemplate.count(query, getTClass());
        query.with(PageUtils.toSpringPageRequest(request));
        List<T> list = mongoTemplate.find(query, getTClass());
        return PageResponse.fromRequest(request, count, list);
    }

    protected Criteria createCriteria(List<Condition> conditionGroup) {
        Criteria criteria = new Criteria();
        for (Condition condition : conditionGroup) {
            if(condition.getConditions() != null && condition.getConditions().size() > 0) {
                // 条件组
                if(LogicalOperators.or.equals(condition.getLogicalOperator())) {
                    criteria.orOperator(createCriteria(condition.getConditions()));
                } else {
                    criteria.andOperator(createCriteria(condition.getConditions()));
                }
            } else {
                // 条件
                if(LogicalOperators.or.equals(condition.getLogicalOperator())) {
                    criteria.orOperator(createCriteria(condition));
                } else {
                    criteria.andOperator(createCriteria(condition));
                }
            }

        }
        return criteria;
    }

    private Criteria createCriteria(Condition condition) {
        switch (condition.getComparisonOperator()) {
            case like:
                return new Criteria(condition.getProperty()).regex("^.*" + condition.getValue() + ".*$");
            case endWith:
                return new Criteria(condition.getProperty()).regex("^" + condition.getValue() + ".*$");
            case startWith:
                return new Criteria(condition.getProperty()).regex("^.*" + condition.getValue() + "$");
            case neq:
                return new Criteria(condition.getProperty()).ne(condition.getValue());
            case in:
                return new Criteria(condition.getProperty()).in(condition.getValues());
            case notIn:
                return new Criteria(condition.getProperty()).in(condition.getValues()).not();
            case notNull:
                return new Criteria(condition.getProperty()).ne(null);
            case isNull:
                return new Criteria(condition.getProperty()).is(null);
            case gt:
                return new Criteria(condition.getProperty()).gt(condition.getValue());
            case gte:
                return new Criteria(condition.getProperty()).gte(condition.getValue());
            case lt:
                return new Criteria(condition.getProperty()).lt(condition.getValue());
            case lte:
                return new Criteria(condition.getProperty()).lte(condition.getValue());
            default:
                return new Criteria(condition.getProperty()).is(condition.getValue());
        }
    }

    @Override
    public List<T> findList(List<Condition> conditions, List<SortRequest> sortRequest) {
        Criteria criteria = createCriteria(conditions);
        Query query = Query.query(criteria);
        query.with(PageUtils.toSpringSort(sortRequest));
        return mongoTemplate.find(query, getTClass());
    }

    /**
     * 查找列表
     * @param params
     * @param sortRequests
     * @return
     */
    @Override
    public List<T> findList(Map<String, Object> params, List<SortRequest> sortRequests) {
        Criteria criteria = new Criteria();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            criteria.and(entry.getKey()).is(entry.getValue());
        }
        Query query = Query.query(criteria);
        query.with(PageUtils.toSpringSort(sortRequests));
        return mongoTemplate.find(query, getTClass());
    }

    @Override
    public List<T> findList(String property, Object value, List<SortRequest> sortRequest) {
        Criteria criteria = Criteria.where(property).is(value);
        Query query = Query.query(criteria);
        query.with(PageUtils.toSpringSort(sortRequest));
        return mongoTemplate.find(query, getTClass());
    }

    /**
     * 查找已存在数据
     *
     * @param entity
     * @return
     */
    protected T findExist(T entity) {
        return null;
    }

    /**
     * 新增保存前处理方法
     *
     * @param entity
     */
    protected void addProcess(T entity) {
    }

    protected T save(T entity) {
        return getRepository().save(entity);
    }

    /**
     * 获取实体对应的Repository
     *
     * @return
     */
    protected abstract BaseMongoRepository<T, PK> getRepository();

    /**
     * 修改保存前处理方法
     *
     * @param entity
     */
    protected void updateProcess(T entity) {
    }

    protected Class<T> getTClass() {
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }
}
