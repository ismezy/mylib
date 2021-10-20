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
package com.zy.mylib.data.jpa;

import com.zy.mylib.base.exception.BusException;
import com.zy.mylib.base.i18n.I18n;
import com.zy.mylib.base.model.*;
import com.zy.mylib.base.service.Manager;
import com.zy.mylib.utils.BeanUtils;
import com.zy.mylib.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.zy.mylib.base.model.SortRequest.SortDirection.Descend;


/**
 * @author ASUS
 */
public abstract class BaseJpaManager<T extends JpaEntity, PK extends Serializable> extends I18n implements Manager<T, PK> {
  private static final Logger logger = LoggerFactory.getLogger(BaseJpaManager.class);
  @Inject
  private EntityManager entityManager;


  class WhereAndParam {
    StringBuffer where = new StringBuffer(500);
    Map<String, Object> params = new HashMap<>(30);

    public String whereSql() {
      return where.length() > 0 ? "where " + where : "";
    }
  }


  protected Class<T> getTClass() {
    Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    return tClass;
  }

  /**
   * 获取entityManager
   *
   * @return
   */
  public EntityManager getEntityManager() {
    return entityManager;
  }

  /**
   * 获取实体对应的Repository
   *
   * @return
   */
  protected abstract JpaRepository<T, PK> getRepository();

  @Override
  public T findById(PK id) {
    return getRepository().findById(id).orElse(null);
  }

  @Override
  public List<T> all() {
    return (List<T>) getRepository().findAll();
  }

  protected T save(T entity) {
    return getRepository().save(entity);
  }

  @Override
  @Transactional(rollbackFor = RuntimeException.class)
  public void delete(PK id) {
    getRepository().deleteById(id);
  }

  @Override
  @Transactional(rollbackFor = RuntimeException.class)
  public T add(T entity) {
    T exist = findExist(entity);
    if (exist != null) {
      throw BusException.builder().message(entity.description() + "已存在").build();
    }
    addProcess(entity);
    return save(entity);
  }

  @Override
  @Transactional(rollbackFor = RuntimeException.class)
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

  /**
   * 根据字段值查找唯一记录
   *
   * @param property 字段名
   * @param value    字段值
   * @return
   */
  @Override
  public T findOne(String property, Object value) {
    TypedQuery<T> query = buildPropertyQuery(property, value);
    return query.getSingleResult();
  }

  /**
   * 根据条件查找唯一记录
   *
   * @RETURN
   */
  @Override
  public T findOne(List<Condition> conditions) {
    String entityName = getClass().getName();
    WhereAndParam whereAndParam = genWhereAndParams(conditions);

    String jpql = "select t from " + entityName + "  t " + whereAndParam.whereSql();

    TypedQuery<T> query = entityManager.createQuery(jpql, getTClass());
    for (Map.Entry<String, Object> entry: whereAndParam.params.entrySet()) {
      query.setParameter(entry.getKey(), entry.getValue());
    }

    return query.getSingleResult();
  }

  /**
   * 生成查询条件语句和参数
   * @param conditions
   * @return
   */
  protected WhereAndParam genWhereAndParams(List<Condition> conditions) {
    WhereAndParam whereAndParam = new WhereAndParam();
    buildGroup(conditions, whereAndParam);
    return whereAndParam;
  }

  private void buildGroup(List<Condition> conditions, WhereAndParam whereAndParam) {
    if(conditions.size() == 0) {
      return;
    }
    whereAndParam.where.append("(");
    boolean first = true;
    for (Condition condition : conditions) {
      if(!first) {
        whereAndParam.where.append(" ").append(condition.getLogicalOperator()).append(" ");
      }
      if(condition.getConditions() != null && condition.getConditions().size() > 0) {
        buildGroup(condition.getConditions(), whereAndParam);
      } else {
        buildCondition(condition, whereAndParam);
      }
      first = false;
    }
    whereAndParam.where.append(")");
  }

  private void buildCondition(Condition condition, WhereAndParam whereAndParam) {
      whereAndParam.where.append(getCondition(condition.getComparisonOperator(),
          condition.getProperty()));
      if(ComparisonOperators.in == condition.getComparisonOperator()
          || ComparisonOperators.notIn == condition.getComparisonOperator()) {
        // 数组值
        whereAndParam.params.put(condition.getProperty().replaceAll("\\.", "_"), condition.getValues());
      } else {
        whereAndParam.params.put(condition.getProperty().replaceAll("\\.", "_"), condition.getValue());
      }
  }

