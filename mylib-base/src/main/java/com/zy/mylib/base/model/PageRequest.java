package com.zy.mylib.base.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页查询请求
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageRequest {
  /**
   * 排序规则
   */
  private List<SortRequest> sortRequests;
  /**
   * 每页大小，默认20条每页
   */
  private Long size = 20L;
  /**
   * 当前页码(从0开始)
   */
  private Long page = 0L;
  /**
   * 查询记录起始数
   * @return
   */
  public Long getOffset() {
    return size * page;
  }
}
