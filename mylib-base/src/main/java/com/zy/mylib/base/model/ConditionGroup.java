package com.zy.mylib.base.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 条件分组
 */
@Data
@SuperBuilder
public class ConditionGroup extends BaseCondition {

  /**
   * 条件
   */
  List<BaseCondition> conditions;
}
