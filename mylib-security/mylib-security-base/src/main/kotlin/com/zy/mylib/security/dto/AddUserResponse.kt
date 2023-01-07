package com.zy.mylib.security.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.util.*

/**
 * 新增用户响应参数
 */
@ApiModel("新增用户响应参数")
class AddUserResponse {
  @ApiModelProperty("id")
  var id: String? = null
  @ApiModelProperty("密码")
  var password: String? = null
  @ApiModelProperty("登录ID")
  var loginId: String? = null
  @ApiModelProperty("是否启用")
  var enabled: Boolean? = null
  @ApiModelProperty("用户名称")
  var username: String? = null
  @ApiModelProperty("用户角色")
  var role = mutableListOf<String>()
}