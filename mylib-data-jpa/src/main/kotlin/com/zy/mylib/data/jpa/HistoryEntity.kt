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
package com.zy.mylib.data.jpa

import com.fasterxml.jackson.annotation.JsonView
import com.zy.mylib.base.model.BaseModel.DetailView
import java.util.*
import javax.persistence.Column
import javax.persistence.MappedSuperclass

/**
 * @author 扬
 * @date 2017/5/15
 */
@MappedSuperclass
abstract class HistoryEntity : UUIDBaseEntity() {
  interface HistoryView : DetailView
  /**
   * Gets 创建人.
   *
   * @return Value of 创建人.
   */
  /**
   * Sets new 创建人.
   *
   * @param createUserId New value of 创建人.
   */
  /**
   * 创建人
   */
  @Column(length = 32)
  @JsonView(HistoryView::class)
  var createUserId: String? = null
  /**
   * Gets 创建时间.
   *
   * @return Value of 创建时间.
   */
  /**
   * Sets new 创建时间.
   *
   * @param createTime New value of 创建时间.
   */
  /**
   * 创建时间
   */
  @Column
  @JsonView(HistoryView::class)
  var createTime: Date? = null
  /**
   * Gets 最后修改时间.
   *
   * @return Value of 最后修改时间.
   */
  /**
   * Sets new 最后修改时间.
   *
   * @param lastModifyTime New value of 最后修改时间.
   */
  /**
   * 最后修改时间
   */
  @Column
  @JsonView(HistoryView::class)
  var lastModifyTime: Date? = null
  /**
   * Gets 最后修改人.
   *
   * @return Value of 最后修改人.
   */
  /**
   * Sets new 最后修改人.
   *
   * @param lastModifyUserId New value of 最后修改人.
   */
  /**
   * 最后修改人
   */
  @Column(length = 32)
  @JsonView(HistoryView::class)
  var lastModifyUserId: String? = null
}