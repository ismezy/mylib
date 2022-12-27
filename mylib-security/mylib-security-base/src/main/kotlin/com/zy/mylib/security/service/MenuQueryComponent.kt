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
package com.zy.mylib.security.service

import com.zy.mylib.security.dto.MenuTreeNode
import com.zy.mylib.security.dto.mapper.MenuConvert
import com.zy.mylib.security.entity.Menu
import com.zy.mylib.security.manager.MenuManager
import org.springframework.stereotype.Component
import javax.inject.Inject

/**
 * 菜单查询组件
 */
@Component
class MenuQueryComponent {
  @Inject
  private lateinit var menuManager: MenuManager
  @Inject
  private lateinit var menuConvert: MenuConvert
  fun genTreeView(parentId: String?): List<MenuTreeNode> {
    val rootNodes: List<Menu> = menuManager.findByParentId(parentId)
    return rootNodes.map { menuConvert.toMenuTreeNode(it).apply { children = genTreeView(this.id) } }
  }
}