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
import com.zy.mylib.base.model.BaseModel.BaseView
import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.MappedSuperclass

/**
 * mongo基础模型
 *
 * @author 周扬
 */
@MappedSuperclass
open class BaseMongoModel : BaseModel {
  @Id
  @Column(name = "_id")
  @JsonView(BaseView::class)
  var id: String? = null
}