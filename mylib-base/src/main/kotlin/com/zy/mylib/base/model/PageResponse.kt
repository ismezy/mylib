/**
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
package com.zy.mylib.base.model

import kotlin.math.ceil

/**
 * 返回分页数据
 */
class PageResponse<T> (
    /**
   * 分页数据
   */
  val list: List<T>? = null,

    /**
   * 数据总数量
   */
  val totalElement: Long = 0,

    /**
   * 每页大小，默认20条每页
   */
  val size: Long = 20L,

    /**
   * 当前页码(从0开始)
   */
  val page: Long = 0L,
) {

  /**
   * 页面总数量
   * @return
   */
  val totalPage: Long
    get() = ceil((totalElement / size).toDouble()).toLong()

  companion object {
    @JvmStatic
    fun <T> fromRequest(request: PageRequest, totalElement: Long, list: List<T>?): PageResponse<T> {
      return PageResponse(
          page = request.page,
          size = request.size,
          list = list,
          totalElement = totalElement
      )
    }
  }
}