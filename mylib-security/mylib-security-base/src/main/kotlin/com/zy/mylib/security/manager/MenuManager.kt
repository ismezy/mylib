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
package com.zy.mylib.security.manager

import com.zy.mylib.security.entity.Menu
import com.zy.mylib.base.service.Manager

/**
 * 菜单 管理类接口
 * @author 代码生成器
 */
interface MenuManager : Manager<Menu, String> {
  fun findByCodes(codes: List<String>): List<Menu>
  fun findByParentId(parentId: String?): List<Menu>
}
