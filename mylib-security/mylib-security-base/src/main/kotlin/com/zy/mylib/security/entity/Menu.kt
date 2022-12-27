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
package com.zy.mylib.security.entity

import com.zy.mylib.mongo.model.BaseMongoModel
import javax.persistence.*
import org.springframework.data.mongodb.core.mapping.*

/**
 * 菜单
 * @author 代码生成器
 */
@Document("sys_menu")
class Menu: BaseMongoModel() {
  /**
   * 菜单编号
   */
  @Column
  var code: String? = null
  /**
   * 菜单标题
   */
  @Column
  var caption: String? = null
  /**
   * 低级菜单id
   */
  @Column
  var parentId: String? = null

  /**
   * 图标
   */
  @Column
  var iconKey: String? = null
}
