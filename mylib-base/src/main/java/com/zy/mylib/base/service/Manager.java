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
package com.zy.mylib.base.service;

import com.zy.mylib.base.model.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author ASUS
 */
public interface Manager<T extends BaseModel, PK extends Serializable> {
  /**
   * 新增
   *
   * @param entity
   * @return
   */
  T add(T entity);

  /**
   * 更新
   *
   * @param entity
   * @return
   */
  T update(T entity);

  /**
   * 删除
   *
   * @param id
   */
  void delete(PK id);

  /**
   * 查找所有
   *
   * @return
   */
  List<T> all();

  /**
   * 分页查询
   *
   * @param request
   * @param conditionGroup
   * @return
   */
  PageResponse<T> pageQuery(PageRequest request, List<Condition> conditionGroup);

  /**
   * 根据id查找
   *
   * @param id
   * @return
   */
  T findById(PK id);

  /**
   * 根据字段值查找唯一记录
   * @param property 字段名
   * @param value 字段值
   * @return
   */
  T findOne(String property, Object value);

  /**
   * 根据条件查找唯一记录
   * @return
   */
  T findOne(List<Condition> conditions);

  /**
   * 根据条件查找唯一记录
   * @param params
   * @return
   */
  T findOne(Map<String, Object> params);

  /**
   * 查找列表
   * @param property
   * @param value
   * @param sortRequest
   * @return
   */
  List<T> findList(String property, Object value, List<SortRequest> sortRequest);

  /**
   * 查找列表
   * @param sortRequest
   * @return
   */
  List<T> findList(List<Condition> conditions, List<SortRequest> sortRequest);

  /**
   * 查找列表
   * @param params
   * @param sortRequests
   * @return
   */
  List<T> findList(Map<String, Object> params, List<SortRequest> sortRequests);
}
