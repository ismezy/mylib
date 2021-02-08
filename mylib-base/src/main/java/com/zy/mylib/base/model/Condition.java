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
package com.zy.mylib.base.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 查询条件
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Condition {
  LogicalOperators logicalOperator = LogicalOperators.and;
  /**
   * 比较运算符 默认等于
   */
  ComparisonOperators comparisonOperator = ComparisonOperators.eq;
  /**
   * 条件属性
   */
  String property;
  /**
   * 条件值
   */
  Object value;
  /**
   * 多值条件
   */
  List<Object> values;

  /**
   * 条件集合，此字段不为空时忽略property、value、values、comparisonOperator字段
   */
  List<Condition> conditions;
}