  /**
   * 修改保存前处理方法
   *
   * @param entity
   */
  protected void updateProcess(T entity) {
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


  public String getCondition(ComparisonOperators operate, String prop) {
    String paramName = prop.replaceAll("\\.", "_");
    switch (operate) {
      case like:
        return "t." + prop + " like concat('%', :" + paramName + " , '%')";
      case endWith:
        return "t." + prop + " like concat('%', :" + paramName + ")";
      case startWith:
        return "t." + prop + " like concat(:" + paramName + ", '%')";
      case neq:
        return "t." + prop + " <> :" + paramName;
      case in:
        return "t." + prop + " in :" + paramName;
      case notIn:
        return "t." + prop + " not in :" + paramName;
      case notNull:
        return "t." + prop + " is not null";
      case isNull:
        return "t." + prop + " is null";
      case gt:
        return "t." + prop + " > :" + paramName;
      case gte:
        return "t." + prop + " >= :" + paramName;
      case lt:
        return "t." + prop + " < :" + paramName;
      case lte:
        return "t." + prop + " <= :" + paramName;
/*
    TODO: 暂时不支持between
      case between:
        return prop + " between :" + prop + " and ?" + (index + 1);
 */
      default:
        return "t." + prop + " = :" + paramName;
    }
  }

  @Override
  public List<T> findList(List<Condition> conditions, List<SortRequest> sortRequest) {
    List<Object> whereParams = new ArrayList<>(20);
    WhereAndParam whereAndParam = genWhereAndParams(conditions);
    String orderByString = genOrderBy(sortRequest);

    String entityName = getTClass().getName();
    String jpql = "select t from " + entityName + "  t " + whereAndParam.whereSql() + " " + orderByString;
    TypedQuery<T> query = entityManager.createQuery(jpql, getTClass());
    for (int i = 0; i < whereParams.size(); i++) {
      query.setParameter(i, whereParams.get(i));
    }
    return query.getResultList();
  }

  /**
   * 根据条件查找唯一记录
   * @param params
   * @return
   */
  public T findOne(Map<String, Object> params) {
    TypedQuery<T> query = buildQuery(params);
    return query.getSingleResult();
  }

  private TypedQuery<T> buildQuery(Map<String, Object> params) {
    Class<T> tClass = getTClass();
    String entityName = tClass.getName();
    String where = String.join(" and ",
        params.keySet().stream().map(it -> String.format("t.%s=:%s", it, it)).collect(Collectors.toList()));
    String jpql = String.format("select t from %s t where %s", entityName, where);
    TypedQuery<T> query = entityManager.createQuery(jpql, tClass);
    for (Map.Entry<String, Object> entry : params.entrySet()) {
      query.setParameter(entry.getKey(), entry.getValue());
    }
    return query;
  }

  /**
   * 查找列表
   * @param params
   * @param sortRequests
   * @return
   */
  @Override
  public List<T> findList(Map<String, Object> params, List<SortRequest> sortRequests) {
    TypedQuery<T> query = buildQuery(params);
    return query.getResultList();
  }

  /**
   * 查找列表
   * @param property
   * @param value
   * @param sortRequest
   * @return
   */
  @Override
  public List<T> findList(String property, Object value, List<SortRequest> sortRequest) {
    TypedQuery<T> query = buildPropertyQuery(property, value);
    return query.getResultList();
  }

  private TypedQuery<T> buildPropertyQuery(String property, Object value) {
    Class<T> tClass = getTClass();
    String entityName = tClass.getName();
    String jpql = String.format("select t from %s t where %s = ?0", entityName, property);
    TypedQuery<T> query = entityManager.createQuery(jpql, tClass);
    query.setParameter(0, value);
    return query;
  }

  @Override
  public PageResponse<T> pageQuery(PageRequest request, List<Condition> conditionGroup) {
    WhereAndParam whereAndParams = genWhereAndParams(conditionGroup);
    String orderByString = genOrderBy(request.getSortRequests());

    String entityName = getTClass().getName();
    String jpql = "select t from " + entityName + "  t where t.id in ?1 " + orderByString;
    String countJpql = "select count(t) from " + entityName + " t " + whereAndParams.whereSql();
    String queryIdJpql = "select t.id from " + entityName + " t " + whereAndParams.whereSql() + " " + orderByString;
    logger.debug(queryIdJpql);
    TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class);
    TypedQuery<T> query = entityManager.createQuery(jpql, getTClass());
    TypedQuery<String> idsQuery = entityManager.createQuery(queryIdJpql, String.class);
    for (Map.Entry<String, Object> entry : whereAndParams.params.entrySet()) {
      countQuery.setParameter(entry.getKey(), entry.getValue());
      idsQuery.setParameter(entry.getKey(), entry.getValue());
    }

    idsQuery.setFirstResult(request.getPage().intValue() * request.getSize().intValue())
        .setMaxResults(request.getSize().intValue());
    List<String> ids = idsQuery.getResultList();
    Long count = countQuery.getSingleResult();
    List<T> content = new ArrayList<T>();
    if (ids.size() > 0) {
      query.setParameter(1, ids);
      content = query.getResultList();
    }
    return PageResponse.fromRequest(request, count, content);
  }

  private String genOrderBy(List<SortRequest> sortRequest) {
    StringBuilder odb = new StringBuilder();
    if (sortRequest != null) {
      for (SortRequest sort: sortRequest) {
        if (odb.length() == 0) {
          odb.append("order by ");
        } else {
          odb.append(",");
        }
        odb.append(sort.getProperty()).append(" ").append(Descend.equals(sort.getDirection()) ? "desc" : "asc");
      }
    }
    return odb.toString();
  }

}
