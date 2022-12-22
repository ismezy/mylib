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

import com.zy.mylib.security.LoginUser
import com.zy.mylib.security.dto.MenuResponse
import com.zy.mylib.security.dto.mapper.MenuConvert
import com.zy.mylib.security.manager.MenuManager
import com.zy.mylib.security.manager.RoleManager
import org.springframework.stereotype.Component
import javax.inject.Inject

/**
 * 用户服务
 */
@Component
class UserQueryComponent {
  @Inject
  private lateinit var roleManager: RoleManager
  @Inject
  private lateinit var menuManager: MenuManager
  @Inject
  private lateinit var menuConvert: MenuConvert
  fun findUserMenus(loginUser: LoginUser): List<MenuResponse>? {
    return loginUser.roles
      ?.map{ roleManager.findByCode(it) }  // 转为role对象
      ?.map { it.menus }  // 转为菜单code list
      ?.flatten()   // 拉平
      ?.distinct()?.let{
        menuManager.findByCodes(it)
      }?.map { menuConvert.toResponse(it) }
  }
}
