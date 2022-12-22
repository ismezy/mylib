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

import com.zy.mylib.security.LoginUser
import com.zy.mylib.security.Passport
import com.zy.mylib.security.dto.MenuResponse
import com.zy.mylib.security.service.UserQueryComponent
import com.zy.mylib.webmvc.base.BaseRest
import io.swagger.annotations.Api
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject

/**
 * 当前用户信息接口
 */
@RestController("/current-user")
@Api("当前登录用户接口")
class CurrentUserRest : BaseRest() {
  @Inject
  private lateinit var passport: Passport
  @Inject
  private lateinit var userQuery: UserQueryComponent
  /**
   * 获取当前登录用户信息
   */
  @GetMapping
  @ApiOperation("获取当前登录用户")
  fun userInfo(): LoginUser? {
    return passport.user
  }

  /**
   * 获取当前用户菜单
   */
  @GetMapping("/menus")
  @ApiOperation("获取当前用户菜单")
  fun loadMenus(): List<MenuResponse>? {
    return passport.user?.let { userQuery.findUserMenus(it) }
  }

}
