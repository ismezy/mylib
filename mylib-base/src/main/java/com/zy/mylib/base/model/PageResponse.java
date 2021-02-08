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
