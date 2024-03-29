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
package com.zy.mylib.mongo.model

import com.fasterxml.jackson.annotation.JsonView
import com.zy.mylib.base.model.BaseModel
import org.springframework.data.mongodb.core.index.Indexed
import javax.persistence.Column
import javax.persistence.MappedSuperclass

/**
 * 带名称和编号的基础模型
 *
 * @author ASUS
 */
@MappedSuperclass
class BaseNameCodeModel : BaseMongoModel() {
  /**
   * 名称
   */
  @Column
  @Indexed
  @JsonView(BaseModel.ListView::class)
  var name: String? = null
  /**
   * 编号
   */
  @Column
  @Indexed
  @JsonView(BaseModel.ListView::class)
  var code: String? = null

  companion object {
    private const val serialVersionUID = 6599338697014255001L
  }
}