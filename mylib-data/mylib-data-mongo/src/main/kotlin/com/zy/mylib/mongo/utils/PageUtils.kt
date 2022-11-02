/*
 * Copyright © 2020 ismezy (ismezy@qq.com)
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
package com.zy.mylib.mongo.utils

import java.util.stream.Collectors
import com.zy.mylib.base.model.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

object PageUtils {
  fun toSpringPageRequest(request: com.zy.mylib.base.model.PageRequest): PageRequest {
    val sort = toSpringSort(request.sortRequests)
    return PageRequest.of(request.page.toInt(), request.size.toInt(), sort)
  }

  fun toSpringSort(sortRequests: List<SortRequest>?): Sort {
    val orders = sortRequests!!.stream().map { it: SortRequest ->
      if (SortRequest.SortDirection.Descend == it.direction) {
        return@map Sort.Order.desc(it.property)
      }
      Sort.Order.asc(it.property)
    }.collect(Collectors.toList())
    return Sort.by(orders)
  }
}