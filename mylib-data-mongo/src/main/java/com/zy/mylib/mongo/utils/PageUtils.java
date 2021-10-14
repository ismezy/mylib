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
package com.zy.mylib.mongo.utils;

import com.zy.mylib.base.model.PageRequest;
import com.zy.mylib.base.model.SortRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

public class PageUtils {
  public static org.springframework.data.domain.PageRequest toSpringPageRequest(PageRequest request) {
    Sort sort = toSpringSort(request.getSortRequests());
    return org.springframework.data.domain.PageRequest.of(request.getPage().intValue(),
        request.getSize().intValue(), sort);
  }

  public static Sort toSpringSort(List<SortRequest> sortRequests) {
    List<Sort.Order> orders = sortRequests.stream().map(it -> {
      if(SortRequest.SortDirection.descend.equals(it.getDirection())) {
        return Sort.Order.desc(it.getProperty());
      }
      return Sort.Order.asc(it.getProperty());
    }).collect(Collectors.toList());
    return Sort.by(orders);
  }
}
