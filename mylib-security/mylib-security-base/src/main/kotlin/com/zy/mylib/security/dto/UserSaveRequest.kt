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
 * 保存用户请求参数
 */
@ApiModel("保存用户请求参数")
class UserSaveRequest {
  var id: String? = null;
  @ApiModelProperty("登录ID")
  var loginId: String? = null
  @ApiModelProperty("用户名称")
  var username: String? = null
  @ApiModelProperty("用户角色")
  var role = mutableListOf<String>()
}