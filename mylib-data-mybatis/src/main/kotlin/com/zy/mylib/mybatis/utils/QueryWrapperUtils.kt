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
package com.zy.mylib.mybatis.utils

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.zy.mylib.base.model.*

object QueryWrapperUtils {
    /**
     * 构建查询条件
     *
     * @param sortRequests
     * @return
     */
    fun <T> build(condition: List<Condition>, sortRequests: List<SortRequest>?): QueryWrapper<T> {
        var query = Wrappers.query<T>()
        query = buildGroup(query, condition)
        query = buildSort(query, sortRequests)
        return query
    }

    private fun <T> buildSort(query: QueryWrapper<T>, sortRequests: List<SortRequest>?): QueryWrapper<T> {
        var query = query
        if (sortRequests == null || sortRequests.size == 0) return query
        for (sort in sortRequests) {
            query = query.orderBy(true,
                    sort.direction == SortRequest.SortDirection.Ascend, sort.property)
        }
        return query
    }

    fun <T> build(params: Map<String, Any?>, sortRequests: List<SortRequest>?): QueryWrapper<T> {
        var query = Wrappers.query<T>()
        for ((key, value) in params) {
            query = query.eq(key, value)
        }
        query = buildSort(query, sortRequests)
        return query
    }

    private fun <T> buildGroup(query: QueryWrapper<T>, conditions: List<Condition>): QueryWrapper<T> {
        for (condition in conditions) {
            if (!condition.conditions.isNullOrEmpty()) {
                // 条件集
                if (condition.logicalOperator == LogicalOperators.Or) {
                    query.or { buildGroup(it, condition.conditions!!) }
                } else {
                    query.and { buildGroup(it, condition.conditions!!) }
                }
            } else {
                // 单条件
                if (condition.logicalOperator == LogicalOperators.Or) {
                    query.or { buildCondition(it, condition) }
                } else {
                    query.and { buildCondition(it, condition) }
                }
            }
        }
        return query
    }

    private fun <T> buildCondition(query: QueryWrapper<T>, condition: Condition): QueryWrapper<T> {
        return if (condition.comparisonOperator == null) {
            query.eq(condition.property, condition.value)
        } else when (condition.comparisonOperator) {
            ComparisonOperators.neq -> query.ne(condition.property, condition.value)
            ComparisonOperators.gt -> query.gt(condition.property, condition.value)
            ComparisonOperators.gte -> query.ge(condition.property, condition.value)
            ComparisonOperators.lt -> query.lt(condition.property, condition.value)
            ComparisonOperators.lte -> query.le(condition.property, condition.value)
            ComparisonOperators.like -> query.like(condition.property, condition.value)
            ComparisonOperators.startWith -> query.likeRight(condition.property, condition.value)
            ComparisonOperators.endWith -> query.likeLeft(condition.property, condition.value)
            ComparisonOperators.`in` -> query.`in`(condition.property, condition.values)
            ComparisonOperators.notIn -> query.notIn(condition.property, condition.values)
            ComparisonOperators.between -> query.between(condition.property, condition.values!![0], condition.values!![1])
            else -> query.eq(condition.property, condition.value)
        }
    }
}