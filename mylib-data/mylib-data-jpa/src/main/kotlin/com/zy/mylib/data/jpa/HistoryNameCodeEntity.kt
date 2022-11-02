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
package com.zy.mylib.data.jpa

import com.fasterxml.jackson.annotation.JsonView
import com.zy.mylib.base.model.BaseModel.BaseView
import javax.persistence.Column
import javax.persistence.MappedSuperclass

/**
 * @author 扬
 * @date 2017/5/15
 */
@MappedSuperclass
abstract class HistoryNameCodeEntity : HistoryEntity() {
  /**
   * Gets name.
   *
   * @return Value of name.
   */
  /**
   * Sets new name.
   *
   * @param name New value of name.
   */
  @JsonView(BaseView::class)
  @Column(length = 64)
  var name: String? = null
  /**
   * Gets code.
   *
   * @return Value of code.
   */
  /**
   * Sets new code.
   *
   * @param code New value of code.
   */
  @JsonView(BaseView::class)
  @Column(length = 32)
  var code: String? = null
}