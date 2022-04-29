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
 * 比较运算符
 */
enum class ComparisonOperators {
  /**
   * 等于
   */
  eq,

  /**
   * 不等于
   */
  neq,

  /**
   * 大于
   */
  gt,

  /**
   * 小于
   */
  lt,

  /**
   * 大于等于
   */
  gte,

  /**
   * 小于等于
   */
  lte,

  /**
   * 模糊匹配
   */
  like,

  /**
   * 头匹配
   */
  startWith,

  /**
   * 尾匹配
   */
  endWith,

  /**
   * 之间
   */
  between,

  /**
   * 包含
   */
  `in`,

  /**
   * 不包含
   */
  notIn, isNull, notNull
}