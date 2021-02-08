package com.zy.mylib.base.model;

/**
 * 比较运算符
 */
public enum ComparisonOperators {
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
  in,
  /**
   * 不包含
   */
  notIn,
}
