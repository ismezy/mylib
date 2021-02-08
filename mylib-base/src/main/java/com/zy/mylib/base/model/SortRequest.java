package com.zy.mylib.base.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询排序请求
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SortRequest {
  /**
   * 排序方向
   */
  public enum SortDirection {
    ascend,
    descend
  }

  /**
   * 排序方向
   */
  private SortDirection direction;
  /**
   * 排序属性
   */
  private String property;
}
