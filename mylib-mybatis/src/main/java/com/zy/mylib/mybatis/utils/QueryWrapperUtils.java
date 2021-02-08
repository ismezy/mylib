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
package com.zy.mylib.mybatis.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zy.mylib.base.model.BaseCondition;
import com.zy.mylib.base.model.Condition;
import com.zy.mylib.base.model.ConditionGroup;
import com.zy.mylib.base.model.LogicalOperators;

public class QueryWrapperUtils {
  /**
   * 构建查询条件
   * @param conditionGroup
   * @return
   */
  public static <T> QueryWrapper<T> build(ConditionGroup conditionGroup) {
    QueryWrapper<T> query = Wrappers.query();
    return buildGroup(query, conditionGroup);
  }

  private static <T> QueryWrapper<T> buildGroup(QueryWrapper<T> query, ConditionGroup group) {
    if(group.getConditions() == null) return query;
    for(BaseCondition condition : group.getConditions()) {
      if(condition instanceof Condition) {
        // 比较运算
        if(condition.getLogicalOperator() == LogicalOperators.or) {
          query.or(it -> buildCondition(it, (Condition) condition));
        } else {
          query.and(it -> buildCondition(it, (Condition) condition));
        }
      } else {
        if(condition.getLogicalOperator() == LogicalOperators.or) {
          query.or(it -> buildGroup(it, (ConditionGroup) condition));
        } else {
          query.and(it -> buildGroup(it, (ConditionGroup) condition));
        }
      }
    }
    return query;
  }

  private static <T> QueryWrapper<T> buildCondition(QueryWrapper<T> query, Condition condition) {
    if(condition.getComparisonOperator() == null) {
      return query.eq(condition.getProperty(), condition.getValue());
    }
    switch (condition.getComparisonOperator()) {
      case neq:
        return query.ne(condition.getProperty(), condition.getValue());
      case gt:
        return query.gt(condition.getProperty(), condition.getValue());
      case gte:
        return query.ge(condition.getProperty(), condition.getValue());
      case lt:
        return query.lt(condition.getProperty(), condition.getValue());
      case lte:
        return query.le(condition.getProperty(), condition.getValue());
      case like:
        return query.like(condition.getProperty(), condition.getValue());
      case startWith:
        return query.likeLeft(condition.getProperty(), condition.getValue());
      case endWith:
        return query.likeRight(condition.getProperty(), condition.getValue());
      case in:
        return query.in(condition.getProperty(), condition.getValues());
      case notIn:
        return query.notIn(condition.getProperty(), condition.getValues());
      case between:
        return query.between(condition.getProperty(), condition.getValues().get(0), condition.getValues().get(1));
      default:
        return query.eq(condition.getProperty(), condition.getValue());
    }
  }
}
