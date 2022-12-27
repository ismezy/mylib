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
package com.zy.mylib.security.rest

import com.zy.mylib.security.manager.MenuManager
import com.zy.mylib.security.entity.Menu
import org.springframework.web.bind.annotation.*
import com.fasterxml.jackson.annotation.JsonView
import com.zy.mylib.base.model.BaseModel
import com.zy.mylib.webmvc.base.BaseRest
import javax.inject.Inject
import com.zy.mylib.base.model.*
import com.zy.mylib.base.exception.BusException
import com.zy.mylib.security.dto.MenuRequest
import com.zy.mylib.security.dto.MenuResponse
import com.zy.mylib.security.dto.MenuTreeNode
import com.zy.mylib.security.dto.mapper.MenuConvert
import com.zy.mylib.security.service.MenuQueryComponent
import org.springframework.validation.annotation.Validated

/**
 * 菜单 rest接口
 * @author 代码生成器
 */
@RestController
@RequestMapping("/sys/menu-manage")
class MenuRest: BaseRest() {
  @Inject
  private lateinit var manager: MenuManager
  @Inject
  private lateinit var menuQuery: MenuQueryComponent
  @Inject
  private lateinit var menuConvert: MenuConvert

  @GetMapping("{id}")
  @JsonView(BaseModel.DetailView::class)
  fun findOne(@PathVariable("id") id: String): Menu {
    var ret = manager.findById(id)
    if(ret != null) {
      return ret
    }
    throw BusException.builder().message("数据不存在").httpStatus(404).build()
  }

  @PostMapping
  fun addEntity(@Validated @RequestBody entity: MenuRequest): MenuResponse {
    return manager.add(menuConvert.fromRequest(entity)).let { menuConvert.toResponse(it)}
  }

  @PutMapping
  fun updateEntity(@Validated @RequestBody entity: MenuRequest): MenuResponse {
    return manager.update(menuConvert.fromRequest(entity)).let { menuConvert.toResponse(it)}
  }

  @DeleteMapping("{id}")
  fun remove(@PathVariable("id") id: String) {
    return manager.delete(id)
  }

  @GetMapping("/pager")
  fun findPage(pageRequest: PageRequest, conditions: List<Condition>): PageResponse<Menu> {
    return manager.pageQuery(pageRequest, conditions)
  }

  @GetMapping("/tree-view")
  fun manageTree(): List<MenuTreeNode> {
    return menuQuery.genTreeView(null)
  }
}
