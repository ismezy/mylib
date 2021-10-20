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
 * 查询排序请求
 */

class SortRequest (

  /**
   * 排序方向
   */
  val direction: SortDirection? = null,

  /**
   * 排序属性
   */
  val property: String? = null
) {
  /**
   * 排序方向
   */
  enum class SortDirection {
    Ascend, Descend
  }
}