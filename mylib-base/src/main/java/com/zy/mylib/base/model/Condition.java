package com.zy.mylib.base.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 查询条件
 */
@Data
@SuperBuilder
public class Condition extends BaseCondition {
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
}
