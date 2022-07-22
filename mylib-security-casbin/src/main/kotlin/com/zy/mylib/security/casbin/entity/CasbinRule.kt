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
package com.zy.mylib.security.casbin.entity

import com.zy.mylib.base.model.BaseModel
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import javax.persistence.Id

/**
 * casbin规则
 */
@Document("casbin_rule")
class CasbinRule : BaseModel {
  /**
   * 租户
   */
  @Id
  var id: String? = null

  /**
   * 租户所有规则
   */
  @Field
  var rules: MutableList<String>? = null

  companion object {
    private const  val serialVersionUID = -88L
  }
}
