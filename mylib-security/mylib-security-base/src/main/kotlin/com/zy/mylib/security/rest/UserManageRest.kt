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

import com.zy.mylib.base.model.Condition
import com.zy.mylib.base.model.PageRequest
import com.zy.mylib.base.model.PageResponse
import com.zy.mylib.security.dto.AddUserResponse
import com.zy.mylib.security.dto.UpdateUserResponse
import com.zy.mylib.security.dto.UserListResponse
import com.zy.mylib.security.dto.UserSaveRequest
import com.zy.mylib.security.dto.mapper.UserConvert
import com.zy.mylib.security.manager.UserManager
import com.zy.mylib.webmvc.base.BaseRest
import com.zy.mylib.webmvc.model.QueryWrapper
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import javax.inject.Inject

/**
 * 用户管理接口
 */
@RestController
@Api(description = "用户管理接口")
@RequestMapping("/sys/user-manage")
class UserManageRest : BaseRest() {
  @Inject
  private lateinit var userManager: UserManager

  @Inject
  private lateinit var userConvert: UserConvert

  /**
   * 创建用户
   */
  @ApiOperation("创建用户")
  @PostMapping
  fun createUser(param: UserSaveRequest): AddUserResponse {
    return userManager.add(userConvert.fromUserSaveRequest(param)).let { userConvert.toAddUserResponse(it) }
  }

  /**
   * 更新用户信息
   */
  @ApiOperation("更新用户信息")
  @PutMapping
  fun updateUserInfo(param: UserSaveRequest): UpdateUserResponse {
    return userManager.add(userConvert.fromUserSaveRequest(param)).let { userConvert.toUpdateUserResponse(it) }
  }

  /**
   * 分页查询
   */
  @GetMapping("/pager")
  @ApiOperation("分页查询")
  fun findPage(queryWrapper: QueryWrapper): PageResponse<UserListResponse> {
    return userManager.pageQuery(queryWrapper.page, queryWrapper.sort, queryWrapper.cond).let {
      PageResponse.fromRequest(queryWrapper.page, it.totalElement,
          it.list?.map { it1 -> userConvert.toUserListResponse(it1) })
    }
  }

  /**
   * 获取用户详情
   */
  @GetMapping("{id}")
  @ApiOperation("获取用户详情")
  fun getUser(id: String): UserListResponse? {
    return userManager.findById(id)?.let { userConvert.toUserListResponse(it) }
  }

  /**
   * 删除用户
   */
  @ApiOperation("删除用户")
  @DeleteMapping("{id}")
  fun removeUser(@PathVariable("id") id: String) {
    userManager.delete(id)
  }
}