/**
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
package com.zy.mylib.data.jpa

import com.zy.mylib.base.exception.BusException.Companion.builder
import java.util.HashMap
import javax.inject.Inject
import java.lang.StringBuffer
import java.lang.RuntimeException
import java.util.stream.Collectors
import com.zy.mylib.base.model.SortRequest.SortDirection
import com.zy.mylib.base.i18n.I18n
import com.zy.mylib.base.model.*
import com.zy.mylib.base.model.PageResponse.Companion.fromRequest
import com.zy.mylib.base.service.Manager
import com.zy.mylib.utils.BeanUtils
import com.zy.mylib.utils.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable
import java.lang.Exception
import java.lang.StringBuilder
import java.lang.reflect.ParameterizedType
import java.util.ArrayList
import javax.persistence.EntityManager
import javax.persistence.TypedQuery

/**
 * @author ASUS
 */
abstract class BaseJpaManager<T : JpaEntity, PK : Serializable> : I18n(), Manager<T, PK> {
  /**
   * 获取entityManager
   *
   * @return
   */
  @Inject
  val entityManager: EntityManager? = null

  inner class WhereAndParam {
    var where = StringBuffer(500)
    var params: MutableMap<String, Any?> = HashMap(30)
    fun whereSql(): String {
      return if (where.isNotEmpty()) "where $where" else ""
    }
  }

  protected open val tClass: Class<T>
    protected get() = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>

  /**
   * 获取实体对应的Repository
   *
   * @return
   */
  protected abstract val repository: JpaRepository<T?, PK?>
  override fun findById(id: PK): T? {
    return repository.findById(id).orElse(null)
  }

  override fun all(): List<T>? {
    return repository.findAll() as List<T>
  }

  private fun save(entity: T): T {
    return repository.save(entity)
  }

  @Transactional(rollbackFor = [RuntimeException::class])
  override fun delete(id: PK) {
    repository.deleteById(id)
  }

  @Transactional(rollbackFor = [RuntimeException::class])
  override fun add(entity: T): T {
    val exist = findExist(entity)
    if (exist != null) {
      throw builder().message(entity!!.description() + "已存在").build()
    }
    addProcess(entity)
    return save(entity)
  }

