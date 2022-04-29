/*
 * Copyright © 2020 ismezy (ismezy@qq.com)
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
package com.zy.mylib.base.service

import com.zy.mylib.base.model.*
import java.io.Serializable

/**
 * @author ASUS
 */
interface Manager<T : BaseModel?, PK : Serializable> {
  /**
   * 新增
   *
   * @param entity
   * @return
   */
  fun add(entity: T): T

  /**
   * 更新
   *
   * @param entity
   * @return
   */
  fun update(entity: T): T

  /**
   * 删除
   *
   * @param id
   */
  fun delete(id: PK)

  /**
   * 查找所有
   *
   * @return
   */
  fun all(): List<T>?

  /**
   * 分页查询
   *
   * @param request
   * @param conditionGroup
   * @return
   */
  fun pageQuery(request: PageRequest, conditionGroup: List<Condition>): PageResponse<T>

  /**
   * 根据id查找
   *
   * @param id
   * @return
   */
  fun findById(id: PK): T?

  /**
   * 根据字段值查找唯一记录
   * @param property 字段名
   * @param value 字段值
   * @return
   */
  fun findOne(property: String, value: Any): T?

  /**
   * 根据条件查找唯一记录
   * @return
   */
  fun findOne(conditions: List<Condition>): T?

  /**
   * 根据条件查找唯一记录
   * @param params
   * @return
   */
  fun findOne(params: Map<String, Any>): T?

  /**
   * 查找列表
   * @param property
   * @param value
   * @param sortRequest
   * @return
   */
  fun findList(property: String, value: Any, sortRequest: List<SortRequest>): List<T>?

  /**
   * 查找列表
   * @param sortRequest
   * @return
   */
  fun findList(conditions: List<Condition>, sortRequest: List<SortRequest>): List<T>?

  /**
   * 查找列表
   * @param params
   * @param sortRequests
   * @return
   */
  fun findList(params: Map<String, Any>, sortRequests: List<SortRequest>): List<T>?
}