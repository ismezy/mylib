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
package com.zy.mylib.base.model

/**
 * 分页查询请求
 */
class PageRequest (
    /**
   * 排序规则
   */
  val sortRequests: List<SortRequest>? = null,

    /**
   * 每页大小，默认20条每页
   */
  val size: Long = 20L,

    /**
   * 当前页码(从0开始)
   */
  val page: Long = 0L

) {
  /**
   * 查询记录起始数
   * @return
   */
  val offset: Long
    get() = size * page
}