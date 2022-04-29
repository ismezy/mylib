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
package com.zy.mylib.mongo.manager.impl

import com.zy.mylib.base.exception.BusException.Companion.builder
import com.zy.mylib.base.model.PageResponse.Companion.fromRequest
import org.springframework.data.mongodb.core.MongoTemplate
import javax.inject.Inject
import com.zy.mylib.base.i18n.I18n
import com.zy.mylib.base.model.*
import com.zy.mylib.base.service.Manager
import com.zy.mylib.mongo.repos.BaseMongoRepository
import com.zy.mylib.mongo.utils.PageUtils
import com.zy.mylib.utils.BeanUtils
import com.zy.mylib.utils.StringUtils
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import java.io.Serializable
import java.lang.Exception
import java.lang.reflect.ParameterizedType

/**
 * mongodb基础管理实现类
 *
 * @param <T>
 * @param <PK>
 * @author 周扬
</PK></T> */
abstract class BaseMongoManagerImpl<T : BaseModel?, PK : Serializable> : I18n(), Manager<T, PK> {
  protected var mongoTemplate: MongoTemplate? = null
  @Inject
  fun setMongoTemplate(mongoTemplate: MongoTemplate?): BaseMongoManagerImpl<T, PK> {
    this.mongoTemplate = mongoTemplate
    return this
  }

  override fun findById(id: PK): T? {
    return repository.findById(id).orElse(null)
  }

  override fun all(): List<T>? {
    return repository.findAll()
  }

  override fun add(entity: T): T {
    val exist = findExist(entity)
    if (exist != null) {
      throw builder().message(entity!!.description() + "已存在").build()
    }
    addProcess(entity)
    return save(entity)
  }

  override fun update(entity: T): T {
    val exist = findExist(entity)
    if (exist != null) {
      var existId: String? = null
      var newId: String? = null
      try {
        existId = BeanUtils.getProperty(exist, "id") as String
        newId = BeanUtils.getProperty(entity as Any, "id") as String
      } catch (e: Exception) {
        e.printStackTrace()
      }
      if (!StringUtils.equals(existId, newId) && exist != null && newId != null) {
        throw builder().message(entity!!.description() + "已存在").build()
      }
    }
    updateProcess(entity)
    return save(entity)
  }

  override fun delete(id: PK) {
    repository.deleteById(id)
  }

  override fun findOne(property: String, value: Any): T? {
    val criteria = Criteria.where(property).`is`(value)
    val query = Query.query(criteria)
    return mongoTemplate!!.findOne(query, tClass)
  }

  override fun findOne(conditions: List<Condition>): T? {
    val criteria = createCriteria(conditions)
    val query = Query.query(criteria)
    return mongoTemplate!!.findOne(query, tClass)
  }

  override fun findOne(params: Map<String, Any>): T? {
    val criteria = Criteria()
    for ((key, value) in params) {
      criteria.and(key).`is`(value)
    }
    val query = Query.query(criteria)
    return mongoTemplate!!.findOne(query, tClass)
  }

  /**
   * 分页查询
   */
  override fun pageQuery(request: PageRequest, conditionGroup: List<Condition>): PageResponse<T> {
    val criteria = createCriteria(conditionGroup)
    val query = Query.query(criteria)
    val count = mongoTemplate!!.count(query, tClass)
    query.with(PageUtils.toSpringPageRequest(request))
    val list = mongoTemplate!!.find(query, tClass)
    return fromRequest(request, count, list)
  }

  protected fun createCriteria(conditionGroup: List<Condition>): Criteria {
    val criteria = Criteria()
    for (condition in conditionGroup) {
      if (condition.conditions != null && condition.conditions!!.size > 0) {
        // 条件组
        if (LogicalOperators.Or == condition.logicalOperator) {
          criteria.orOperator(createCriteria(condition.conditions!!))
        } else {
          criteria.andOperator(createCriteria(condition.conditions!!))
        }
      } else {
        // 条件
        if (LogicalOperators.Or == condition.logicalOperator) {
          criteria.orOperator(createCriteria(condition))
        } else {
          criteria.andOperator(createCriteria(condition))
        }
      }
    }
    return criteria
  }

  private fun createCriteria(condition: Condition): Criteria {
    return when (condition.comparisonOperator) {
      ComparisonOperators.like -> Criteria(condition.property).regex("^.*" + condition.value + ".*$")
      ComparisonOperators.endWith -> Criteria(condition.property).regex("^" + condition.value + ".*$")
      ComparisonOperators.startWith -> Criteria(condition.property).regex("^.*" + condition.value + "$")
      ComparisonOperators.neq -> Criteria(condition.property).ne(condition.value)
      ComparisonOperators.`in` -> Criteria(condition.property).`in`(condition.values)
      ComparisonOperators.notIn -> Criteria(condition.property).`in`(condition.values).not()
      ComparisonOperators.notNull -> Criteria(condition.property).ne(null)
      ComparisonOperators.isNull -> Criteria(condition.property).`is`(null)
      ComparisonOperators.gt -> Criteria(condition.property).gt(condition.value)
      ComparisonOperators.gte -> Criteria(condition.property).gte(condition.value)
      ComparisonOperators.lt -> Criteria(condition.property).lt(condition.value)
      ComparisonOperators.lte -> Criteria(condition.property).lte(condition.value)
      else -> Criteria(condition.property).`is`(condition.value)
    }
  }

  override fun findList(conditions: List<Condition>, sortRequest: List<SortRequest>): List<T>? {
    val criteria = createCriteria(conditions)
    val query = Query.query(criteria)
    query.with(PageUtils.toSpringSort(sortRequest))
    return mongoTemplate!!.find(query, tClass)
  }

  /**
   * 查找列表
   * @param params
   * @param sortRequests
   * @return
   */
  override fun findList(params: Map<String, Any>, sortRequests: List<SortRequest>): List<T>? {
    val criteria = Criteria()
    for ((key, value) in params) {
      criteria.and(key).`is`(value)
    }
    val query = Query.query(criteria)
    query.with(PageUtils.toSpringSort(sortRequests))
    return mongoTemplate!!.find(query, tClass)
  }

  override fun findList(property: String, value: Any, sortRequest: List<SortRequest>): List<T>? {
    val criteria = Criteria.where(property).`is`(value)
    val query = Query.query(criteria)
    query.with(PageUtils.toSpringSort(sortRequest))
    return mongoTemplate!!.find(query, tClass)
  }

  /**
   * 查找已存在数据
   *
   * @param entity
   * @return
   */
  protected open fun findExist(entity: T): T? {
    return null
  }

  /**
   * 新增保存前处理方法
   *
   * @param entity
   */
  protected open fun addProcess(entity: T) {}
  protected open fun save(entity: T): T {
    return repository.save(entity)
  }

  /**
   * 获取实体对应的Repository
   *
   * @return
   */
  protected open abstract val repository: BaseMongoRepository<T, PK>

  /**
   * 修改保存前处理方法
   *
   * @param entity
   */
  protected open fun updateProcess(entity: T) {}
  protected open val tClass: Class<T>
    protected get() = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
}