  @Transactional(rollbackFor = [RuntimeException::class])
  override fun update(entity: T): T {
    val exist = findExist(entity)
    if (exist != null) {
      var existId: String? = null
      var newId: String? = null
      try {
        existId = BeanUtils.getProperty(exist, "id") as String
        newId = BeanUtils.getProperty(entity, "id") as String
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

  /**
   * 根据字段值查找唯一记录
   *
   * @param property 字段名
   * @param value    字段值
   * @return
   */
  override fun findOne(property: String, value: Any): T? {
    val query = buildPropertyQuery(property, value)
    return query.singleResult
  }

  /**
   * 根据条件查找唯一记录
   *
   * @RETURN
   */
  override fun findOne(conditions: List<Condition>): T? {
    val entityName = javaClass.name
    val whereAndParam = genWhereAndParams(conditions)
    val jpql = "select t from " + entityName + "  t " + whereAndParam.whereSql()
    val query = entityManager!!.createQuery(jpql, tClass)
    for ((key, value) in whereAndParam.params) {
      query.setParameter(key, value)
    }
    return query.singleResult
  }

  /**
   * 生成查询条件语句和参数
   * @param conditions
   * @return
   */
  private fun genWhereAndParams(conditions: List<Condition>?): WhereAndParam {
    val whereAndParam = WhereAndParam()
    buildGroup(conditions, whereAndParam)
    return whereAndParam
  }

  private fun buildGroup(conditions: List<Condition>?, whereAndParam: WhereAndParam) {
    if (conditions.isNullOrEmpty()) {
      return
    }
    whereAndParam.where.append("(")
    var first = true
    for (condition in conditions) {
      if (!first) {
        whereAndParam.where.append(" ").append(condition.logicalOperator).append(" ")
      }
      if (!condition.conditions.isNullOrEmpty()) {
        buildGroup(condition.conditions, whereAndParam)
      } else {
        buildCondition(condition, whereAndParam)
      }
      first = false
    }
    whereAndParam.where.append(")")
  }

  private fun buildCondition(condition: Condition, whereAndParam: WhereAndParam) {
    whereAndParam.where.append(getCondition(condition.comparisonOperator,
        condition.property))
    if (ComparisonOperators.`in` === condition.comparisonOperator
        || ComparisonOperators.notIn === condition.comparisonOperator) {
      // 数组值
      whereAndParam.params[condition.property!!.replace("\\.".toRegex(), "_")] = condition.values
    } else {
      whereAndParam.params[condition.property!!.replace("\\.".toRegex(), "_")] = condition.value
    }
  }

  /**
   * 修改保存前处理方法
   *
   * @param entity
   */
  protected open fun updateProcess(entity: T) {}

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

  private fun getCondition(operate: ComparisonOperators?, prop: String?): String {
    val paramName = prop!!.replace("\\.".toRegex(), "_")
    return when (operate) {
      ComparisonOperators.like -> "t.$prop like concat('%', :$paramName , '%')"
      ComparisonOperators.endWith -> "t.$prop like concat('%', :$paramName)"
      ComparisonOperators.startWith -> "t.$prop like concat(:$paramName, '%')"
      ComparisonOperators.neq -> "t.$prop <> :$paramName"
      ComparisonOperators.`in` -> "t.$prop in :$paramName"
      ComparisonOperators.notIn -> "t.$prop not in :$paramName"
      ComparisonOperators.notNull -> "t.$prop is not null"
      ComparisonOperators.isNull -> "t.$prop is null"
      ComparisonOperators.gt -> "t.$prop > :$paramName"
      ComparisonOperators.gte -> "t.$prop >= :$paramName"
      ComparisonOperators.lt -> "t.$prop < :$paramName"
      ComparisonOperators.lte -> "t.$prop <= :$paramName"
      else -> "t.$prop = :$paramName"
    }
  }

  override fun findList(conditions: List<Condition>, sortRequest: List<SortRequest>): List<T>? {
    val whereParams: List<Any> = ArrayList(20)
    val whereAndParam = genWhereAndParams(conditions)
    val orderByString = genOrderBy(sortRequest)
    val entityName = tClass.name
    val jpql = "select t from " + entityName + "  t " + whereAndParam.whereSql() + " " + orderByString
    val query = entityManager!!.createQuery(jpql, tClass)
    for (i in whereParams.indices) {
      query.setParameter(i, whereParams[i])
    }
    return query.resultList
  }

  /**
   * 根据条件查找唯一记录
   * @param params
   * @return
   */
  override fun findOne(params: Map<String, Any>): T? {
    val query = buildQuery(params)
    return query.singleResult
  }

  private fun buildQuery(params: Map<String, Any>): TypedQuery<T> {
    val tClass = tClass
    val entityName = tClass.name
    val where = java.lang.String.join(" and ",
        params.keys.stream().map { String.format("t.%s=:%s", it, it) }.collect(Collectors.toList()))
    val jpql = String.format("select t from %s t where %s", entityName, where)
    val query = entityManager!!.createQuery(jpql, tClass)
    for ((key, value) in params) {
      query.setParameter(key, value)
    }
    return query
  }

  /**
   * 查找列表
   * @param params
   * @param sortRequests
   * @return
   */
  override fun findList(params: Map<String, Any>, sortRequests: List<SortRequest>): List<T>? {
    val query = buildQuery(params)
    return query.resultList
  }

  /**
   * 查找列表
   * @param property
   * @param value
   * @param sortRequest
   * @return
   */
  override fun findList(property: String, value: Any, sortRequest: List<SortRequest>): List<T>? {
    val query = buildPropertyQuery(property, value)
    return query.resultList
  }

  private fun buildPropertyQuery(property: String, value: Any): TypedQuery<T> {
    val tClass = tClass
    val entityName = tClass.name
    val jpql = String.format("select t from %s t where %s = ?0", entityName, property)
    val query = entityManager!!.createQuery(jpql, tClass)
    query.setParameter(0, value)
    return query
  }

  override fun pageQuery(request: PageRequest, conditionGroup: List<Condition>): PageResponse<T> {
    val whereAndParams = genWhereAndParams(conditionGroup)
    val orderByString = genOrderBy(request.sortRequests)
    val entityName = tClass.name
    val jpql = "select t from $entityName  t where t.id in ?1 $orderByString"
    val countJpql = "select count(t) from " + entityName + " t " + whereAndParams.whereSql()
    val queryIdJpql = "select t.id from " + entityName + " t " + whereAndParams.whereSql() + " " + orderByString
    logger.debug(queryIdJpql)
    val countQuery = entityManager!!.createQuery(countJpql, Long::class.java)
    val query = entityManager.createQuery(jpql, tClass)
    val idsQuery = entityManager.createQuery(queryIdJpql, String::class.java)
    for ((key, value) in whereAndParams.params) {
      countQuery.setParameter(key, value)
      idsQuery.setParameter(key, value)
    }
    idsQuery.setFirstResult(request.page.toInt() * request.size.toInt()).maxResults = request.size.toInt()
    val ids = idsQuery.resultList
    val count = countQuery.singleResult
    var content: List<T>? = ArrayList()
    if (ids.size > 0) {
      query.setParameter(1, ids)
      content = query.resultList
    }
    return fromRequest(request, count, content)
  }

  private fun genOrderBy(sortRequest: List<SortRequest>?): String {
    val odb = StringBuilder()
    if (sortRequest != null) {
      for (sort in sortRequest) {
        if (odb.isEmpty()) {
          odb.append("order by ")
        } else {
          odb.append(",")
        }
        odb.append(sort.property).append(" ").append(if (SortDirection.Descend == sort.direction) "desc" else "asc")
      }
    }
    return odb.toString()
  }

  companion object {
    private val logger = LoggerFactory.getLogger(BaseJpaManager::class.java)
  }
}