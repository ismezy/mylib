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

import com.zy.mylib.base.model.ConditionGroup;
import com.zy.mylib.base.model.PageRequest;
import com.zy.mylib.base.model.PageResponse;

import java.io.Serializable;
import java.util.List;

/**
 * @author ASUS
 */
public interface Manager<T> {
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

  /**
   * 删除
     * @param id
     */
    void delete(Serializable id);

    /**
     * 查找所有
     * @return
     */
    List<T> all();

    /**
     * 分页查询
     * @param request
     * @param conditionGroup
     * @return
     */
    PageResponse<T> pageQuery(PageRequest request, ConditionGroup conditionGroup);
}
