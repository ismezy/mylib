package com.zy.mylib.base.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 查询条件基础类
 */
@Getter @Setter @SuperBuilder
public abstract class BaseCondition {
  /**
   * 逻辑运算符 默认and
   */
  LogicalOperators logicalOperator = LogicalOperators.and;
}
