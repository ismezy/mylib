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
package com.zy.mylib.security.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.util.*

/**
 * 用户查询列表返回字段
 */
@ApiModel("用户查询列表返回字段")
class UserListResponse {
  @ApiModelProperty("id")
  var id: String? = null
  @ApiModelProperty("登录ID")
  var loginId: String? = null
  @ApiModelProperty("是否启用")
  var enabled: Boolean? = null
  @ApiModelProperty("用户名称")
  var username: String? = null
  @ApiModelProperty("最后登录时间")
  var lastLogin: Date? = null
  @ApiModelProperty("用户角色")
  var role = mutableListOf<String>()
}