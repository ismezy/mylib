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
package com.zy.mylib.security.casbin.rest

import com.zy.mylib.security.casbin.manager.CasbinModelManager
import com.zy.mylib.security.casbin.entity.CasbinModel
import org.springframework.web.bind.annotation.*
import com.fasterxml.jackson.annotation.JsonView
import com.zy.mylib.base.model.BaseModel
import com.zy.mylib.webmvc.base.BaseRest
import javax.inject.Inject
import com.zy.mylib.base.model.*
import com.zy.mylib.base.exception.BusException
import org.springframework.validation.annotation.Validated

/**
 * casbin model rest接口
 * @author 代码生成器
 */
//@RestController
//@RequestMapping("/填写rest地址")
class CasbinModelRest: BaseRest() {
  @Inject
  private lateinit var manager: CasbinModelManager

  @GetMapping("{id}")
  @JsonView(BaseModel.DetailView::class)
  fun findOne(@PathVariable("id") id: String): CasbinModel {
    var ret = manager.findById(id)
    if(ret != null) {
      return ret
    }
    throw BusException.builder().message("数据不存在").httpStatus(404).build()
  }

  @PostMapping
  @JsonView(BaseModel.DetailView::class)
  fun addEntity(@Validated(BaseModel.AddCheck::class) @RequestBody entity: CasbinModel): CasbinModel {
    return manager!!.add(entity)
  }

  @PutMapping
  @JsonView(BaseModel.DetailView::class)
  fun updateEntity(@Validated(BaseModel.UpdateCheck::class) @RequestBody entity: CasbinModel): CasbinModel {
    return manager.update(entity)
  }

  @DeleteMapping("{id}")
  fun remove(@PathVariable("id") id: String) {
    return manager.delete(id)
  }
}
