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
package com.zy.mylib.mybatis.manager

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.zy.mylib.base.exception.BusException
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.zy.mylib.base.model.*
import com.zy.mylib.mybatis.utils.QueryWrapperUtils
import com.zy.mylib.base.service.Manager
import com.zy.mylib.utils.BeanUtils
import com.zy.mylib.utils.StringUtils
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable
import java.lang.Exception

/**
 * @author ASUS
 */
abstract class MyBatisBaseManagerImpl<T : BaseModel, PK : Serializable> : Manager<T, PK> {
    protected open abstract val mapper: BaseMapper<T>

    /**
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = [Exception::class])
    override fun add(entity: T): T {
        val exist = findExist(entity)
        if (exist != null) {
            throw BusException.builder().message(getEntityDescription(entity) + "已存在").build()
        }
        addProcess(entity)
        mapper.insert(entity)
        return entity
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = [Exception::class])
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
                throw BusException.builder().message(getEntityDescription(entity) + "已存在").build()
            }
        }
        updateProcess(entity)
        mapper.updateById(entity)
        return entity
    }

    override fun delete(id: PK) {
        mapper.deleteById(id)
    }

    override fun all(): List<T> {
        return mapper.selectList(Wrappers.emptyWrapper())
    }

    override fun pageQuery(request: PageRequest, conditionGroup: List<Condition>): PageResponse<T> {
        var page = Page<T>(request.page + 1, request.size)
        val queryWrapper: QueryWrapper<T> = QueryWrapperUtils.build<T>(conditionGroup, request.sortRequests)
        page = mapper.selectPage(page, queryWrapper)
        return PageResponse.fromRequest(request, page.total, page.records)
    }

    override fun findById(id: PK): T {
        return mapper.selectById(id)
    }

    override fun findOne(property: String, value: Any): T? {
        return mapper.selectOne(Wrappers.query<T>().eq(property, value))
    }

    override fun findOne(conditions: List<Condition>): T? {
        val queryWrapper = QueryWrapperUtils.build<T>(conditions, null)
        return mapper.selectOne(queryWrapper)
    }

    override fun findOne(params: Map<String, Any>): T? {
        val list = mapper.selectByMap(params)
        return if (list.size > 0) list[0] else null
    }

    override fun findList(property: String, value: Any, sortRequest: List<SortRequest>): List<T> {
        val queryWrapper = Wrappers.query<T>().eq(property, value)
        return mapper.selectList(queryWrapper)
    }

    override fun findList(conditions: List<Condition>, sortRequest: List<SortRequest>): List<T> {
        val queryWrapper = QueryWrapperUtils.build<T>(conditions, sortRequest)
        return mapper.selectList(queryWrapper)
    }

    override fun findList(params: Map<String, Any>, sortRequests: List<SortRequest>): List<T> {
        val queryWrapper = QueryWrapperUtils.build<T>(params, sortRequests)
        return mapper.selectList(queryWrapper)
    }

    protected open fun getEntityDescription(entity: T): String {
        return "数据"
    }

    /**
     * 新增保存前处理方法
     *
     * @param entity
     */
    protected open fun addProcess(entity: T): T {
        return entity
    }

    /**
     * 更新保存前处理方法
     * @param entity
     * @return
     */
    protected open fun updateProcess(entity: T): T {
        return entity
    }

    /**
     * 查找已存在数据，用于新增(add)和修改(update)时判断有无重复数据，如根据唯一编号，状态等获取唯一数据。
     *
     * @param entity
     * @return
     */
    protected open fun findExist(entity: T): T? {
        return null
    }
}