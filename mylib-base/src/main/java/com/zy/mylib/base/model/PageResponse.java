package com.zy.mylib.base.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 返回分页数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {
  /**
   * 分页数据
   */
  private List<T> list;

  /**
   * 数据总数量
   */
  private Long totalElement;
  /**
   * 每页大小，默认20条每页
   */
  private Long size = 20L;
  /**
   * 当前页码(从0开始)
   */
  private Long page = 0L;

  /**
   * 页面总数量
   * @return
   */
  public Long getTotalPage() {
    return totalElement / getSize() + (totalElement % getSize() > 0 ? 1 : 0);
  }

  public static <T> PageResponse<T> fromRequest(PageRequest request, Long totalElement, List<T> list) {
    return PageResponse.<T>builder()
        .page(request.getPage())
        .size(request.getSize())
        .list(list)
        .totalElement(totalElement)
        .build();
  }
}
