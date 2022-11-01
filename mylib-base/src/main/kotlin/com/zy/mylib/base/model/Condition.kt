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
package com.zy.mylib.base.model

/**
 * 查询条件
 */
class Condition(
    var logicalOperator: LogicalOperators = LogicalOperators.And,

    /**
     * 比较运算符 默认等于
     */
    var comparisonOperator: ComparisonOperators = ComparisonOperators.eq,

    /**
     * 条件属性
     */
    var property: String? = null,

    /**
     * 条件值
     */
    var value: Any? = null,

    /**
     * 多值条件
     */
    var values: List<Any>? = null,

    /**
     * 条件集合，此字段不为空时忽略property、value、values、comparisonOperator字段
     */
    var conditions: List<Condition>? = null) {
  /**
   * 构造相等表达式
   */
  constructor(property: String, value: Any) : this(property, value, ComparisonOperators.eq) {
  }

  constructor(property: String, value: Any, operate: ComparisonOperators) : this() {
    this.property = property
    this.value = value
    comparisonOperator = operate;
  }

  constructor(property: String, values: List<Any>, operate: ComparisonOperators) : this() {
    this.property = property
    this.values = values
    comparisonOperator = operate;
  }

  constructor(conditions: List<Condition>): this(conditions, LogicalOperators.And) {
  }

  constructor(conditions: List<Condition>, operate: LogicalOperators): this() {
    this.conditions = conditions;
    this.logicalOperator = operate
  }

  companion object {
    @JvmStatic
    inline fun and(vararg conditions: Condition): Condition {
      return Condition(listOf(*conditions));
    }
    @JvmStatic
    inline fun or(vararg conditions: Condition): Condition {
      return Condition(listOf(*conditions), LogicalOperators.Or);
    }
  }
}
