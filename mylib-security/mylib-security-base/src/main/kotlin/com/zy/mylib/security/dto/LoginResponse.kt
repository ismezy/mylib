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

import com.zy.mylib.security.LoginUser
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * 登录响应
 */
@ApiModel("登录成功返回结果")
class LoginResponse {
  @ApiModelProperty("token")
  lateinit var token: String
  @ApiModelProperty("登录用户信息")
  lateinit var loginUser: LoginUser
}
